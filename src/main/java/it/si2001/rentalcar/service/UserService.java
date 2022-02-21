package it.si2001.rentalcar.service;

import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.entities.User;

import java.util.List;

/*
*
* Service used to managing the user
*
* */
public interface UserService {

    /*
    *  @Param user is the user to add in the db
    *
    *  this method add the user in the db
    * */
    void addUser(User user);

    /*
    *  @Param id is the id of the user to delete
    *
    *  this method delete the user from the id
    * */
    void deleteUser(int id);

    /*
    *  return a list of all user
    * */
    List<User> listUser();

    /*
    *  @Param account is the account used to find the user
    *
    *  return the found user
    *
    * */
    User findUserByAccount(Account account);

    /*
    *  @Param id is the id used to find the user
    *
    *  return the found user
    * */
    User userById(int id);

    /*
    *  @Param user is the updated user
    *
    *  this method update the user in the db
    * */
    void updateUser(User user);

}
