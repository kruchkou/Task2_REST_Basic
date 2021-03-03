package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.RoleDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dao.impl.RoleRepositoryImpl;
import com.epam.esm.repository.dao.impl.UserRepositoryImpl;
import com.epam.esm.repository.model.entity.Role;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.impl.AuthException;
import com.epam.esm.service.exception.impl.UserAlreadyExistsException;
import com.epam.esm.service.exception.impl.UserNotFoundException;
import com.epam.esm.service.model.dto.UserDto;
import com.epam.esm.service.model.util.AuthRequest;
import com.epam.esm.service.model.util.SignUpUserData;
import com.epam.esm.service.util.mapper.EntityDtoUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceRepo implements UserService, UserDetailsService {

    /**
     * Format string to provide info by what id User wasn't found.
     */
    private static final String NOT_FOUND_BY_ID_PARAMETER = "id: %d";

    /**
     * Error message when User wasn't found by id.
     */
    private static final String NO_USER_WITH_ID_FOUND = "No user with id: %d found";

    /**
     * Error message when User with provided login already exists.
     */
    private static final String USER_BY_LOGIN_ALREADY_EXISTS = "User with login: %s already exists";

    /**
     * Error code when User with provided login already exists.
     */
    private static final String ERROR_CODE_USER_BY_LOGIN_ALREADY_EXISTS = "0304";

    /**
     * Error message when User wasn't found by login.
     */
    private static final String NO_USER_WITH_LOGIN_FOUND = "No user with login: %s found";

    /**
     * Error message when User wasn't found by login.
     */
    private static final String AUTH_FAILED = "No user with login and password found";

    /**
     * Error code when User wasn't found by id.
     */
    private static final String ERROR_CODE_AUTH_FAILED = "0305";

    /**
     * Error code when User wasn't found by id.
     */
    private static final String ERROR_CODE_USER_BY_ID_NOT_FOUND_FAILED = "0302404%d";

    /**
     * An object of {@link UserRepositoryImpl}.
     */
    private final UserRepositoryImpl userRepository;

    /**
     * An object of {@link RoleRepositoryImpl}.
     */
    private final RoleRepositoryImpl roleRepository;

    /**
     * An object of {@link PasswordEncoder}.
     */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceRepo(UserRepositoryImpl userRepository, RoleRepositoryImpl roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);

        User user = optionalUser.orElseThrow(() -> new UserNotFoundException(
                String.format(NO_USER_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_USER_BY_ID_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDtoUserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsers(Page page) {
        return EntityDtoUserMapper.toDto(userRepository.findAll());
    }

    @Override
    @Transactional
    public UserDto signUp(SignUpUserData signUpUserData) {

        Optional<User> userByLogin = userRepository.findByLogin(signUpUserData.getLogin());
        if (userByLogin.isPresent()) {
            throw new UserAlreadyExistsException(USER_BY_LOGIN_ALREADY_EXISTS, ERROR_CODE_USER_BY_LOGIN_ALREADY_EXISTS);
        }

        User user = new User();

        user.setName(signUpUserData.getName());
        user.setLogin(signUpUserData.getLogin());
        user.setPassword(passwordEncoder.encode(signUpUserData.getPassword()));

        Role role = roleRepository.getRoleByName(Role.RoleNames.USER);
        user.setRole(role);

        return EntityDtoUserMapper.toDto(userRepository.saveAndFlush(user));
    }

    @Override
    public UserDto signIn(AuthRequest authRequest) {
        String login = authRequest.getLogin();
        User userByLogin = userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException(NO_USER_WITH_LOGIN_FOUND + login));

        if (!passwordEncoder.matches(authRequest.getPassword(), userByLogin.getPassword())) {
            throw new AuthException(AUTH_FAILED, ERROR_CODE_AUTH_FAILED);
        }

        return EntityDtoUserMapper.toDto(userByLogin);
    }

    public List<UserDto> getUsers(Pageable pageable) {
        return EntityDtoUserMapper.toDto(userRepository.findAll(pageable).toList());
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(NO_USER_WITH_LOGIN_FOUND + login));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
    }
}
