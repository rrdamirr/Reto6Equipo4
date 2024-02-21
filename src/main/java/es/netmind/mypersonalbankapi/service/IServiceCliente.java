package es.netmind.mypersonalbankapi.service;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;

import java.util.List;

public interface IServiceCliente {

    public List<Cliente> getAll();

    public Cliente getClient(Integer id);

    public Personal createPersonal(Personal personal);
    public Empresa createEmpresa(Empresa empresa);

    public Cliente updateClient(Integer id, Cliente client);

    public void deleteClient(Integer id);


}
