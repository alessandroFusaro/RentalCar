package it.si2001.rentalcar.controller;

import it.si2001.rentalcar.entities.Vehicle;
import it.si2001.rentalcar.service.ReservationService;
import it.si2001.rentalcar.service.TipologyService;
import it.si2001.rentalcar.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*
 * Rest controller for managing vehicle operations
 *
 */

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TipologyService tipologyService;

    private final Logger log = LoggerFactory.getLogger(VehicleController.class);


    /*
    * return a ResponseEntity with a list of vehicle in the body, or with the exception error
    * */
    @GetMapping("/list")
    public ResponseEntity<?> listVehicle(){

        log.debug("Rest request to get a list of vehicle");

        try
        {
            log.debug("get the list of vehicle");
            List<Vehicle> vehicles = vehicleService.listVehicle();

            return ResponseEntity.ok().body(vehicles);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }

    /*
    *   /data/{plate} get the plate of the vehicle
    *
    *   @PathVariable plate is the plate of the vehicle to retrieve
    *
    *   return a ResponseEntity with the data of the vehicle in the body, or with the exception error
    * */
    @GetMapping("/data/{plate}")
    public ResponseEntity<?> vehicleData(@Valid @PathVariable("plate") String plate){

        log.debug("Rest request to get the data of the vehicle");

        try
        {
            log.debug("get the data of the vehicle");

            Vehicle vehicle = vehicleService.vehicleByPlate(plate);

            return ResponseEntity.ok().body(vehicle);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }


    }


    /*
    *
    *   @RequestBody vehicle is the vehicle to add to db
    *
    *   Return a ResponseEntity with the vehicle data added in the body, or with the exception error
    *
    * */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addVehicle(@Valid @RequestBody Vehicle vehicle)
    {
        log.debug("Rest request to add a vehicle");

        try
        {
            log.debug("add vehicle");

            //control if exists a vehicle with this plate
            if(vehicleService.existsVehicleByPlate(vehicle.getPlate()))
            {
                return ResponseEntity.badRequest().body("Esiste già un veicolo con questa targa");
            }

            vehicleService.addVehicle(vehicle);

            return ResponseEntity.ok().body(vehicle);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }

    /*
    *   /delete/{plate} get the plate of the vehicle to delete
    *
    *   @PathVariable plate is the plate of the vehicle
    *
    *   return a ResponseEntity with the plate of the vehicle deleted in the body, or with the exception error
    * */
    @DeleteMapping(value = "/delete/{plate}")
    public ResponseEntity<?> deleteVehicle(@Valid @PathVariable("plate") String plate)
    {
        log.debug("Rest request to delete a vehicle");

        try
        {
            log.debug("delete vehicle"+plate);

            //control if there are reservation with this vehicle
            if(reservationService.existsReservationByVehicle(vehicleService.vehicleByPlate(plate)))
            {
                return ResponseEntity.ok().body("Impossibile eliminare il veicolo\nCi sono delle prenotazioni con questo veicolo");
            }

            //delete the reservation expired with this vehicle
            reservationService.removeAllByVehicle(vehicleService.vehicleByPlate(plate));

            //delete the vehicle
            vehicleService.deleteVehicle(plate);
            return ResponseEntity.ok().body("Veicolo eliminato : "+plate);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }

    /*
    *   @RequestBody vehicle is the updated vehicle
    *
    *
    *   Return a ResponseEntity with the updated vehicle in the body, or with the exception error
    * */
    @PutMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateVehicle(@Valid @RequestBody Vehicle vehicle)
    {
        log.debug("Rest request to update a vehicle");

        try
        {
            log.debug("update vehicle"+vehicle);

            vehicleService.updateVehicle(vehicle);

            return ResponseEntity.ok().body(vehicle);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }

    @GetMapping("/available")
    public ResponseEntity<?> availableVehicle()
    {
        List<Vehicle> vehicles;
        try
        {
            vehicles = vehicleService.availableVehicles();
            return ResponseEntity.ok().body(vehicles);

        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
