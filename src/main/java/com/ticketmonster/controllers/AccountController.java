package com.ticketmonster.controllers;


import com.ticketmonster.exceptions.ResourceNotFoundException;
import com.ticketmonster.models.Account;
import com.ticketmonster.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    // Get All Accounts
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get a Single Account
    @GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable(value = "id") Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));
    }

    // Create a new Account
    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody Account account) {
        return accountRepository.save(account);
    }

    // Update a Account
    @PutMapping("/accounts/{id}")
    public Account updateAccount(@PathVariable(value = "id") Long id, @Valid @RequestBody Account accountDetails) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));
        account.setEmail(accountDetails.getEmail());
        account.setPassword(accountDetails.getPassword());
        account.setRole(accountDetails.getRole());

        Account updatedAccount = accountRepository.save(account);
        return updatedAccount;
    }

    // Delete a Account
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable(value = "id") Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));

        accountRepository.delete(account);

        return ResponseEntity.ok().build();
    }
}
