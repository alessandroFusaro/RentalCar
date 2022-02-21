package it.si2001.rentalcar.service;

import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.entities.User;

/*
 *
 * Service interface used to managing the account
 *
 */
public interface AccountService {

    /*
     *  Update the data of the account
     *
     *  @Param account is the account to update
     */
    void updateAccount(Account account);


    /*
    *  @Param id is the id of the account to find
    *
    *  returns the found account
    * */
    Account findAccountByAccountId(int id);

    /*
     *  @Param username is the username used to fin the account
     *
     *  return the found account
     */
    Account findAccountByUsername(String username);

    /*
    *  @Param user is the user used to find the account
    *
    *  return the found account
    * */
    Account findAccountByUser(User user);

    /*
    *  @Param username is the username used to control if an account have the same username
    *
    *  return true if exists an account with the same username, false if not exists
    * */
    boolean existsAccount(String username);
}

