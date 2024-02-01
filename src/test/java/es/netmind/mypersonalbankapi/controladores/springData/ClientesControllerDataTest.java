package es.netmind.mypersonalbankapi.controladores.springData;

import es.netmind.mypersonalbankapi.config.SpringConfig;
import es.netmind.mypersonalbankapi.controladores.ClientesController;
import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import es.netmind.mypersonalbankapi.persistencia.*;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
public class ClientesControllerDataTest {
    @Autowired
    private IClientesRepoData repoCliente;

    @Autowired
    private ClientesController clientesController;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void dado_clienteValido_cuando_alta_cliente_entonces_se_añade_en_repositorio() throws Exception {
        System.out.println("Prueba inicial!!!!!!!");
        //Dado
        String fechaHoy = String.valueOf(LocalDate.now());
        System.out.println("Fecha:" + fechaHoy );

        String [] persona={"personal", "Juan Juanez", "jj@j.com", "Calle JJ 1", fechaHoy, "12345678J"};

       //IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();
       //IClientesRepo clientesRepo = repoCliente.getInstance();
       List<Cliente> clientes = repoCliente.findAll();
       int numClientes = clientes.size();
       System.out.println("Numero clientes ini: " +numClientes);
        //Cuando
        clientesController.add(persona);
        List<Cliente> clientes_new = repoCliente.findAll();

        System.out.println("NumClientes nuevo: " + clientes_new.size() );

        //Entonces
        assertEquals(numClientes+1, clientes_new.size());

    }

    @ParameterizedTest
    @CsvSource({"personal,J,jjj@.com,Calle JJ 1,2023-10-23,12345678J",
                "personal,Juan Juanez,jjj.com,Calle JJ 1,2023-10-23,12345678J",
                "personal,Juan Juanez,jjj@.com,Calle JJ 1,2023-10-23,"})

    void dado_clienteNoValido_cuando_alta_cliente_entonces_NOK(String tipoCliente, String nombre,
                                                                            String email, String direccion, LocalDate fechaHoy, String dni) throws DateTimeException, Exception, ClienteException, NumberFormatException {
        //Dado Parametro entrada
        String [] persona={tipoCliente, nombre, email, direccion, fechaHoy.toString(), dni};

        System.out.println("persona: " + persona);

        //Cuando
        clientesController.add(persona);

        //Entonces
        System.out.println(outContent);
        assertThat(outContent.toString(), containsString("Cliente NO válido"));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
