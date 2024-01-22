package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.cuentas.Cuenta;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Setter
@Getter
@Repository
public class CuentaRepoJPA implements ICuentasRepo{

    @PersistenceContext  // Accede al emf; emf.createEntityManager();
    private EntityManager em;

    @Override
    public List<Cuenta> getAll() throws RuntimeException{
        return em.createQuery("SELECT c FROM Cuenta c", Cuenta.class).getResultList();
    }

    @Override
    public Cuenta getAccountById(Integer id) throws Exception {
        return em.find(Cuenta.class, id);
    }

    @Override
    @Transactional
    public Cuenta addAccount(Cuenta cuenta) throws Exception {
        em.persist(cuenta);
        return cuenta;
    }

    @Override
    public boolean deleteAccount(Cuenta cuenta) throws Exception {
        return false;
    }

    @Override
    public Cuenta updateAccount(Cuenta cuenta) throws Exception {
        return null;
    }

    @Override
    public List<Cuenta> getAccountsByClient(Integer uid) throws Exception {
        return null;
    }

    @Override
    public Cuenta getAccountsByClientAndId(Integer uid, Integer aid) throws Exception {
        return null;
    }
}
