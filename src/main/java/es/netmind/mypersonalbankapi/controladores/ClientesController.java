package es.netmind.mypersonalbankapi.controladores;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.prestamos.Prestamo;
import es.netmind.mypersonalbankapi.persistencia.*;
import es.netmind.mypersonalbankapi.utils.ClientesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientesController {


     @Autowired
    private static IClientesRepoData clientesRepo;

  /*  static {
        try {
            clientesRepo = ClientesInDBRepo.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    private static ICuentasRepo cuentasRepo = CuentasInMemoryRepo.getInstance();
    private static IPrestamosRepo prestamosRepo = PrestamosInMemoryRepo.getInstance();

    public static void mostrarLista() throws Exception {
        System.out.println("\nLista de clientes:");
        System.out.println("───────────────────────────────────");
        List<Cliente> clientes = clientesRepo.findAll();
        for (Cliente cl : clientes) {

            try {
                cl.validar();
                System.out.println("(" + cl.getId() + ") " + cl.getNombre() + " " + cl.getId());
            } catch (ClienteException e) {
                System.out.println("El cliente solicitado tiene datos erroneos 😞! Ponte en contacto con el admin. \nCode: " + e.getCode());
            } catch (Exception e) {
                System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
            }

        }
    }

    public static void mostrarDetalle(Integer uid) {
        System.out.println("\nDetalle de cliente: " + uid);
        System.out.println("───────────────────────────────────");

       // try {
            Optional<Cliente> op = clientesRepo.findById(uid);
            Cliente cl = op.get();
            System.out.println(cl);
        /*} catch (ClienteException e) {
            System.out.println("Cliente NO encontrado 😞! \nCode: " + e.getCode());
        } catch (Exception e) {
        /*    System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
        }*/

    }

    @Transactional
    public void add(String[] args) {
        System.out.println("\nAñadiendo cliente");
        System.out.println("───────────────────────────────────");
        try {
            Cliente cl = ClientesUtils.extractClientFromArgsForCreate(args);
            clientesRepo.save(cl);
            System.out.println("Cliente añadido: " + cl + " 🙂");
            mostrarLista();
        } catch (ClienteException e) {
            System.out.println("ME voy por aqui 1");
            System.out.println("Cliente NO válido 😞! \nCode: " + e.getCode());
        } catch (DateTimeException e) {
            System.out.println("ME voy por aqui 2");
            System.out.println("⚠ LAS FECHAS DEBEN TENER EL FORMATO yyyy-mm-dd, por ejemplo 2023-12-01 ⚠");
        } catch (Exception e) {
            System.out.println("ME voy por aqui 3");
            System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
            e.printStackTrace();
        }

    }

    @Transactional
    public static void eliminar(Integer uid) {
        System.out.println("\nBorrando cliente: " + uid);
        System.out.println("───────────────────────────────────");

        try {
            Optional<Cliente> op = clientesRepo.findById(uid);
            Cliente cl = op.get();
            clientesRepo.delete(cl);
            Optional<Cliente> op2 = clientesRepo.findById(uid);
            Cliente cl2 = op2.get();
            if (cl2 == null) {
                System.out.println("Cliente borrado 🙂!!");
                mostrarLista();
            } else System.out.println("Cliente NO borrado 😞!! Consulte con su oficina.");
        } catch (ClienteException e) {
            System.out.println("Cliente NO encontrado 😞! \nCode: " + e.getCode());
        } catch (Exception e) {
            System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
        }

    }

    @Transactional
    public static void actualizar(Integer uid, String[] args) {
        System.out.println("\nActualizando cliente: " + uid);
        System.out.println("───────────────────────────────────");

        try {
            Optional<Cliente> op = clientesRepo.findById(uid);
            Cliente cl = op.get();
            System.out.println("cl.getClass():" + cl.getClass() + " " + cl);
            ClientesUtils.updateClientFromArgs(cl, args);
            clientesRepo.save(cl);
            System.out.println("Cliente actualizado 🙂!!");
            System.out.println(cl);
            mostrarLista();
        } catch (ClienteException e) {
            System.out.println("Cliente NO encontrado 😞! \nCode: " + e.getCode());
        } catch (DateTimeException e) {
            System.out.println("⚠ LAS FECHAS DEBEN TENER EL FORMATO yyyy-mm-dd, por ejemplo 2023-12-01 ⚠");
        } catch (Exception e) {
            System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
            e.printStackTrace();
        }

    }

    public static void evaluarPrestamo(Integer uid, Double cantidad) {
        System.out.println("\nEvaluando préstamos de " + cantidad + " EUR para el  cliente: " + uid);
        System.out.println("───────────────────────────────────");

       // try {
            Optional<Cliente> op = clientesRepo.findById(uid);
            Cliente cliente = op.get();
            System.out.println("Saldo total del cliente: " + cliente.obtenerSaldoTotal());
            int numPrestamos = cliente.getPrestamos() != null ? cliente.getPrestamos().size() : 0;
            System.out.println("Número total de préstamos del cliente: " + numPrestamos);

            Prestamo prestamoSolictado = new Prestamo(null, LocalDate.now(), cantidad, cantidad, 10, 5, false, false, 5);

            boolean aceptable = cliente.evaluarSolicitudPrestamo(prestamoSolictado);
            if (aceptable) System.out.println("SÍ se puede conceder 🙂!!");
            else System.out.println("NO puede conceder 😞!! Saldo insuficiente.");

       /* } catch (ClienteException e) {
            System.out.println("Cliente NO encontrado 😞! \nCode: " + e.getCode());
        } catch (Exception e) {
            System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
            e.printStackTrace();
        }*/


    }
}
