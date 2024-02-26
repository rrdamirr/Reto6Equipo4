package es.netmind.mypersonalbankapi.controladores;

import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepoData;
import es.netmind.mypersonalbankapi.service.ServiceCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/clients")
@Validated
@Tag(name = "Clients API", description = "Clients management APIs")
public class ClientesControlerAPI {

    private static final Logger logger = LoggerFactory.getLogger(ClientesControlerAPI.class);

    @Autowired
    private ServiceCliente service;

    @Autowired
    private IClientesRepoData clientesRepo;

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Cliente>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get a client by id", description = "Returns a client as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The client was not found")
    })
    @RequestMapping(value = "/{cid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Object getOne(
            @Parameter(name = "id", description = "Product id", example = "1", required = true)
            @PathVariable("cid") @Min(1) Integer id
    ) {
        // return clientesRepo.findById(id).get();
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.getClient(id));
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value = "/personal", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Personal> savePersonal(@RequestBody @Valid Personal newPersonal) {
        logger.info("newProduct:" + newPersonal);
        newPersonal.setId(null);
        return new ResponseEntity<>(service.createPersonal(newPersonal), HttpStatus.CREATED);
    }

    @PostMapping(value = "/empresa", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Empresa> saveEmpresa(@RequestBody @Valid Empresa newEmpresa) {
        logger.info("newProduct:" + newEmpresa);
        newEmpresa.setId(null);
        return new ResponseEntity<>(service.createEmpresa(newEmpresa), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{cid}")
    public ResponseEntity delete(@PathVariable("cid") @Min(1) Integer id) {
        this.service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/empresa/{cid}")
    public ResponseEntity<Empresa> update(@RequestBody Empresa empresa,@PathVariable("cid") @Min(1) Integer id) {
        empresa.setId(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateClientEmp(id, empresa));
    }

    @PutMapping("/personal/{cid}")
    public ResponseEntity<Personal> update(@RequestBody Personal personal,@PathVariable("cid") @Min(1) Integer id) {
        personal.setId(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateClientPers(id, personal));
    }
}

