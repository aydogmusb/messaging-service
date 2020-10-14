package com.example.messagingservice.service;

import com.example.messagingservice.converter.MessageDtoListConverter;
import com.example.messagingservice.dto.MessageDto;
import com.example.messagingservice.entity.Message;
import com.example.messagingservice.entity.User;
import com.example.messagingservice.repository.MessageRepository;
import com.example.messagingservice.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessagingServiceTest {

    private MessagingService messagingService;

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageDtoListConverter messageDtoListConverter;

    @Before
    public void setUp() {
        messagingService = new MessagingService(messageRepository,
                userRepository,
                messageDtoListConverter);
    }

    @Test
    public void should_send_message() {
        //given
        User sender = new User();
        sender.setUsername("username");
        sender.setId(1L);
        sender.setEmail("senderEmail");

        String message = "Hello";
        String receiverName = "receiverName";

        User receiver = new User();
        receiver.setUsername(receiverName);
        receiver.setId(2L);
        receiver.setEmail("receiverEmail");

        when(userRepository.findByUsername(receiverName)).thenReturn(receiver);

        //when
        messagingService.sendMessage(message, sender, receiverName);

        //then
        verify(userRepository).findByUsername(receiverName);
        verify(messageRepository).save(any());
    }

    @Test
    public void should_retrieve_messages() {
        //given
        Long id = 1L;
        Message message = new Message();
        message.setMessage("Hello");

        List<Message> messageList = new ArrayList<>();
        messageList.add(message);

        User user = new User();
        user.setMessages(messageList);

        MessageDto messageDto = new MessageDto();
        messageDto.setMessage("Hello");

        List<MessageDto> messageDtoList = new ArrayList<>();
        messageDtoList.add(messageDto);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(messageDtoListConverter.convert(messageList)).thenReturn(messageDtoList);

        //when
        List<MessageDto> messageDtos = messagingService.retrieveMessages(id);

        //then
        assertThat(messageDtos).isNotEmpty();
    }
}
