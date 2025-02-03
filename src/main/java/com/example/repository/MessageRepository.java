package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{

    public Message findMessageByMessageId(int messageId);

    public void deleteMessageByMessageId(int messageId);

    public List<Message> getAllMessagesByPostedBy(int postedBy);
}
