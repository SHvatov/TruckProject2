package com.ishvatov.service.security;

import com.ishvatov.model.entity.UserEntity;
import com.ishvatov.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link UserDetailsService} interface.
 *
 * @author Sergey Khvatov
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * DAO used in this service to access the DB.
     */
    private UserRepository userRepository;

    /**
     * Default class constructor used for the field injection.
     *
     * @param userRepository {@link UserRepository} instance.
     */
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user into the system using his UID.
     *
     * @param userUniqueId unique identificator of the user.
     * @return {@link UserDetails} instance with the details about new user.
     * @throws UsernameNotFoundException if user's unique id was not found in the DB.
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userUniqueId) {
        // find user entity
        UserEntity user = userRepository.findById(userUniqueId)
            .orElseThrow(() -> new UsernameNotFoundException("User with id: [" + userUniqueId + "] was not found."));
        return new UserAuthCredentials(user);
    }
}
