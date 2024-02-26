package es.netmind.mypersonalbankapi.service;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import es.netmind.mypersonalbankapi.modelos.prestamos.Prestamo;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ServiceCliente implements IServiceCliente {

    @Autowired
    private IClientesRepoData clientesRepo;

    @PersistenceContext
    EntityManager em;

    /*public List<Cliente> getProductsByText(String text) {
        return IClientesRepoData.findByNameContaining(text);
    }*/

    @Override
    public List<Cliente> getAll() {
        List<Cliente> clientes = clientesRepo.findAll();
        if (clientes != null && clientes.size() > 0) return clientes;
        else throw new ClienteNotFoundException("La lista de clientes está vacía");
    }

    @Override
    public Cliente getClient(Integer id) {
        Cliente client = clientesRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        return client;
    }

    @Override
    public Personal createPersonal(Personal personal) {
        return clientesRepo.save(personal);
    }

    @Override
    public Empresa createEmpresa(Empresa empresa) {
        return clientesRepo.save(empresa);
    }

   /* @Override
    public Cliente updateClient(Integer id, Cliente client) {
        Cliente newCliente = clientesRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        newCliente.setDireccion(client.getDireccion());
        newCliente.setNombre(client.getNombre());
        newCliente.setAlta(client.getAlta());
        return clientesRepo.save(newCliente);
    }*/
    @Override
    public Empresa updateClientEmp(Integer id, Empresa empresa) {
        Empresa newEmpresa = (Empresa) clientesRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        newEmpresa.setDireccion(empresa.getDireccion());
        newEmpresa.setEmail(empresa.getEmail());
        newEmpresa.setNombre(empresa.getNombre());
        newEmpresa.setAlta(empresa.getAlta());
        return clientesRepo.save(newEmpresa);
    }

    @Override
    public Personal updateClientPers(Integer id, Personal personal) {
        Personal newPersonal = (Personal) clientesRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        newPersonal.setDireccion(personal.getDireccion());
        newPersonal.setEmail(personal.getEmail());
        newPersonal.setNombre(personal.getNombre());
        newPersonal.setAlta(personal.getAlta());
        return clientesRepo.save(newPersonal);
    }

    @Override
    public void deleteClient(Integer id) {
        Cliente cliente = clientesRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        this.clientesRepo.delete(cliente);
    }

    @Override
    public Boolean evaluarPrestamos(Integer id, Double cantidad) {
        Cliente client = clientesRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        Prestamo prestamo = new Prestamo(null, LocalDate.now(), cantidad, cantidad, 10, 5, false, false, 5);
        Boolean aceptado = client.evaluarSolicitudPrestamo(prestamo);
        return aceptado;
    }

}
