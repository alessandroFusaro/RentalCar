package it.si2001.rentalcar.repository;

import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.entities.Reservation;
import it.si2001.rentalcar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    List<User> findAllByRole(String role);

    User findUserById(int id);

    void deleteUserById(int id);

    User findUserByAccount(Account account);

    User findUserByReservation(Reservation reservation);

}
