package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.dtos.responses.*;
import com.develhope.spring.entities.*;
import com.develhope.spring.exceptions.*;
import com.develhope.spring.repositories.*;
import com.develhope.spring.services.interfaces.MinioService;
import com.develhope.spring.services.interfaces.UserService;
import com.develhope.spring.utils.Security;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
public class UserServiceImpl implements UserService {


    //TODO CONFIGUR. ENABLE, DISABLE, CREARE RUOLO CHE PUO FARE STE COSE (ADMIN)
    //todo aggiungere controllo di Current User  anche x i metodi getById


    private final MinioService minioService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ListenerRepository listenerRepository;
    private final AdvertiserRepository advertiserRepository;
    private final ArtistRepository artistRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionServiceImpl subscriptionService;
    private final PasswordEncoder passwordEncoder;

    @Value("${minio.profileDataBucket}")
    private String profileDataBucket;

    @Autowired
    public UserServiceImpl(MinioService minioService, ModelMapper modelMapper, UserRepository userRepository, ListenerRepository listenerRepository, AdvertiserRepository advertiserRepository, ArtistRepository artistRepository, SubscriptionRepository subscriptionRepository, SubscriptionServiceImpl subscriptionService, PasswordEncoder passwordEncoder) {
        this.minioService = minioService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.listenerRepository = listenerRepository;
        this.advertiserRepository = advertiserRepository;
        this.artistRepository = artistRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public Optional<?> createUser(UserCreationDTO request) throws InvocationTargetException, IllegalAccessException {

        if (!inspectAndInvokeGettersForUser(request)) {
            throw new InvalidUserFieldsException("[Creation failed] One or more required fields for User/Listener are null or empty");
        }

        UserEntity toSave = modelMapper.map(request, UserEntity.class);
        toSave.setRegistrationDate(LocalDate.now());
        toSave.setPassword(passwordEncoder.encode(toSave.getPassword()));

        switch (toSave.getRole()) {

            case LISTENER -> {

                UserEntity savedUserEntity = userRepository.saveAndFlush(toSave);

                Listener listenerToSave = new Listener();
                listenerToSave.setUser(savedUserEntity);

                Listener listenerSaved = listenerRepository.saveAndFlush(listenerToSave);
                ListenerResponseDTO response = modelMapper.map(listenerSaved, ListenerResponseDTO.class);


                return Optional.of(response);
            }

            case ARTIST -> {

                if (!inspectAndInvokeGettersForArtist(request)) {
                    throw new InvalidUserFieldsException("[Creation failed] One or more required fields for Artist are null or empty");
                }

                UserEntity savedUserEntity = userRepository.saveAndFlush(toSave);

                Artist artistToSave = modelMapper.map(request, Artist.class);
                artistToSave.setUser(savedUserEntity);

                Artist artistSaved = artistRepository.saveAndFlush(artistToSave);
                ArtistResponseDTO response = modelMapper.map(artistSaved, ArtistResponseDTO.class);

                return Optional.of(response);
            }

            case ADVERTISER -> {

                if (!inspectAndInvokeGettersForAdvertiser(request)) {
                    throw new InvalidUserFieldsException("[Creation failed] One or more required fields for Advertiser are null or empty");
                }

                UserEntity savedUserEntity = userRepository.saveAndFlush(toSave);

                Advertiser advertiserToSave = modelMapper.map(request, Advertiser.class);
                advertiserToSave.setUser(savedUserEntity);

                Advertiser advertiserSaved = advertiserRepository.saveAndFlush(advertiserToSave);
                AdvertiserResponseDTO response = modelMapper.map(advertiserSaved, AdvertiserResponseDTO.class);

                return Optional.of(advertiserSaved);
            }

            default -> throw new UnknownUserRoleException(
                    "[Creation failed] Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER (case sensitive)!"
            );

        }

    }

    @Transactional
    @Override
    public Optional<?> updateUser(UserCreationDTO request, Long id) throws InvocationTargetException, IllegalAccessException {
        UserEntity currentUser = Security.getCurrentUser();

        if (id < 0) {
            throw new NegativeIdException(
                    "[Update failed] Subscription ID cannot be negative. Now: " + id);
        }

        Optional<UserEntity> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("[Update failed] User with ID " + id + " not Found!");
        }

        UserEntity userEntity = userOptional.get();
        if (! userEntity.equals(currentUser))
            throw new AccessDeniedException("You are not allowed to update this user");


        checkFieldsAndUpdateUser(request, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        if (request.getRole() != userEntity.getRole()) {
            switch (userEntity.getRole()) {

                case LISTENER -> {
                    subscriptionRepository.deleteById(id);
                    listenerRepository.deleteById(id);
                    userRepository.deleteById(id);
                    return createUser(request);
                }
                case ARTIST -> {
                    artistRepository.deleteById(id);
                    userRepository.deleteById(id);
                    return createUser(request);
                }
                case ADVERTISER -> {
                    advertiserRepository.deleteById(id);
                    userRepository.deleteById(id);
                    return createUser(request);
                }
            }
        }

        switch (userEntity.getRole()) {

            case LISTENER -> {
                return updateSaveUserAndListener(userEntity);
            }
            case ARTIST -> {
                return updateSaveUserAndArtist(request, userEntity);
            }
            case ADVERTISER -> {
                return updateSaveAdvertiser(request, userEntity);
            }
            default -> throw new UnknownUserRoleException(
                    "[Update failed] Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER (case sensitive)!"
            );
        }
    }

    @Override
    public UserWithRoleDetailsResponseDTO getCurrentUser(){
        UserEntity currentUser = Security.getCurrentUser();
        UserWithRoleDetailsResponseDTO response = modelMapper.map(currentUser, UserWithRoleDetailsResponseDTO.class);

        switch (currentUser.getRole()) {
            case LISTENER -> {
                return handleListenerRole(currentUser, response);
            }
            case ARTIST -> {
                return handleArtistRole(currentUser, response);
            }
            case ADVERTISER -> {
                return handleAdvertiserRole(currentUser, response);
            }
            default -> throw new UnknownUserRoleException(
                    "[Search failed] Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER!"
            );
        }
    }

    @Override
    public UserWithRoleDetailsResponseDTO getUserById(Long id) {

        if (id < 0) {
            throw new NegativeIdException(
                    "[Search failed] Subscription ID cannot be negative. Now: " + id);
        }

        return userRepository.findById(id).map(user -> {

            UserWithRoleDetailsResponseDTO response = modelMapper.map(user, UserWithRoleDetailsResponseDTO.class);

            switch (user.getRole()) {
                case LISTENER -> {
                    return handleListenerRole(user, response);
                }
                case ARTIST -> {
                    return handleArtistRole(user, response);
                }
                case ADVERTISER -> {
                    return handleAdvertiserRole(user, response);
                }
                default -> throw new UnknownUserRoleException(
                        "[Search failed] Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER!"
                );
            }
        }).orElseThrow(() -> new EntityNotFoundException("[Search failed] User with ID " + id +
                " not found in the database"));
    }

    @Override
    public List<UserWithRoleDetailsResponseDTO> getAllUsers() {

        return userRepository.findAll().stream().map(user -> {

            UserWithRoleDetailsResponseDTO response = modelMapper.map(user, UserWithRoleDetailsResponseDTO.class);

            switch (response.getRole()) {
                case LISTENER -> {
                    return handleListenerRole(user, response);
                }
                case ARTIST -> {
                    return handleArtistRole(user, response);
                }
                case ADVERTISER -> {
                    return handleAdvertiserRole(user, response);
                }
                default -> throw new UnknownUserRoleException(
                        "[Search failed] Unknown user role. The role can only be: LISTENER, ARTIST or ADVERTISER."
                );

            }

        }).collect(toList());
    }

    @Override
    public List<UserWithRoleDetailsResponseDTO> getAllByRole(UserEntity.Role role) {

        List<UserEntity> userEntities = userRepository.findByRole(role);

        if (userEntities.isEmpty()) {
            throw new EmptyResultException("Users with role " + role +
                    " were not found");
        }

        return userEntities.stream().map(user -> {

            UserWithRoleDetailsResponseDTO response = modelMapper.map(user, UserWithRoleDetailsResponseDTO.class);

            switch (user.getRole()) {

                case ARTIST -> handleArtistRole(user, response);

                case LISTENER -> handleListenerRole(user, response);

                case ADVERTISER -> handleAdvertiserRole(user, response);

                default ->
                        throw new UnknownUserRoleException("[Search failed] Unknown user role. The role can only be: LISTENER, ARTIST or ADVERTISER.");
            }

            return response;

        }).toList();
    }

    @Transactional
    @Override
    public UserWithRoleDetailsResponseDTO deleteUserById(Long id) {
        UserEntity currentUser = Security.getCurrentUser();

        if (id < 0) {
            throw new NegativeIdException(
                    "[Search failed] Subscription ID cannot be negative. Now: " + id);
        }

        Optional<UserEntity> userOptional = userRepository.findById(id);


        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("[Delete failed] User with ID " + id + " not Found!");
        }

        if (!userOptional.get().equals(currentUser))// todo ex handler
            throw new AccessDeniedException("You are not allowed to delete this user");

        var responseDTO = modelMapper.map(userOptional.get(), UserWithRoleDetailsResponseDTO.class);

        switch (userOptional.get().getRole()) {

            case ARTIST -> {
                handleArtistRole(userOptional.get(), responseDTO);
                artistRepository.deleteById(id);
                userRepository.deleteById(id);
                return responseDTO;
            }

            case LISTENER -> {
                handleListenerRole(userOptional.get(), responseDTO);
                subscriptionRepository.deleteById(id);
                listenerRepository.deleteById(id);
                userRepository.deleteById(id);
                return responseDTO;
            }

            case ADVERTISER -> {
                handleAdvertiserRole(userOptional.get(), responseDTO);
                advertiserRepository.deleteById(id);
                userRepository.deleteById(id);
                return responseDTO;
            }

            default ->
                    throw new UnknownUserRoleException("[Search failed] Unknown user role. The role can only be: LISTENER, ARTIST or ADVERTISER.");
        }
    }

