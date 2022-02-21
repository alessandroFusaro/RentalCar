package it.si2001.rentalcar.controller;

import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    /*
     *   @PathVariable id is the id of the account
     *
     *   this method update the data of the account
     *
     *   return a ResponseEntity with the updated account in the body
     *
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody Account account)
    {
        try
        {
            //set the user id
            account.setUser(accountService.findAccountByAccountId(account.getId()).getUser());

            //crypt the password
            String password = encoder.encode(account.getPassword());

            //set the password
            account.setPassword(password);

            //update the account
            accountService.updateAccount(account);

            return ResponseEntity.ok().body(account);

        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
