package it.si2001.rentalcar.service.impl;

import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.entities.User;
import it.si2001.rentalcar.repository.AccountRepository;
import it.si2001.rentalcar.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/*
 *
 * Implementation of the AccountService
 *
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;


    /*
     *  Update the data of the account
     *
     *  @Param account is the account to update
     */

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    /*
     *  @Param id is the id of the account to find
     *
     *  returns the found account
     * */
    @Override
    public Account findAccountByAccountId(int id) {
        return accountRepository.findAccountById(id);
    }

    /*
     *  @Param username is the username used to fin the account
     *
     *  return the found account
     */
    @Override
    public Account findAccountByUsername(String username)
    {
        return accountRepository.findAccountByUsername(username);
    }

    /*
     *  @Param user is the user used to find the account
     *
     *  return the found account
     * */
    @Override
    public Account findAccountByUser(User user)
    {
        return accountRepository.findAccountByUser(user);
    }

    /*
     *  @Param username is the username used to control if an account have the same username
     *
     *  return true if exists an account with the same username, false if not exists
     * */
    @Override
    public boolean existsAccount(String username)
    {
        return accountRepository.existsAccountByUsername(username);
    }
}
