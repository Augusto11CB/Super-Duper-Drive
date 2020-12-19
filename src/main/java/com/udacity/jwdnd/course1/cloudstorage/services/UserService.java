package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private HashService hashService;
    private UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public Optional<User> createUser(final String userName, final String password, final String firstName, final String lastName) {


        final String encodedSalt = hashService.getEncodedSalt();
        final String encodedPassword = hashService.getHashedValue(password, encodedSalt);

        final User user = User.builder()
                .userName(userName)
                .password(encodedPassword)
                .firstName(firstName)
                .salt(encodedSalt)
                .lastName(lastName)
                .build();

        final int userID = userMapper.insert(user);

        if (userID > 0) {
            user.setUserID(userID);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> findUserByUserName(final String userName) {
        return userMapper.getUserByName(userName);
    }

}
