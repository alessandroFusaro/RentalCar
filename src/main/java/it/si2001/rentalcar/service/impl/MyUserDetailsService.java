package it.si2001.rentalcar.service.impl;

import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.entities.User;
import it.si2001.rentalcar.service.AccountService;
import it.si2001.rentalcar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    private Account account;

    private User user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Account account = accountService.findAccountByUsername(username);

        if(account != null)
        {
            setAccount(account);
            setUser(userService.findUserByAccount(account));

           //return new org.springframework.security.core.userdetails.User(account.getUsername(),account.getPassword(),new ArrayList<>());
           return new org.springframework.security.core.userdetails.User(account.getUsername(),account.getPassword(),buildSimpleGrantedAuthorities(user));

        }
        else
        {
            return null;
        }
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}
