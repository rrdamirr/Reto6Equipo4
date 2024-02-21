package es.netmind.mypersonalbankapi.controladores;

import es.netmind.mypersonalbankapi.exceptions.CuentaException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.cuentas.Ahorro;
import es.netmind.mypersonalbankapi.modelos.cuentas.Cuenta;
import es.netmind.mypersonalbankapi.persistencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CuentasController {
    //private static ICuentasRepo cuentasRepo = CuentasInMemoryRepo.getInstance();
    //private static IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();
    @Autowired
    private ICuentasRepoData cuentasRepo;

    @Autowired
    private IClientesRepoData clientesRepo;


    public void mostrarLista(Integer uid) {
        System.out.println("\nLista de cuentas del cliente: " + uid);
        System.out.println("───────────────────────────────────");

        try {
            List<Cuenta> cuentas = cuentasRepo.findByCliente_Id(uid);
            if (cuentas != null && cuentas.size() > 0) System.out.println(cuentas);
            else System.out.println("El cliente no tiene cuentas!");
        } catch (Exception e) {
            System.out.println("Cliente NO encontrado 😞!");
        }
    }

    public void mostrarDetalle(Integer uid, Integer aid) {
        System.out.println("\nDetalle de cuenta: " + aid + ", del cliente: " + uid);
        System.out.println("───────────────────────────────────");

        try {
            Cuenta cuenta = cuentasRepo.findByCliente_IdAndId(uid, aid);
            System.out.println(cuenta);
        } catch (Exception e) {
            System.out.println("Cuenta NO encontrada para el cliente 😞!");
        }
    }

    @Transactional
    public void eliminar(Integer uid, Integer aid) {
        System.out.println("\nBorrando cuenta: " + aid + ", para cliente: " + uid);
        System.out.println("───────────────────────────────────");

       // try {
            Cuenta cu = cuentasRepo.findByCliente_IdAndId(uid, aid);
            cuentasRepo.delete(cu);
            boolean borrado = cuentasRepo.existsById(cu.getId());
            if (!borrado) {
                Optional<Cliente> op = clientesRepo.findById(uid);
                Cliente cl = op.get();
                cl.delisgarCuenta(cu);
                System.out.println("Cuenta borrada 🙂!!");
                mostrarLista(uid);
            } else System.out.println("Cuenta NO borrada 😞!! Consulte con su oficina.");
       // } catch (CuentaException e) {
       //     System.out.println("Cuenta NO encontrado 😞! \nCode: " + e.getCode());
       // } catch (Exception e) {
        //    System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
       // }

    }

    @Transactional
    public static void add(Integer uid, String[] args) {
        System.out.println("uid: " + uid);
        for (String arg : args) {
            System.out.println(arg);
        }
    }

    @Transactional
    public static void actualizar(Integer uid, Integer aid, String[] args) {
        System.out.println("uid: " + uid);
        System.out.println("aid: " + aid);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
