package it.si2001.rentalcar.service.impl;

import it.si2001.rentalcar.entities.Vehicle;
import it.si2001.rentalcar.repository.VehicleRepository;
import it.si2001.rentalcar.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/*
*
* Implementation of the VehicleService interface
*
* */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    /*
     *  @Param vehicle is the vehicle to add to db
     *
     *  this method add the vehicle in the db
     * */
    @Override
    public void addVehicle(Vehicle vehicle)
    {
        vehicleRepository.saveAndFlush(vehicle);
    }

    /*
     *  @Param plate is the plate of the vehicle to delete
     *
     *  this method delete a vehicle using the plate
     * */
    @Override
    public void deleteVehicle(String plate) {
        vehicleRepository.deleteVehicleByPlate(plate);
    }

    /*
     *  this method get a list of vehicle
     *
     *  return a list of the all vehicle
     * */
    @Override
    public List<Vehicle> listVehicle() {
        return vehicleRepository.findAll();
    }

    /*
     *  @Param plate is the plate of the vehicle to find
     *
     *  return the found vehicle
     *
     * */
    @Override
    public Vehicle vehicleByPlate(String plate) {
        return vehicleRepository.findVehicleByPlate(plate);
    }

    /*
     *  @Param vehicle is the vehicle to update
     *
     *  this method update the vehicle in the db
     *
     * */
    @Override
    public void updateVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    /*
     *  @Param plate is the plate used to control if exists a vehicle with the same plate
     *
     *  return true if exists a vehicle with this plate otherwise false
     * */
    @Override
    public boolean existsVehicleByPlate(String plate) {
        return vehicleRepository.existsVehicleByPlate(plate);
    }

    @Override
    public List<Vehicle> availableVehicles() {
        return vehicleRepository.findVehicleByAvailability((byte) 1);
    }

}
