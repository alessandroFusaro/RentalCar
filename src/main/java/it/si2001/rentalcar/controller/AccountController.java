package it.si2001.rentalcar.controller;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;

@OpenAPIDefinition(info = @Info(title = "Rest controller to manage the account operations"))
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
    @ApiOperation(value = "Update account", notes = "Update the account using the id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account correctly updated"),
            @ApiResponse(code = 400, message = "Error update account"),
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@ApiParam(name = "account", value = "account data", required = true) @Valid @RequestBody Account account)
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
