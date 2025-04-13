package com.example.banking_app.controller;

import com.example.banking_app.dto.AccountDto;
import com.example.banking_app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController// for retful web services it combines controller & responsebody
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    //to add acount REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){  // itd method signature
            return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);// its a methos body
    }

    //to get account rest api
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){

        AccountDto accountDto=accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }
    // API for Deposit REST API
    @PutMapping ("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id ,@RequestBody Map<String,Double> request){
        Double amount =request.get("amount");
        AccountDto accountDto=accountService.deposit(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    //withdraw REST API

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id ,@RequestBody Map<String, Double> request){

        double amount=request.get("amount");
        AccountDto accountDto=accountService.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);

    }

    //GET all counts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts=accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    //delete Account rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted Successfully");
    }
}
