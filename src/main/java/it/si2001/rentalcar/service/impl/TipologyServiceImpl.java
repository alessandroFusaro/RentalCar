package it.si2001.rentalcar.service.impl;

import it.si2001.rentalcar.entities.Tipology;
import it.si2001.rentalcar.repository.TipologyRepository;
import it.si2001.rentalcar.service.TipologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/*
*
* Implementation of the TipologyService interface
*
* */

@Service
@Transactional
public class TipologyServiceImpl implements TipologyService {

    @Autowired
    TipologyRepository tipologiaDao;


    /*
     *  @Param tipology is the tipology to add in the db
     *
     *  this method add the tipology in the db
     * */
    @Override
    public void addTipology(Tipology tipology) {
        tipologiaDao.saveAndFlush(tipology);
    }


    /*
     *  return a list of all tipologies
     * */
    @Override
    public List<Tipology> listTipologies() {
        return tipologiaDao.findAll();
    }


    /*
     *  @Param id is the id of the tipology to find
     *
     *  return the found tipology
     * */
    @Override
    public Tipology tipologyById(int id) {
        return tipologiaDao.findTipologyById(id);
    }

}
