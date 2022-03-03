package it.si2001.rentalcar.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.Info;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

/*
 * Rest controller for managing vehicle operations
 *
 */

@OpenAPIDefinition(info = @Info(title = "Rest controller to managing vehicle operations"))
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
    @ApiOperation(value = "Vehicle list", notes = "Get a list of all vehicles", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vehicle list"),
            @ApiResponse(code = 400, message = "Error get vehicle list"),
    })
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
    @ApiOperation(value = "Vehicle data",notes = "Get the data of the vehicle using the plate",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vehicle data"),
            @ApiResponse(code = 400, message = "Error get vehicle data"),
    })
    @GetMapping("/data/{plate}")
    public ResponseEntity<?> vehicleData(@ApiParam(name ="plate" ,value = "plate of the vehicle",required = true) @Valid @PathVariable("plate") String plate){

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
    @ApiOperation(value = "Add vehicle",notes = "Add a new vehicle" ,response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "vehicle correctly registered"),
            @ApiResponse(code = 400, message = "vehicle data wrong or already exists a this plate"),
    })
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addVehicle(@ApiParam(name = "vehicle" ,value = "Vehicle data",required = true) @Valid @RequestBody Vehicle vehicle)
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
    @ApiOperation(value = "Delete vehicle" ,notes = "Delete a vehcile using the plate",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "vehicle correctly deleted"),
            @ApiResponse(code = 400, message = "Error delete vehicle"),
    })
    @DeleteMapping(value = "/delete/{plate}")
    public ResponseEntity<?> deleteVehicle(@ApiParam(name ="plate" , value ="plate of teh vehivle" ,required = true) @Valid @PathVariable("plate") String plate)
    {
        log.debug("Rest request to delete a vehicle");

        try
        {
            log.debug("delete vehicle"+plate);

            //control if there are reservation with this vehicle
            if(reservationService.existsReservationByVehicle(vehicleService.vehicleByPlate(plate)))
            {
                return ResponseEntity.ok().body("Impossibile eliminare il veicolo.\nCi sono delle prenotazioni con questo veicolo");
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
    @ApiOperation(value ="Update vehicle" ,notes = "Update the vehicle data",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vehicle correctly Updated"),
            @ApiResponse(code = 400, message = "Error update vehicle"),
    })
    @PutMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateVehicle(@ApiParam(name ="vehicle" ,value ="vehicle data" ,required = true)@Valid @RequestBody Vehicle vehicle)
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

    @ApiOperation(value = "Available vehicles",notes = "list of the all available vehicles" ,response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of the available vehicle"),
            @ApiResponse(code = 400, message = "Error get list"),
    })
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
