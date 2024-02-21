package es.netmind.mypersonalbankapi.service;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Override
    public Cliente updateClient(Integer id, Cliente client) {
        return null;
    }

    @Override
    public void deleteClient(Integer id) {

    }
}
