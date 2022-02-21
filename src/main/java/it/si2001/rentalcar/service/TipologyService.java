package it.si2001.rentalcar.service;

import it.si2001.rentalcar.entities.Tipology;

import java.util.List;

/*
*
*  Service used to managing the tipologies
*
* */
public interface TipologyService {

    /*
    *  @Param tipology is the tipology to add in the db
    *
    *  this method add the tipology in the db
    * */
    void addTipology(Tipology tipology);


    /*
    *  return a list of all tipologies
    * */
    List<Tipology> listTipologies();


    /*
    *  @Param id is the id of the tipology to find
    *
    *  return the found tipology
    * */
    Tipology tipologyById(int id);
}