    @Transactional
    @Override
    public void deleteAllUsers() {

        userRepository.deleteAll();
        listenerRepository.deleteAll();
        artistRepository.deleteAll();
        advertiserRepository.deleteAll();
    }

    @Override
    public String uploadUserProfileImage(MultipartFile file, Long userID)  {
        UserEntity currentUser = Security.getCurrentUser();
        Optional<UserEntity> userOptional = userRepository.findById(userID);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("[Profile image upload failed] User with ID " + userID + " not Found!");
        }

        if (! userOptional.get().equals(currentUser))// todo ex handler
            throw new AccessDeniedException("You are not allowed to update this user");

        long maxSize = 1048576L; // 1MB
        int maxSizeMB = (int) maxSize / (1024 * 1024);

        String extension = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();

        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png") && !extension.equals("gif")) {
            throw new UnsupportedFileFormatException(
                    "[Profile image upload failed] unsupported format(Available formats: .jpg / .jpeg / .png / .gif)"
            );
        }
        if (file.getSize() > maxSize) {
            try {
                throw new FileSizeLimitExceededException( //todo aggiungere in ex. handler
                        "File too large. Max. size = " + maxSizeMB + "MB", file.getSize(), maxSize
                );
            } catch (FileSizeLimitExceededException e) {
                throw new MinIOFileUploadException(e.getMessage());
            }
        }

        String destinationFolderName = "user_" + userID + "_data/profileImages";
        String newFileName = "user_" + userID + "_profileImg";

        Map<String, String> uploaded = minioService.uploadFile(file, newFileName, destinationFolderName, profileDataBucket);
        String fullLink = uploaded.get("fullLink");
        String objectStorageFileName = uploaded.get("objectName");

        UserEntity userEntity = userOptional.get();
        userEntity.setUrlPhoto(fullLink);
        userEntity.setPhotoObjectStorageName(objectStorageFileName);
        userRepository.saveAndFlush(userEntity);

        return "Uploaded file url:" + fullLink;
    }

    @Override
    public void deleteUserProfileImg(Long userID) {
        Optional<UserEntity> userOptional = userRepository.findById(userID);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("[Profile image delete failed] User with ID " + userID + " not Found!");
        }

        UserEntity userEntity = userOptional.get();
        String fileToDelete = userEntity.getPhotoObjectStorageName();

        boolean deleted = minioService.deleteFile(profileDataBucket, fileToDelete);

        if (deleted) {
            userEntity.setPhotoObjectStorageName(null);
            userEntity.setUrlPhoto(null);
            userRepository.saveAndFlush(userEntity);
        }
    }

    private UserWithRoleDetailsResponseDTO handleListenerRole(UserEntity userEntity, UserWithRoleDetailsResponseDTO response) {

        if (userEntity.getId() < 0) {
            throw new NegativeIdException(
                    "[Search failed] User ID cannot be negative. Now: " + userEntity.getId());
        }

        Optional<Listener> listener = listenerRepository.findById(userEntity.getId());

        if (listener.isEmpty()) {
            throw new EntityNotFoundException("[Search failed] Listener with ID " + userEntity.getId() + " not Found!");
        }

        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(userEntity.getId());
        Subscription subscription = subscriptionOptional.orElse(null);

        if (subscription != null) {
            SubscriptionWithoutListenerDTO subscriptionWithoutListenerDTO = modelMapper.map(subscription, SubscriptionWithoutListenerDTO.class);

            subscriptionService.invokeFromOutsideSetCalculableFieldsDTO(subscriptionWithoutListenerDTO);
            ListenerDetailsDTO dto = new ListenerDetailsDTO(subscriptionWithoutListenerDTO);

            response.setRoleDetails(dto);
        } else {
            response.setRoleDetails("Listener does not have a premium subscription");
        }


        return response;
    }

    private UserWithRoleDetailsResponseDTO handleArtistRole(UserEntity userEntity, UserWithRoleDetailsResponseDTO response) {
        Optional<Artist> artist = artistRepository.findById(userEntity.getId());
        if (artist.isEmpty()) {
            throw new EntityNotFoundException("Artist with ID " + userEntity.getId() + " not Found!");
        }

        ArtistWithoutUserDTO dto = modelMapper.map(artist.get(), ArtistWithoutUserDTO.class);
        response.setRoleDetails(dto);

        return response;
    }

    private UserWithRoleDetailsResponseDTO handleAdvertiserRole(UserEntity userEntity, UserWithRoleDetailsResponseDTO response) {
        Optional<Advertiser> advertiser = advertiserRepository.findById(userEntity.getId());
        if (advertiser.isEmpty()) {
            throw new EntityNotFoundException("Advertiser with ID " + userEntity.getId() + " not Found!");
        }

        AdvertiserWithoutUserDTO dto = modelMapper.map(advertiser.get(), AdvertiserWithoutUserDTO.class);
        response.setRoleDetails(dto);

        return response;
    }

    private Boolean inspectAndInvokeGettersForUser(Object object) throws IllegalAccessException, InvocationTargetException {

        String[] fieldsToCheck = {
                "getNickName",
                "getName",
                "getLastName",
                "getNumPhone",
                "getEmail",
                "getPassword",
                "getUrlPhoto",
                "getUrlSocial",
                "getUserCountry",
                "getRole"
        };

        Class<?> inputClass = object.getClass();
        Method[] methods = inputClass.getMethods();

        for (String field : fieldsToCheck) {
            for (Method method : methods) {
                if (method.getName().equals(field) && method.getParameterTypes().length == 0) {
                    Object value = method.invoke(object);
                    if (value == null || (value instanceof String && ((String) value).isBlank())) {

                        return false;
                    }
                }
            }
        }

        return true;
    }

    private Boolean inspectAndInvokeGettersForArtist(Object object) throws IllegalAccessException, InvocationTargetException {

        String[] fieldsToCheck = {
                "getArtistName",
                "getDescription",
                "getArtistCountry"
        };

        Class<?> inputClass = object.getClass();
        Method[] methods = inputClass.getMethods();

        for (String field : fieldsToCheck) {
            for (Method method : methods) {
                if (method.getName().equals(field) && method.getParameterTypes().length == 0) {
                    Object value = method.invoke(object);
                    if (value == null || (value instanceof String && ((String) value).isBlank())) {

                        return false;
                    }
                }
            }
        }

        return true;
    }

    private Boolean inspectAndInvokeGettersForAdvertiser(Object object) throws IllegalAccessException, InvocationTargetException {

        String[] fieldsToCheck = {
                "getCompanyName",
                "getAdvertiserCountry",
                "getAdvertiserDescription"
        };

        Class<?> inputClass = object.getClass();
        Method[] methods = inputClass.getMethods();

        for (String field : fieldsToCheck) {
            for (Method method : methods) {
                if (method.getName().equals(field) && method.getParameterTypes().length == 0) {
                    Object value = method.invoke(object);
                    if (value == null || (value instanceof String && ((String) value).isBlank())) {

                        return false;
                    }
                }
            }
        }

        return true;
    }


    private void checkFieldsAndUpdateUser(UserCreationDTO request, UserEntity toUpdate) throws IllegalAccessException, InvocationTargetException {
        String[] fieldsToCheck = {
                "getNickName",
                "getName",
                "getLastName",
                "getNumPhone",
                "getEmail",
                "getPassword",
                "getUrlPhoto",
                "getUrlSocial",
                "getUserCountry"
        };

        Class<?> inputClass = request.getClass();
        Method[] methods = inputClass.getMethods();

        for (String field : fieldsToCheck) {
            for (Method method : methods) {
                if (method.getName().equals(field) && method.getParameterTypes().length == 0) {
                    Object value = method.invoke(request);
                    if (value == null || (value instanceof String && ((String) value).isBlank())) {
                        continue;
                    }

                    String setterName = "set" + field.substring(3);
                    try {
                        Method setter = UserEntity.class.getMethod(setterName, method.getReturnType());
                        setter.invoke(toUpdate, value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Setter not found for: " + setterName);
                    }
                }
            }
        }
    }

    public void checkFieldsAndUpdateArtist(UserCreationDTO request, Artist artist) throws InvocationTargetException, IllegalAccessException {
        String[] fieldsToCheck = {
                "getArtistName",
                "getDescription",
                "getArtistCountry"
        };

        Class<?> inputClass = request.getClass();
        Method[] methods = inputClass.getMethods();

        for (String field : fieldsToCheck) {
            for (Method method : methods) {
                if (method.getName().equals(field) && method.getParameterTypes().length == 0) {
                    Object value = method.invoke(request);
                    if (value == null || (value instanceof String && ((String) value).isBlank())) {
                        continue;
                    }

                    String setterName = "set" + field.substring(3);
                    try {
                        Method setter = Artist.class.getMethod(setterName, method.getReturnType());
                        setter.invoke(artist, value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Setter not found for: " + setterName);
                    }
                }
            }
        }
    }

    public void checkFieldsAndUpdateAdvertiser(UserCreationDTO request, Advertiser advertiser) throws InvocationTargetException, IllegalAccessException {
        String[] fieldsToCheck = {
                "getCompanyName",
                "getAdvertiserCountry",
                "getAdvertiserDescription"
        };

        Class<?> inputClass = request.getClass();
        Method[] methods = inputClass.getMethods();

        for (String field : fieldsToCheck) {
            for (Method method : methods) {
                if (method.getName().equals(field) && method.getParameterTypes().length == 0) {
                    Object value = method.invoke(request);
                    if (value == null || (value instanceof String && ((String) value).isBlank())) {
                        continue;
                    }

                    String setterName = "set" + field.substring(3);
                    try {
                        Method setter = Advertiser.class.getMethod(setterName, method.getReturnType());
                        setter.invoke(advertiser, value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Setter not found for: " + setterName);
                    }
                }
            }
        }
    }


    private Optional<?> updateSaveUserAndListener(UserEntity userEntity) {
        UserEntity updatedUserEntity = userRepository.saveAndFlush(userEntity);

        return listenerRepository.findById(updatedUserEntity.getId()).map(listener -> {

            listener = listenerRepository.saveAndFlush(listener);
            ListenerResponseDTO response = modelMapper.map(listener, ListenerResponseDTO.class);

            return response;
        });
    }

    private Optional<?> updateSaveUserAndArtist(UserCreationDTO request, UserEntity userEntity) {
        UserEntity updated = userRepository.saveAndFlush(userEntity);

        return artistRepository.findById(updated.getId()).map(artist -> {

            try {
                checkFieldsAndUpdateArtist(request, artist);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            artist = artistRepository.saveAndFlush(artist);
            ArtistResponseDTO response = modelMapper.map(artist, ArtistResponseDTO.class);

            return response;
        });
    }

    private Optional<?> updateSaveAdvertiser(UserCreationDTO request, UserEntity userEntity) throws InvocationTargetException, IllegalAccessException {
        UserEntity updatedUserEntity = userRepository.saveAndFlush(userEntity);

        return advertiserRepository.findById(updatedUserEntity.getId()).map(advertiser -> {

            try {
                checkFieldsAndUpdateAdvertiser(request, advertiser);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            advertiser = advertiserRepository.saveAndFlush(advertiser);

            AdvertiserResponseDTO response = modelMapper.map(advertiser, AdvertiserResponseDTO.class);
            return response;
        });
    }
}



