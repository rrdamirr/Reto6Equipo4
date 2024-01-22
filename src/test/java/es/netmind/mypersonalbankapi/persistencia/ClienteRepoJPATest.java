package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRepoJPATest {

    @Autowired
    private IClientesRepo repoCliente;

    @Test
    void getAll() {
    }

    @Test
    void getClientById() {
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


        System.out.println(newCl);
        repoCliente.addClient(newCl);
        Cliente aCliente = repoCliente.getClientById(newCl.getId());
        assertEquals(aCliente.getId(), newCl.getId());



    }

    @Test
    void deleteClient() {
    }

    @Test
    void updateClient() {
    }

    @Test
    void getEm() {
    }

    @Test
    void setEm() {
    }
}