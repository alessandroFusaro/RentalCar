package it.si2001.rentalcar.repository;

import it.si2001.rentalcar.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>
{
        Vehicle findVehicleByPlate(String plate);

        void deleteVehicleByPlate(String targa);

        boolean existsVehicleByPlate(String plate);

        List<Vehicle> findVehicleByAvailability(Byte availability);


}
