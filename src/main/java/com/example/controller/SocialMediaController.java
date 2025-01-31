package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.service.AccountService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@RequestBody Account body){

        //if account exists with username
        //return 409

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
}
