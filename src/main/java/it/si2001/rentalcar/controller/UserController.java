package it.si2001.rentalcar.controller;

import com.nimbusds.jose.Header;
import com.sun.net.httpserver.Headers;

import io.swagger.annotations.*;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.entities.Reservation;
import it.si2001.rentalcar.entities.User;
import it.si2001.rentalcar.models.AuthenticationRequest;
import it.si2001.rentalcar.models.AuthenticationResponse;
import it.si2001.rentalcar.security.JwtUtil;
import it.si2001.rentalcar.service.AccountService;
import it.si2001.rentalcar.service.ReservationService;
import it.si2001.rentalcar.service.UserService;
import it.si2001.rentalcar.service.impl.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

/*
 * Rest controller for managing user operations
 *
 */
@OpenAPIDefinition(info = @Info(title = "Rest controller to managing user operations"))
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    AccountService accountService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final Logger log = LoggerFactory.getLogger(UserController.class);


    /*
    *   @RequestBody authenticationRequest contains username and password entered by the user
    *
    *   in this method control if the account exists
    *
    *   return a response entity with the generated token in the body, or with the message error
    * */
    @ApiOperation(value = "Login user", notes = "Login user with an username and password",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login successfull"),
            @ApiResponse(code = 400, message = "Wrong username or password"),
    })
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@ApiParam(name = "Authentication request",value = "Username and password") @RequestBody AuthenticationRequest authenticationRequest) throws Exception
    {
        //log message
        log.debug("REST request to get token jwt");

        //ricavo il profilo
        Account account = accountService.findAccountByUsername(authenticationRequest.getUsername());

        //controllo se il profilo esiste
        if(account != null)
        {
            //controllo se la password inserita dall'utente ?? giusta
            if(encoder.matches(authenticationRequest.getPassword(),account.getPassword()))
            {
                //log message
                log.debug("Correct Username and password");

                UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getUsername());

                //generate the toke
                String jwt = jwtTokenUtil.generateToken(userDetails);

                //return token
                return ResponseEntity.ok(new AuthenticationResponse(jwt));
            }
            else
            {
                log.debug("Wrong password");
                return ResponseEntity.badRequest().body("Password errata");
            }
        }

        log.debug("Wrong username");
        return ResponseEntity.badRequest().body("Username errato");

    }


    /*
    * return a ResponseEntity with a list of user in the body, or with the exception error
    *
    * */
    @ApiOperation(value = "List of user", notes = "Get a list of all user tha have a user role",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User list"),
            @ApiResponse(code = 400, message = "Error get user list"),
    })
    @GetMapping("/list")
    public ResponseEntity<?> userList()
    {
        log.debug("Rest request to get a list of user");

        try{

            log.debug("retrieve a list of user");
            List<User> users = userService.listUser();

            return ResponseEntity.ok().body(users);
        }
        catch (Exception e)
        {
            log.debug("error retrieve a list of user");
            return ResponseEntity.badRequest().body(e);
        }

    }

    /*
    * return ResponseEntity with the user data in the body, or with the exception error
    *
    * */

    @ApiOperation(value = "User Data", notes = "Get the data of the logged user",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User data"),
            @ApiResponse(code = 400, message = "Error get user data"),
    })
    @GetMapping("/data")
    public ResponseEntity<?> UserData()
    {
        log.debug("Rest request to get the data of the user");

        try{
            log.debug("get the user account");

            //retrive the user account
            Account userAccount = userDetailsService.getAccount();

            log.debug("get the user account");

            //retrieve the user data
            User user = userService.findUserByAccount(userAccount);

            return ResponseEntity.ok().body(user);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }


    /*
    *  @RequestBody user is the user to be entered
    *
    *  Return a ResponseEntity with the user data added in the body, or with the exception error
    * */
    @ApiOperation(value = "Add a new user", notes = "Sign in a new user",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User correctly registered"),
            @ApiResponse(code = 400, message = "User data wrong or username already exists"),
    })
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@ApiParam(name = "User", value = "All the user data with an username and password to create an account",required = true) @Valid @RequestBody User user)
    {
        log.debug("Rest request to add a new user");
        try
        {
            log.debug("add a new user");

            //check if insert also the account of the user
            if(user.getAccount() != null)
            {
                //get the password
                String password = user.getAccount().getPassword();

                //crypt the password
                password = encoder.encode(password);

                //check if exists an account with the username
                boolean exists = accountService.existsAccount(user.getAccount().getUsername());

                if(exists)
                {
                    return ResponseEntity.badRequest().body("Username non disponibile : "+ user.getAccount().getUsername());
                }

                //set the account password
                user.getAccount().setPassword(password);

                //add the user
                userService.addUser(user);

                //set the user id in the account
                user.getAccount().setUser(userService.findUserByAccount(user.getAccount()));

                //update the id
                accountService.updateAccount(user.getAccount());

                return ResponseEntity.ok().body(user);

            }
            else {
                //if the data of the account are null return a response entity with a message error in the header and the user in the body
                return ResponseEntity.badRequest().body("Username o password mancante");
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /*
    *  /delete/{id} is the id of the user to delete
    *
    *  @PathVariable is the id of the user
    *
    *  Return a ResponseEntity with the id of the user deleted, or with the exception error
    *
    * */
    @ApiOperation(value = "Delete an user" ,notes = "Delete an user with the user Id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User correctly deleted"),
            @ApiResponse(code = 400, message = "Error delete user, he has an active reservation "),
    })
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@ApiParam(name = "id", value = "The id of the user to delete", required = true) @Valid @PathVariable("id") int id)
    {
        log.debug("Rest request to delete a user");

        try{
            log.debug("delete a user");

            //control if the user have riservation active
            if(reservationService.reservationActiveByUser(userService.userById(id)))
            {
                return ResponseEntity.badRequest().body("Impossibile eliminare l'utente\nHa una prenotazione attiva");
            }

            userService.deleteUser(id);
            return ResponseEntity.ok().body("Utente eliminato : "+id);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }


    /*
    *  @RequestBody is the updated user
    *
    *  Return a ResponseEntity with the updated user in the body, or with the exception error
    * */
    @ApiOperation(value = "Update user",notes = "Update the user data", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User correctly updated"),
            @ApiResponse(code = 400, message = "User data wrong"),
            @ApiResponse(code = 400, message = "Username already exists"),
    })
    @PutMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@ApiParam(name = "user",value = "the user to update", required = true) @Valid @RequestBody User user)
    {
        log.debug("Rest request to update a user");

        try{
            //retrieve the user account
            Account account = accountService.findAccountByUser(user);

            //control if he changed the password
            if(!user.getAccount().getPassword().startsWith("$2a$10$"))
            {
                //encrypt the password
                String psw = encoder.encode(user.getAccount().getPassword());
                user.getAccount().setPassword(psw);

                account.setPassword(psw);
            }

            boolean exists = false;

            //control if the user has changed the username
            if(!user.getAccount().getUsername().equals(account.getUsername()))
            {
                //check if exists an account with the username
                exists = accountService.existsAccount(user.getAccount().getUsername());
            }

            if(exists)
            { System.out.println(exists+"\n\n\n\n\n\n\n\n\n"+user.getAccount().getUsername()+"\n\n\n\n\n\n\n");
                return ResponseEntity.badRequest().body("Username non disponibile : "+ user.getAccount().getUsername());
            }

            account.setUsername(user.getAccount().getUsername());

            //set to the user the account
            user.setAccount(account);

            //update the user
            userService.updateUser(user);

            return ResponseEntity.ok().body(user);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @ApiOperation(value = "Get user", notes = "get the user with the id",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User correctly registered"),
            @ApiResponse(code = 400, message = "User data wrong or username already exists"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> userById(@PathVariable("id") int id)
    {
        try {
            User user = userService.userById(id);
            return ResponseEntity.ok().body(user);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
