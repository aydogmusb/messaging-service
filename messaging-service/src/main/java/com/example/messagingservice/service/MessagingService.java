package com.example.messagingservice.service;

import com.example.messagingservice.converter.MessageDtoListConverter;
import com.example.messagingservice.dto.MessageDto;
import com.example.messagingservice.entity.Block;
import com.example.messagingservice.entity.Message;
import com.example.messagingservice.entity.User;
import com.example.messagingservice.exception.MessagingServiceException;
import com.example.messagingservice.repository.MessageRepository;
import com.example.messagingservice.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessagingService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageDtoListConverter messageDtoListConverter;

    public MessagingService(MessageRepository messageRepository,
                            UserRepository userRepository,
                            MessageDtoListConverter messageDtoListConverter) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageDtoListConverter = messageDtoListConverter;
    }

    @MessageMapping("/send")
    @SendTo("/topic/send")
    public void sendMessage(String message, User sender, String receiverName) {
        if (!isUserInBlockedList(sender, receiverName)) {
            Message newMessage = new Message();
            newMessage.setSender(sender);
            newMessage.setMessage(message);
            messageRepository.save(newMessage);
        } else {
            throw new MessagingServiceException("You cannot send messages because you are in the blocked list.");
        }
    }

    public List<MessageDto> retrieveMessages(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        List<Message> messages = userOptional.map(User::getMessages).orElseThrow(() -> new MessagingServiceException("Can not found messages for given user"));
        return messageDtoListConverter.convert(messages);
    }

    private boolean isUserInBlockedList(User sender, String receiverName) {
        User receiverUser = userRepository.findByUsername(receiverName);
        List<Block> blockedList = receiverUser.getBlockedList();
        return blockedList.contains(sender);
    }
}
