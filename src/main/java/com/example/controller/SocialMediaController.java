package com.example.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.entity.Message;
import com.example.service.MessageService;

import java.util.List;

import javax.websocket.server.PathParam;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@RequestBody Account body){

        if(accountService.doesUserExist(body.getUsername()) != null){
            return ResponseEntity.status(409).body("Username Taken");
        }

        Account registeredUser = accountService.persistAccount(body);

        if(registeredUser != null){
            return ResponseEntity.status(200).body(registeredUser);
        }
        else{
            return ResponseEntity.status(400).body("Bad Request");
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity veryifyLogin(@RequestBody Account body){

        Account verifiedAccount = accountService.verifyLogin(body.getUsername(), body.getPassword());

        if(verifiedAccount != null){
            return ResponseEntity.status(200).body(verifiedAccount);
        }
        else{
            return ResponseEntity.status(401).body("Unauthorized Login");
        }
    }

    @PostMapping(value = "/messages")
    public ResponseEntity postMessage(@RequestBody Message body){

        Message postedMessage = messageService.persistMessage(body);

        if(postedMessage != null){
            return ResponseEntity.status(200).body(postedMessage);
        }
        else{
            return ResponseEntity.status(400).body("Error Posting Message");
        }
    }

    @GetMapping(value = "/messages")
    public ResponseEntity getAllMessages(){

        List<Message> allMessages = messageService.getAllMessages();

        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping(value = "/messages/{messageId}")
    public ResponseEntity getMessageByMesageId(@PathVariable int messageId){

        Message foundMessage = messageService.getMessageByMessageId(messageId);

        return ResponseEntity.status(200).body(foundMessage);
    }

    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity deleteMessageByMessageId(@PathVariable int messageId){

        Message deletedMessage = messageService.getMessageByMessageId(messageId);

        if(deletedMessage != null){
            messageService.deleteMessageByMessageId(messageId);
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body("");
    }

    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity updateMessageById(@PathVariable int messageId, @RequestBody Message message){

        Message updatedMessage = messageService.updateMessageById(message.getMessageText(), messageId);

        if(updatedMessage != null){
            return ResponseEntity.status(200).body("1");
        }
        return ResponseEntity.status(400).body("Error Updating Message");
    }

    @GetMapping(value = "/accounts/{accountId}/messages")
    public ResponseEntity getAllMessagesFromUser(@PathVariable int accountId){

        List<Message> foundMessages = messageService.getAllMessagesFromUser(accountId);

        return ResponseEntity.status(200).body(foundMessages);

    }
}
