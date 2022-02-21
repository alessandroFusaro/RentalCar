package it.si2001.rentalcar.service;

import it.si2001.rentalcar.entities.Vehicle;

import java.util.List;

/*
*
* Service used to managing the vehicle
*
* */
public interface VehicleService {

    /*
    *  @Param vehicle is the vehicle to add to db
    *
    *  this method add the vehicle in the db
    * */
    void addVehicle(Vehicle vehicle);


    /*
    *  @Param plate is the plate of the vehicle to delete
    *
    *  this method delete a vehicle using the plate
    * */
    void deleteVehicle(String plate);

    /*
    *  this method get a list of vehicle
    *
    *  return a list of the all vehicle
    * */
    List<Vehicle> listVehicle();

    /*
    *  @Param plate is the plate of the vehicle to find
    *
    *  return the found vehicle
    *
    * */
    Vehicle vehicleByPlate(String plate);

    /*
    *  @Param vehicle is the vehicle to update
    *
    *  this method update the vehicle in the db
    *
    * */
    void updateVehicle(Vehicle vehicle);

    /*
    *  @Param plate is the plate used to control if exists a vehicle with the same plate
    *
    *  return true if exists a vehicle with this plate otherwise false
    * */
    boolean existsVehicleByPlate(String plate);

    List<Vehicle> availableVehicles();

}
