package it.si2001.rentalcar.repository;

import it.si2001.rentalcar.entities.Tipology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipologyRepository extends JpaRepository<Tipology, Long>
{

    Tipology findTipologyById(int id);

    void deleteTipologyById(int id);
}
