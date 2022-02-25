package it.si2001.rentalcar.controller;

import it.si2001.rentalcar.entities.Reservation;
import it.si2001.rentalcar.entities.User;
import it.si2001.rentalcar.entities.Vehicle;
import it.si2001.rentalcar.service.ReservationService;
import it.si2001.rentalcar.service.UserService;
import it.si2001.rentalcar.service.VehicleService;
import it.si2001.rentalcar.service.impl.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/*
 * Rest controller for managing Reservation operations
 *
*/

@RestController
@RequestMapping(value = "/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private VehicleService vehicleService;

    private final Logger log = LoggerFactory.getLogger(ReservationController.class);

    /*
     * return a ResponseEntity with a list of reservation in the body , or with the exception error
     */
    @GetMapping("/list")
    public ResponseEntity<?> reservationList()
    {
        log.debug("Rest request to get a list of reservation");

        try
        {
            log.debug("get the list of reservation");
            List<Reservation> reservation = reservationService.listReservation();

            return ResponseEntity.ok().body(reservation);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }


    }

    /*
     *  /data/{id} get the id of the reservation to retrieve
     *
     *  @PathVariable id is the id of the reservation to retrieve
     *
     *  return a ResponseEntity with the data of the reservation in the body , or with the exception error
     */
    @GetMapping("/data/{id}")
    public ResponseEntity<?> reservationData(@Valid @PathVariable("id") int id)
    {
        log.debug("Rest request to get the data of the reservation");

        try {
            Reservation reservation = reservationService.findReservationByReservationId(id);

            return ResponseEntity.ok().body(reservation);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }

    /*
     *  /userBookings/{userId} get the id of the user, this id is used to retrieve all the reservations of the user
     *
     *  @PathVariable userId is the id of the user
     *
     *  return a ResponseEntity with a list of user Reservations in the body, or with the exception error
     */
    @GetMapping("/userReservation/{userId}")
    public ResponseEntity<?> userReservations(@Valid @PathVariable("userId") int id)
    {
        log.debug("Rest request to get a list of the user reservations");

        try {
            log.debug("get the user by id");

            User user = userService.userById(id);

            log.debug("get the list of user reservations");
            List<Reservation> reservations = reservationService.listUserReservation(user);

            return ResponseEntity.ok().body(reservations);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }


    }

     /*
     *
     *  /approve/{id} get the id of the reservation to approve
     *
     *  @PathVariable id is the id of the reservation
     *
     *  return a ResponseEntity with the reservation approved in the body, or with the exception error
     * */
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveReservation(@Valid @PathVariable("id") int id)
    {
        log.debug("Rest request to approve a reservation");

        try {
            log.debug("get the reservation by id");

            Reservation reservation = reservationService.findReservationByReservationId(id);

            //check if the reservation is already approved
            if(reservation.getApprove() == (byte) 1)
            {
                return ResponseEntity.badRequest().body("Prenotazione gia' approvata");
            }

            //chek if the reservation is not expired
            if(reservationService.isActive(id,new Date()))
            {
                reservation.setApprove((byte) 1);

                reservationService.updateReservation(reservation);

                return ResponseEntity.ok().body(reservation);

            }
            else
            {
                return ResponseEntity.badRequest().body("Impossibile approvare la prenotazione\nPrenotazione iniziata o scaduta");
            }

        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    /*
     *
     *  /delete/{id} get the id of the reservation to delete
     *
     *  @PathVariable id is the id of the reservation
     *
     *  return a ResponseEntity with the id of the reservation deleted in the body, or with the Exception error
     * */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") int id)
    {
        log.debug("Rest request to delete a reservation");

        try {
            log.debug("delete the reservation");

            //control if the reservation is not active
            if(reservationService.reservationIsActive(id,new Date(), new Date()))
            {
                reservationService.deleteReservationById(id);

                return ResponseEntity.ok().body("Prenotazione eliminata : "+id);

            }
            else
            {
                return  ResponseEntity.badRequest().body("Impossibile eliminare la prenotazione\nPrenotazione attiva o scaduta");
            }

           }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }


    /*
     *
     *  /update/{id} get the id of the reservation to update
     *
     *  @requestBody reservation is the updated reservation
     *
     *  return a ResponseEntity with the reservation updated in the body , or with the exception error
     * */
    @PutMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> UpdateBooking(@Valid @RequestBody Reservation reservation)
    {
        log.debug("Rest request to update a reservation");

        try{
            log.debug("update the reservation");

            //get the user of the reservation
            Reservation temp = reservationService.findReservationByReservationId(reservation.getId());

            //set the user
            reservation.setUser(temp.getUser());

            //control if the reservation is not active
            if(reservationService.isActive(reservation.getId(), new Date()))
            {
                //control if the vehicle has change
                if(!temp.getVehicle().getPlate().equals(reservation.getVehicle().getPlate()))
                {
                    //set the availability
                    temp.getVehicle().setAvailability((byte) 1);

                    Vehicle vehicleRes = vehicleService.vehicleByPlate(reservation.getVehicle().getPlate());
                   vehicleRes.setAvailability((byte) 0);

                    System.out.println(temp.getVehicle().getModel()+"\n\n\n\n\n"+reservation.getVehicle().getModel());
                    vehicleService.updateVehicle(temp.getVehicle());
                    vehicleService.updateVehicle(vehicleRes);
                }

                //update the reservation
                reservationService.updateReservation(reservation);

                return ResponseEntity.ok().body(reservation);

            }
            else
            {
                return ResponseEntity.badRequest().body("Impossibile modificare la prenotazione\nPrenotazione attiva o scaduta");
            }

            }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }


    }

    /*
     *
     *  @requestBody reservation is the reservation to be inserted in the db
     *
     *  return a ResponseEntity with the added reservation in the body, or with the exception error
     * */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> AddBooking(@Valid @RequestBody Reservation reservation)
    {
        log.debug("Rest request to add a reservation");

        try {
            log.debug("add reservation");


            //control if the user have any reservation active
            if(reservationService.reservationActiveByUser(myUserDetailsService.getUser()))
            {
                return ResponseEntity.badRequest().body("Hai già una prenotazione attiva");
            }

            reservation.setUser(myUserDetailsService.getUser());

            reservationService.addReservation(reservation);

            return ResponseEntity.ok().body(reservation);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/isNotActive/{id}")
    public ResponseEntity<?> isActive(@PathVariable("id") int id)
    {
        //reservation is not active
        boolean result = true;

        result = reservationService.reservationIsActive(id, new Date(), new Date());
        System.out.println(result);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/listByVehicle/{plate}")
    public ResponseEntity<?> reservationVehicle(@PathVariable("plate") String plate)
    {

        List<Reservation> result;

        Vehicle vehicle = vehicleService.vehicleByPlate(plate);

        result = reservationService.reservationByVehicle(vehicle);
        return ResponseEntity.ok().body(result);
    }
}
