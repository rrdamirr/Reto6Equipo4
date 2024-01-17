package es.netmind.mypersonalbankapi.modelos.clientes;

import es.netmind.mypersonalbankapi.controladores.ClientesController;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void validar_cliente() throws Exception {
        //Dado
        Empresa empresa = new Empresa(100, "Empresa1", "mail@gmail.com","Calle java", LocalDate.now(), true, false, "B34245632", null);

        //Cuando
        boolean retorno = empresa.validar();

        //Entonces
        assertTrue(retorno);
    }

    @Test
    void validar_cliente_nulo() throws Exception {
        //Dado
        //Empresa empresa = new Empresa(100, "", "mail@gmail.com","Calle java", LocalDate.now(), true, false, "", null);
        Empresa empresa = null;

        //Entonces
        assertThrows(Exception.class, ()->{
            //Cuando
            empresa.validar();
        });
    }

    @Test
    void validar_cliente_sin_nif() throws Exception {
        //Dado
        Empresa empresa = new Empresa(100, "Empresa1", "mail@gmail.com","Calle java", LocalDate.now(), true, false, "", null);
        //Cuando
        boolean retorno = empresa.validar();
        //Entonces
        assertFalse(retorno);

    }


    @Test
    void validar() {
    }
}