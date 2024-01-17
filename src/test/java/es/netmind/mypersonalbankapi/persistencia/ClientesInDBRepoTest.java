package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientesInDBRepoTest {

    private IClientesRepo repo;

    @BeforeEach
    void setUp() throws Exception {
        repo = new ClientesInDBRepo();
    }


    @ParameterizedTest
    @CsvSource({"Personal,Equipo4_Nuevo,e4@gmail.com,Calle cuatro,12345678Z", "Empresa,Empresa4 SA,emp4@gmail.com,Calle Empresa cuatro ,B12345678"})
    void dadoAltaNuevoClienteCuandoClienteValidoEntoncesOK(String tipoCliente, String nombre, String mail, String direccion, String idDocumento) throws Exception {

        Cliente altaUsuario = null;

        if (Objects.equals(tipoCliente, "Personal")) {
            altaUsuario = new Personal(null, nombre, mail, direccion, LocalDate.now(), true, false, idDocumento);
        } else {
            altaUsuario = new Empresa(null, nombre, mail, direccion, LocalDate.now(), true, false, idDocumento, null );
        }

        repo.addClient(altaUsuario);

        System.out.println(altaUsuario);

        assertThat(altaUsuario.getId(), greaterThan(0));

    }

    @Test
    void dadoAltaNuevoClienteCuandoClienteNoValidoEntoncesExcepcion() throws Exception {

        Cliente altaUsuario = null;

        assertThrows(Exception.class, ()->{
            repo.addClient(altaUsuario);
        });
    }

    @Test
    void dadoUnUsuarioCuandoQuiereListaClientesEntoncesOK() throws Exception {
        List<Cliente> clientes;

        clientes = repo.getAll();
        System.out.println("Numero de clientes: " +clientes.size());

        System.out.println(clientes);

        assertTrue(true);

    }

    @Test
    void dadoUnUsuarioCuandoQuiereConsultarUnClienteEntoncesOK() throws Exception {
        Cliente cliente;

        cliente = repo.getClientById(1);

        System.out.println(cliente);

        assertThat(cliente.getId(), is(1));

    }

    @Test
    void dadoUnUsuarioCuandoQuiereConsultarUnClienteNoExistenteEntoncesClienteNull() throws Exception {
        Cliente cliente = repo.getClientById(998);
        assertNull(cliente);

    }

}