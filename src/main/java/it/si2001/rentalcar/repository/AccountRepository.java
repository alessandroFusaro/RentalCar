package it.si2001.rentalcar.repository;

import it.si2001.rentalcar.entities.Account;
import it.si2001.rentalcar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>
{
    Account findAccountById(int id);

    Account findAccountByUsername(String username);

    Account findAccountByUser(User user);

    boolean existsAccountByUsername(String username);

    void deleteAccountById(int id);

}
