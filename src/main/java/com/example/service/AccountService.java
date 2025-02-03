package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account persistAccount(Account account){
        if((account.getPassword().length() >= 4) && (account.getUsername().length() != 0)){
            return accountRepository.save(account);
        }
        return null;
    }

    public Account doesUserExist(String username){
        return accountRepository.findAccountByUsername(username);
    }

    public Account verifyLogin(String username, String password){
        return accountRepository.findAccountByUsernameAndPassword(username, password);
    }

}
