package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientesRepoData extends JpaRepository<Cliente, Integer>{
    //public List<Cliente> getAll() throws Exception;

    //public Cliente getClientById(Integer id) throws Exception;

    //public Cliente addClient(Cliente cliente) throws Exception;

    //public boolean deleteClient(Cliente cliente) throws Exception;

    //public Cliente updateClient(Cliente cliente) throws Exception;
}
