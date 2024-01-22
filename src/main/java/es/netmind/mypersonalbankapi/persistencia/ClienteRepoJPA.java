package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import lombok.*;
import org.springframework.stereotype.*;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Setter
@Getter
@Repository
public class ClienteRepoJPA implements IClientesRepo{

    @PersistenceContext  // Accede al emf; emf.createEntityManager();
    private EntityManager em;

    @Override
    public List<Cliente> getAll() throws Exception {
        return null;
    }

    @Override
    public Cliente getClientById(Integer id) throws Exception {
        return em.find(Cliente.class, id);
    }

    @Override
    @Transactional
    public Cliente addClient(Cliente cliente) {
        em.persist(cliente);
        return cliente;
    }

    @Override
    public boolean deleteClient(Cliente cliente) throws Exception {
        return false;
    }

    @Override
    public Cliente updateClient(Cliente cliente) throws Exception {
        return null;
    }
}
