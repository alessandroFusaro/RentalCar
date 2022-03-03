package it.si2001.rentalcar.controller;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import it.si2001.rentalcar.entities.Tipology;
import it.si2001.rentalcar.entities.Vehicle;
import it.si2001.rentalcar.service.TipologyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

/*
 * Rest controller for managing Tipology operations
 *
 */

@OpenAPIDefinition(info = @Info(title = "Rest controller to managing the typology operations"))
@RestController
@RequestMapping(value = "/tipology")
public class TipologyController {

    @Autowired
    private TipologyService tipologyService;

    private final Logger log = LoggerFactory.getLogger(TipologyController.class);

    /*
    *  return a ResponseEntity with the list of tipologies in the body, or with the exception error
    *
    * */
    @ApiOperation(value = "Ttpology list",notes = "List of all typologies",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of typologies"),
            @ApiResponse(code = 400, message = "Error get list"),
    })
    @GetMapping("/list")
    public ResponseEntity<?> listTypologies(){

        log.debug("Rest request to get a list of tipology");

        try{
            log.debug("get the list of tipologies");

            List<Tipology> typologies = tipologyService.listTipologies();

            return ResponseEntity.ok().body(typologies);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }


    }

    /*
     *  /data/{id} get the id of the tipology to retrieve
     *
     *  @PathVariable id is the id of the tipology
     *
     *  return a ResponseEntity with the data of the tipology in the body, or with the exception error
     *
     * */
    @ApiOperation(value = "Typology data",notes = "Get the data of the typology",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Typology data"),
            @ApiResponse(code = 400, message = "Error get typology data"),
    })
    @GetMapping("/data/{id}")
    public ResponseEntity<?> tipologyData(@ApiParam(name = "id",value = "Typology id",required = true) @Valid @PathVariable("id") int id){

        log.debug("Rest request to get the data of tipology");

        try
        {
            log.debug("get the data of the tipology");

            Tipology typology = tipologyService.tipologyById(id);

            return ResponseEntity.ok().body(typology);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }

    }
}
