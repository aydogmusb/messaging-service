package com.example.messagingservice.converter;

import com.example.messagingservice.dto.MessageDto;
import com.example.messagingservice.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageDtoListConverter {

    public List<MessageDto> convert(List<Message> messageList) {
        List<MessageDto> messageDtoList = new ArrayList<>();
        MessageDto messageDto = new MessageDto();

        messageList.forEach(message -> {
            messageDto.setMessage(message.getMessage());
            messageDtoList.add(messageDto);
        });
        return messageDtoList;
    }
}
