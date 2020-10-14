package com.example.messagingservice.service;

import com.example.messagingservice.entity.Block;
import com.example.messagingservice.entity.User;
import com.example.messagingservice.exception.MessagingServiceException;
import com.example.messagingservice.repository.BlockRepository;
import com.example.messagingservice.repository.UserRepository;
import com.example.messagingservice.request.BlockUserRequest;
import com.example.messagingservice.request.LoginUserRequest;
import com.example.messagingservice.request.RegisterUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository,
                       BlockRepository blockRepository,
                       BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.blockRepository = blockRepository;
        this.encoder = encoder;
    }

    public void registerUser(RegisterUserRequest request) {
        logger.info("Registration process started for given username {}", request.getUserName());

        String encodedPassword = encoder.encode(request.getPassword());
        User user = new User(request.getEmail(), request.getUserName(), encodedPassword);
        User userExists = userRepository.findByUsername(user.getUsername());

        if (userExists != null) {
            throw new MessagingServiceException(user.getUsername() + " already registered.");
        }
        userRepository.save(user);
        logger.info("Registration process ended for given username {}", request.getUserName());
    }

    public void loginUser(LoginUserRequest request) {
        logger.info("Login process started for given username {}", request.getUserName());
        User userExists = userRepository.findByUsername(request.getUserName());

        if (userExists == null) {
            throw new MessagingServiceException("Invalid user name.");
        }

        String password = request.getPassword();
        if (!encoder.matches(password, userExists.getPassword())) {
            throw new MessagingServiceException("Invalid user name and password combination.");
        }
        logger.info("Login process ended for given username {}", request.getUserName());
    }

    public void blockUser(BlockUserRequest request) {
        logger.info("Block user process started for given user id {}", request.getBlockedUserId());
        Optional<User> userOptional = userRepository.findById(request.getBlockedUserId());
        userOptional.ifPresent(userOpt -> addUserToBlockedList(userOptional, request.getUsername()));
        logger.info("Block user process ended for given user id {}", request.getBlockedUserId());
    }

    private void addUserToBlockedList(Optional<User> userOptional, String username) {
        User user = userRepository.findByUsername(username);
        Block block = new Block();
        block.setBlockedUser(userOptional.get());
        block.setUser(user);
        List<Block> blockedList = user.getBlockedList();
        blockedList.add(block);
        blockRepository.save(block);
    }
}
