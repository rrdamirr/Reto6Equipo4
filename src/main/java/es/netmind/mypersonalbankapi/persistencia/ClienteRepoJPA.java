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
    public List<Cliente> getAll() throws RuntimeException {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
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
    @Transactional
    public Cliente updateClient(Cliente cliente) throws Exception {
        Cliente uCl = em.find(Cliente.class, cliente.getId());

        uCl.setNombre(cliente.getNombre());
        uCl.setDireccion(cliente.getDireccion());
        return uCl;
    }
}
