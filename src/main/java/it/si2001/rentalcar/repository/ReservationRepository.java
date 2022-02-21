package it.si2001.rentalcar.repository;

import it.si2001.rentalcar.entities.Reservation;
import it.si2001.rentalcar.entities.User;
import it.si2001.rentalcar.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>
{

    List<Reservation> findAllByUser(User user);

    Reservation findReservationById(int id);

    List<Reservation> findAllByUserAndEndDateIsAfter(User user, Date data);

    Reservation findAllByIdAndEndDateIsBefore(int id, Date data);

    List<Reservation> findAllByVehicle(Vehicle vehicle);

    Reservation findReservationByVehicleAndEndDateIsAfter(Vehicle vehicle, Date date);

    void deleteReservationById(int id);

    boolean existsReservationByIdAndStartDateIsAfter(int id, Date date);

    boolean existsReservationByIdAndStartDateIsAfterOrEndDateIsBefore(int id,Date date, Date date2);

    boolean existsReservationByUserAndStartDateIsAfterOrEndDateIsAfterAndUser_Id(User user, Date date, Date date2, int id);

    boolean existsReservationByVehicleAndStartDateIsAfterOrEndDateIsAfterAndVehicle_Plate(Vehicle vehicle, Date date, Date date2, String plate);

    void removeAllByVehicle(Vehicle veicolo);

    List<Reservation> findReservationByVehicle(Vehicle vehicle);

}
