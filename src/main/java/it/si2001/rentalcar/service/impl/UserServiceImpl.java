package it.si2001.rentalcar.service.impl;

import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.entities.User;
import it.si2001.rentalcar.repository.UserRepository;
import it.si2001.rentalcar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/*
*
* Implementation of the UserService interface
*
* */

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    /*
     *  @Param user is the user to add in the db
     *
     *  this method add the user in the db
     * */
    @Override
    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }

    /*
     *  @Param id is the id of the user to delete
     *
     *  this method delete the user from the id
     * */
    @Override
    public void deleteUser(int id) {
        userRepository.deleteUserById(id);
    }

    /*
     *  return a list of all user
     * */
    @Override
    public List<User> listUser()
    {
        return userRepository.findAllByRole("user");
    }

    /*
     *  @Param account is the account used to find the user
     *
     *  return the found user
     *
     * */
    @Override
    public User findUserByAccount(Account account) {
        return userRepository.findUserByAccount(account);
    }

    /*
     *  @Param id is the id used to find the user
     *
     *  return the found user
     * */
    @Override
    public User userById(int id) {
        return userRepository.findUserById(id);
    }

    /*
     *  @Param user is the updated user
     *
     *  this method update the user in the db
     * */
    @Override
    public void updateUser(User user) {

        userRepository.save(user);
    }

}
