package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.config.SpringConfig;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class ClienteRepoJPATest {

    @Autowired
    private IClientesRepo repoCliente;

    @Test
    void getAll() throws Exception {
        List<Cliente> clientes;

        clientes = repoCliente.getAll();
        System.out.println("Numero de clientes: " +clientes.size());

        System.out.println(clientes);

        assertNotNull(clientes);

    }

    @Test
    void getClientById() throws Exception {
        Cliente Cl = repoCliente.getClientById(1);
        System.out.println(Cl);
        assertNotNull(Cl);
    }


    @ParameterizedTest
    @CsvSource({"Personal,Equipo4_Nuevo,e4@gmail.com,Calle cuatro,12345678Z", "Empresa,Empresa4 SA,emp4@gmail.com,Calle Empresa cuatro ,B12345678"})
    void addClient(String tipoCliente, String nombre, String mail, String direccion, String idDocumento) throws Exception {

        Cliente newCl = null;

        if (Objects.equals(tipoCliente, "Personal")) {
            newCl = new Personal(null, nombre, mail, direccion, LocalDate.now(), true, false, idDocumento);
        } else {
            newCl = new Empresa(null, nombre, mail, direccion, LocalDate.now(), true, false, idDocumento, null );
        }
        repoCliente.addClient(newCl);
        Cliente aCliente = repoCliente.getClientById(newCl.getId());
        assertEquals(aCliente.getId(), newCl.getId());

    }

    @Test
    void updateClient() throws Exception {
        Cliente clUpd = repoCliente.getClientById(2);
        boolean TipoPersonal = clUpd instanceof Personal;

        Cliente newCl = null;
        if (TipoPersonal) {
            newCl = new Personal(clUpd.getId(), "4Fantasticos", clUpd.getEmail(), "La calle de la Risa", LocalDate.now(), true, false, ((Personal) clUpd).getDni());
        } else {
            newCl = new Empresa(clUpd.getId(), "Fantastic S.L.", clUpd.getEmail(), "La calle empresarial", LocalDate.now(), true, false, ((Empresa) clUpd).getCif(), null );
        }

        Cliente clCambiado = repoCliente.updateClient(newCl);
        assertNotNull(newCl);
        assertEquals(newCl.getNombre(), clCambiado.getNombre());
        assertEquals(newCl.getDireccion(), clCambiado.getDireccion());
    }

    @Test
    void deleteClient() {
    }


}