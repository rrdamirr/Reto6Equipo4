package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.cuentas.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface ICuentasRepoData extends JpaRepository<Cuenta, Integer> {

    public List<Cuenta> findByCliente_Id(int id);

    public Cuenta findByCliente_IdAndId(int cliente_id, int cuenta_id);
}
