package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.dtos.responses.*;
import com.develhope.spring.entities.*;
import com.develhope.spring.exceptions.*;
import com.develhope.spring.repositories.*;
import com.develhope.spring.services.interfaces.MinioService;
import com.develhope.spring.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final MinioService minioService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ListenerRepository listenerRepository;
    private final AdvertiserRepository advertiserRepository;
    private final ArtistRepository artistRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionServiceImpl subscriptionService;

    @Value("${minio.profileDataBucket}")
    private String profileDataBucket;

    @Autowired
    public UserServiceImpl(MinioService minioService, ModelMapper modelMapper, UserRepository userRepository, ListenerRepository listenerRepository, AdvertiserRepository advertiserRepository, ArtistRepository artistRepository, SubscriptionRepository subscriptionRepository, SubscriptionServiceImpl subscriptionService) {
        this.minioService = minioService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.listenerRepository = listenerRepository;
        this.advertiserRepository = advertiserRepository;
        this.artistRepository = artistRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;

    }

    @Transactional
    @Override
    public Optional<?> createUser(UserCreationDTO request) throws InvocationTargetException, IllegalAccessException {

        if (!inspectAndInvokeGettersForUser(request)) {
            throw new InvalidUserFieldsException("[Creation failed] One or more required fields for User/Listener are null or empty");
        }

        User toSave = modelMapper.map(request, User.class);
        toSave.setRegistrationDate(LocalDate.now());

        switch (toSave.getRole()) {

            case LISTENER -> {

                User savedUser = userRepository.saveAndFlush(toSave);

                Listener listenerToSave = new Listener();
                listenerToSave.setUser(savedUser);

                Listener listenerSaved = listenerRepository.saveAndFlush(listenerToSave);
                ListenerResponseDTO response = modelMapper.map(listenerSaved, ListenerResponseDTO.class);


                return Optional.of(response);
            }

            case ARTIST -> {

                if (!inspectAndInvokeGettersForArtist(request)) {
                    throw new InvalidUserFieldsException("[Creation failed] One or more required fields for Artist are null or empty");
                }

                User savedUser = userRepository.saveAndFlush(toSave);

                Artist artistToSave = modelMapper.map(request, Artist.class);
                artistToSave.setUser(savedUser);

                Artist artistSaved = artistRepository.saveAndFlush(artistToSave);
                ArtistResponseDTO response = modelMapper.map(artistSaved, ArtistResponseDTO.class);

                return Optional.of(response);
            }

            case ADVERTISER -> {

                if (!inspectAndInvokeGettersForAdvertiser(request)) {
                    throw new InvalidUserFieldsException("[Creation failed] One or more required fields for Advertiser are null or empty");
                }

                User savedUser = userRepository.saveAndFlush(toSave);

                Advertiser advertiserToSave = modelMapper.map(request, Advertiser.class);
                advertiserToSave.setUser(savedUser);

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

        if (id < 0) {
            throw new NegativeIdException(
                    "[Update failed] Subscription ID cannot be negative. Now: " + id);
        }

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("[Update failed] User with ID " + id + " not Found!");
        }

        User user = userOptional.get();

        checkFieldsAndUpdateUser(request, user);

        if (request.getRole() != user.getRole()) {
            switch (user.getRole()) {

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

        switch (user.getRole()) {

            case LISTENER -> {
                return updateSaveUserAndListener(user);
            }
            case ARTIST -> {
                return updateSaveUserAndArtist(request, user);
            }
            case ADVERTISER -> {
                return updateSaveAdvertiser(request, user);
            }
            default -> throw new UnknownUserRoleException(
                    "[Update failed] Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER (case sensitive)!"
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
                        "[Search failed] Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER (case sensitive)!"
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
    public List<UserWithRoleDetailsResponseDTO> getAllByRole(User.Role role) {

        List<User> users = userRepository.findByRole(role);

        if (users.isEmpty()) {
            throw new EmptyResultException("Users with role " + role +
                    " were not found");
        }

        return users.stream().map(user -> {

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

        if (id < 0) {
            throw new NegativeIdException(
                    "[Search failed] Subscription ID cannot be negative. Now: " + id);
        }

        Optional<User> userOptional = userRepository.findById(id);


        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("[Delete failed] User with ID " + id + " not Found!");
        }

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
    public String uploadUserProfileImage(MultipartFile file, Long userID) throws FileSizeLimitExceededException {

        Optional<User> userOptional = userRepository.findById(userID);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("[Profile image upload failed] User with ID " + userID + " not Found!");
        }

        long maxSize = 1048576L; // 1MB
        int maxSizeMB = (int) maxSize / (1024 * 1024);

        String extension = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();

        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png") && !extension.equals("gif")) {
            throw new UnsupportedFileFormatException( //todo aggiungere in ex. handler
                    "[Profile image upload failed] unsupported format(Available formats: .jpg / .jpeg / .png / .gif)"
            );
        }
        if (file.getSize() > maxSize) {
            throw new FileSizeLimitExceededException( //todo aggiungere in ex. handler
                    "File too large. Max. size = " + maxSizeMB + "MB", file.getSize(), maxSize
            );
        }

        String destinationFolderName = "user_" + userID + "_data/profileImages";
        String newFileName = "user_" + userID + "_profileImg";

        Map<String, String> uploaded = minioService.uploadFile(file, newFileName, destinationFolderName, profileDataBucket);
        String fullLink = uploaded.get("fullLink");
        String objectStorageFileName = uploaded.get("objectName");

        User user = userOptional.get();
        user.setUrlPhoto(fullLink);
        user.setPhotoObjectStorageName(objectStorageFileName);
        userRepository.saveAndFlush(user);

        return "Uploaded file url:" + fullLink;
    }

    @Override
    public void deleteUserProfileImg(Long userID) {
        Optional<User> userOptional = userRepository.findById(userID);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("[Profile image delete failed] User with ID " + userID + " not Found!");
        }

        User user = userOptional.get();
        String fileToDelete = user.getPhotoObjectStorageName();

        boolean deleted = minioService.deleteFile(profileDataBucket, fileToDelete);

        if (deleted) {
            user.setPhotoObjectStorageName(null);
            user.setUrlPhoto(null);
            userRepository.saveAndFlush(user);
        }
    }

    private UserWithRoleDetailsResponseDTO handleListenerRole(User user, UserWithRoleDetailsResponseDTO response) {

        if (user.getId() < 0) {
            throw new NegativeIdException(
                    "[Search failed] User ID cannot be negative. Now: " + user.getId());
        }

        Optional<Listener> listener = listenerRepository.findById(user.getId());

        if (listener.isEmpty()) {
            throw new EntityNotFoundException("[Search failed] Listener with ID " + user.getId() + " not Found!");
        }

        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(user.getId());
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

    private UserWithRoleDetailsResponseDTO handleArtistRole(User user, UserWithRoleDetailsResponseDTO response) {
        Optional<Artist> artist = artistRepository.findById(user.getId());
        if (artist.isEmpty()) {
            throw new EntityNotFoundException("Artist with ID " + user.getId() + " not Found!");
        }

        ArtistWithoutUserDTO dto = modelMapper.map(artist.get(), ArtistWithoutUserDTO.class);
        response.setRoleDetails(dto);

        return response;
    }

    private UserWithRoleDetailsResponseDTO handleAdvertiserRole(User user, UserWithRoleDetailsResponseDTO response) {
        Optional<Advertiser> advertiser = advertiserRepository.findById(user.getId());
        if (advertiser.isEmpty()) {
            throw new EntityNotFoundException("Advertiser with ID " + user.getId() + " not Found!");
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


    private void checkFieldsAndUpdateUser(UserCreationDTO request, User toUpdate) throws IllegalAccessException, InvocationTargetException {
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
                        Method setter = User.class.getMethod(setterName, method.getReturnType());
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


    private Optional<?> updateSaveUserAndListener(User user) {
        User updatedUser = userRepository.saveAndFlush(user);

        return listenerRepository.findById(updatedUser.getId()).map(listener -> {

            listener = listenerRepository.saveAndFlush(listener);
            ListenerResponseDTO response = modelMapper.map(listener, ListenerResponseDTO.class);

            return response;
        });
    }

    private Optional<?> updateSaveUserAndArtist(UserCreationDTO request, User user) {
        User updated = userRepository.saveAndFlush(user);

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

    private Optional<?> updateSaveAdvertiser(UserCreationDTO request, User user) throws InvocationTargetException, IllegalAccessException {
        User updatedUser = userRepository.saveAndFlush(user);

        return advertiserRepository.findById(updatedUser.getId()).map(advertiser -> {

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



