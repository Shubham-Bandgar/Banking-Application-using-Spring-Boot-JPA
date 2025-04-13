package com.example.banking_app.service.impl;

import com.example.banking_app.dto.AccountDto;
import com.example.banking_app.entity.Account;
import com.example.banking_app.entity.mapper.AccountMapper;
import com.example.banking_app.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements com.example.banking_app.service.AccountService {
   private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.maptoAccount(accountDto);
        Account savedAccount =accountRepository.save(account);
        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {

      Account account= accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account Does not Exist "));
        return AccountMapper.maptoAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {

     Account account=accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));

     double total=account.getBalance() + amount;
     account.setBalance(total);
     Account savedAccount =accountRepository.save(account);

     return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account=accountRepository.findById(id).orElseThrow(() ->
                                                           new RuntimeException("Account does not exist"));

        if(account.getBalance()<  amount){
            throw new RuntimeException("Insufficient Amount");

        }
        double total =account.getBalance() -amount;
        account.setBalance(total);
        Account savedAccount =accountRepository.save(account);
        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {

        List<Account> accounts=accountRepository.findAll();

        return accounts.stream().map((account -> AccountMapper.maptoAccountDto(account))).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account=accountRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Account does not exist"));

        accountRepository.deleteById(id);
    }
}
