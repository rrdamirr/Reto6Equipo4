package es.netmind.mypersonalbankapi.service;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ServiceCliente {

    @Autowired
    private IClientesRepoData clientesRepo;

    @PersistenceContext
    EntityManager em;

    /*public List<Cliente> getProductsByText(String text) {
        return IClientesRepoData.findByNameContaining(text);
    }*/

    public List<Cliente> getAll() throws ClienteException {
        List<Cliente> clientes = clientesRepo.findAll();
        if (clientes != null && clientes.size() > 0) return clientes;
        else throw new ClienteException("La lista de clientes está vacía");
    }
}
