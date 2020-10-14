package com.example.messagingservice.service;

import com.example.messagingservice.entity.User;
import com.example.messagingservice.exception.MessagingServiceException;
import com.example.messagingservice.repository.BlockRepository;
import com.example.messagingservice.repository.UserRepository;
import com.example.messagingservice.request.BlockUserRequest;
import com.example.messagingservice.request.LoginUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BlockRepository blockRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @Before
    public void setUp() {
        userService = new UserService(userRepository,
                blockRepository,
                encoder);
    }

    @Test
    public void should_login_user() {
        //given
        LoginUserRequest request = new LoginUserRequest();
        request.setUserName("username");
        request.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setId(1L);
        user.setPassword("password");
        user.setEmail("senderEmail");

        when(userRepository.findByUsername("username")).thenReturn(user);
        when(encoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);

        //when
        userService.loginUser(request);

        //then
        InOrder inOrder = inOrder(userRepository, encoder);
        inOrder.verify(userRepository).findByUsername("username");
        inOrder.verify(encoder).matches(request.getPassword(), user.getPassword());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_throw_exception_when_login_user_password_not_match() {
        //given
        LoginUserRequest request = new LoginUserRequest();
        request.setUserName("username");
        request.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setId(1L);
        user.setPassword("password2");
        user.setEmail("senderEmail");

        when(userRepository.findByUsername("username")).thenReturn(user);
        when(encoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        //when
        Throwable throwable = catchThrowable(() -> userService.loginUser(request));

        //then
        InOrder inOrder = inOrder(userRepository, encoder);
        inOrder.verify(userRepository).findByUsername("username");
        inOrder.verify(encoder).matches(request.getPassword(), user.getPassword());
        inOrder.verifyNoMoreInteractions();

        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(MessagingServiceException.class).hasMessage("Invalid user name and password combination.");
    }

    @Test
    public void should_block_user() {
        //given
        BlockUserRequest request = new BlockUserRequest();
        request.setUsername("username");
        request.setBlockedUserId(1L);

        User blockedUser = new User();
        blockedUser.setUsername("blockedUsername");
        blockedUser.setId(1L);
        blockedUser.setPassword("password");
        blockedUser.setEmail("email");

        User user = new User();
        user.setUsername("username");
        user.setId(2L);
        user.setPassword("password2");
        user.setEmail("email2");

        when(userRepository.findById(request.getBlockedUserId())).thenReturn(Optional.of(blockedUser));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        //when
        userService.blockUser(request);

        //then
        InOrder inOrder = inOrder(userRepository, blockRepository);
        inOrder.verify(userRepository).findByUsername("username");
        inOrder.verify(blockRepository).save(any());
        inOrder.verifyNoMoreInteractions();
    }
}
