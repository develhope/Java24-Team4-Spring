package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.dtos.responses.*;
import com.develhope.spring.entities.*;
import com.develhope.spring.repositories.*;
import com.develhope.spring.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ListenerRepository listenerRepository;
    private final AdvertiserRepository advertiserRepository;
    private final ArtistRepository artistRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionServiceImpl subscriptionService;

    private final IllegalArgumentException fieldsError = new IllegalArgumentException(
            "One or more required fields are null or empty"
    );

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, ListenerRepository listenerRepository, AdvertiserRepository advertiserRepository, ArtistRepository artistRepository, SubscriptionRepository subscriptionRepository, SubscriptionServiceImpl subscriptionService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.listenerRepository = listenerRepository;
        this.advertiserRepository = advertiserRepository;
        this.artistRepository = artistRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;

    }

    @Override
    public Optional<?> createUser(UserCreationDTO request) throws InvocationTargetException, IllegalAccessException {

        if (!inspectAndInvokeGettersForUser(request)) {
            throw fieldsError;
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
                    throw fieldsError;
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
                    throw fieldsError;
                }

                User savedUser = userRepository.saveAndFlush(toSave);

                Advertiser advertiserToSave = modelMapper.map(request, Advertiser.class);
                advertiserToSave.setUser(savedUser);

                Advertiser advertiserSaved = advertiserRepository.saveAndFlush(advertiserToSave);
                AdvertiserResponseDTO response = modelMapper.map(advertiserSaved, AdvertiserResponseDTO.class);

                return Optional.of(advertiserSaved);
            }

            default -> throw new IllegalArgumentException(
                    "Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER (case sensitive)!"
            );

        }

    }

    @Override
    public Optional<?> updateUser(UserCreationDTO request, Long id) throws InvocationTargetException, IllegalAccessException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User with ID " + id + " not Found!");
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
            default -> throw new IllegalArgumentException(
                    "Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER (case sensitive)!"
            );
        }
    }

    @Override
    public Optional<UserWithRoleDetailsResponseDTO> getUserById(Long id) {
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
                default -> throw new IllegalArgumentException(
                        "Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER (case sensitive)!"
                );
            }
        });
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
                default -> throw new IllegalArgumentException(
                        "Unknown user role! The role can only be: LISTENER, ARTIST or ADVERTISER (case sensitive)!"
                );

            }

        }).collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserById(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User with ID " + id + " not Found!");

        } else if (userOptional.get().getRole() == User.Role.LISTENER) {
            listenerRepository.deleteById(id);
            userRepository.deleteById(id);
            return true;


        } else if (userOptional.get().getRole() == User.Role.ARTIST) {
            artistRepository.deleteById(id);
            userRepository.deleteById(id);
            return true;

        } else if (userOptional.get().getRole() == User.Role.ADVERTISER) {
            advertiserRepository.deleteById(id);
            userRepository.deleteById(id);
            return true;
        } else {

            return false;
        }

    }

    @Override
    public void deleteAllUsers() {

        userRepository.deleteAll();
        listenerRepository.deleteAll();
        artistRepository.deleteAll();
        advertiserRepository.deleteAll();
    }

    // TODO ADD METHOD DELETE ALL BY ROLE

    private UserWithRoleDetailsResponseDTO handleListenerRole(User user, UserWithRoleDetailsResponseDTO response) {
        Optional<Listener> listener = listenerRepository.findById(user.getId());

        if (listener.isEmpty()) {
            throw new EntityNotFoundException("Listener with ID " + user.getId() + " not Found!");
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

    private void updateUserRole(UserCreationDTO request, User toUpdate) throws InvocationTargetException, IllegalAccessException {
        Method getRoleMethod;
        try {
            getRoleMethod = request.getClass().getMethod("getRole");
            Object roleValue = getRoleMethod.invoke(request);
            if (roleValue != null && roleValue instanceof User.Role) {

                Method setRoleMethod = User.class.getMethod("setRole", User.Role.class);
                setRoleMethod.invoke(toUpdate, roleValue);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Role getter or setter not found!");
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



