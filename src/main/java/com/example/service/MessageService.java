package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message persistMessage(Message message){

        if(accountRepository.findAccountByAccountId(message.getPostedBy()) == null){
            return null;
        }
        else if((message.getMessageText().length() > 255) || (message.getMessageText().length() == 0)){
            return null;
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(int id){
        return messageRepository.findMessageByMessageId(id);
    }
    
    public List<Message> getAllMessagesFromUser(int postedBy){
        return messageRepository.getAllMessagesByPostedBy(postedBy);
    }

    public Message updateMessageById(String updatedText, int messageId){
        
        Message messageToUpdate = messageRepository.findMessageByMessageId(messageId);

        if((updatedText.length() != 0) && (updatedText.length() < 256) && (messageToUpdate != null)){
            messageToUpdate.setMessageText(updatedText);
            return messageRepository.save(messageToUpdate);
        }
        return null;
    }

    public void deleteMessageByMessageId(int id){
        messageRepository.deleteMessageByMessageId(id);
    }
}
