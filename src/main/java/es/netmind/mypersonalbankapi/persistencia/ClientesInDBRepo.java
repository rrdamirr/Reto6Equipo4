package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.exceptions.ErrorCode;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import es.netmind.mypersonalbankapi.properties.PropertyValues;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesInDBRepo implements IClientesRepo{
    private static ClientesInDBRepo instance;


    private static String db_url = null;

    public ClientesInDBRepo() throws Exception {
        PropertyValues props = new PropertyValues();
        db_url = props.getPropValues().getProperty("db_url");

    }

     public static ClientesInDBRepo getInstance() throws Exception {
        if (instance == null) instance = new ClientesInDBRepo();
        return instance;
    }


    @Override
    public List<Cliente> getAll() throws Exception {
        List<Cliente> clientes = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cliente")
        ) {
            String [] uniNeg = null;
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipoCliente = rs.getString("dtype");
                if (tipoCliente.equals("Personal")) {
                    clientes.add(
                            new Personal(
                                    rs.getInt("id"),
                                    rs.getString("nombre"),
                                    rs.getString("email"),
                                    rs.getString("direccion"),
                                    rs.getDate("alta").toLocalDate(),
                                    rs.getBoolean("activo"),
                                    rs.getBoolean("moroso"),
                                    rs.getString("dni")
                            )
                    );
                } else {
                    if (rs.getString("unidades_de_negocio") != null) uniNeg = rs.getString("unidades_de_negocio").split("");
                    clientes.add(
                            new Empresa(
                                    rs.getInt("id"),
                                    rs.getString("nombre"),
                                    rs.getString("email"),
                                    rs.getString("direccion"),
                                    rs.getDate("alta").toLocalDate(),
                                    rs.getBoolean("activo"),
                                    rs.getBoolean("moroso"),
                                    rs.getString("cif"),
                                    uniNeg

                            )
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        //System.out.println("Clientes : " + clientes);
        return clientes;
    }

     @Override
    public Cliente getClientById(Integer id) throws Exception {
        Cliente cliente = null;
        String [] uniNeg = null;

        try (
                Connection conn = DriverManager.getConnection(db_url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM cliente c WHERE c.id ='" + id + "' LIMIT 1")
        ) {
            if (rs.next()) {
                String tipoCliente = rs.getString("dtype");
                if (tipoCliente.equals("Personal")) {
                    cliente = new Personal(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("direccion"),
                            rs.getDate("alta").toLocalDate(),
                            rs.getBoolean("activo"),
                            rs.getBoolean("moroso"),
                            rs.getString("dni")
                    );
                } else {
                    if (rs.getString("unidades_de_negocio") != null)
                        uniNeg = rs.getString("unidades_de_negocio").split("");
                    cliente = new Empresa(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("direccion"),
                            rs.getDate("alta").toLocalDate(),
                            rs.getBoolean("activo"),
                            rs.getBoolean("moroso"),
                            rs.getString("cif"),
                            uniNeg
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return cliente;
    }

    @Override
    public Cliente addClient(Cliente cliente) throws Exception {
        String sql = "INSERT INTO cliente values (?,NULL,?,?,?,?,?,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            boolean TipoPersonal = cliente instanceof Personal;

            if (TipoPersonal) {

                stmt.setString(1, "Personal");
                stmt.setBoolean(2, cliente.isActivo());
                stmt.setString(3, cliente.getAlta().toString());
                stmt.setString(4, cliente.getDireccion());
                stmt.setString(5, cliente.getEmail());
                stmt.setBoolean(6, cliente.isMoroso());
                stmt.setString(7, cliente.getNombre());
                stmt.setString(8, null);
                stmt.setString(9, null);
                stmt.setString(10, ((Personal) cliente).getDni());
            } else {
                stmt.setString(1, "Empresa");
                stmt.setBoolean(2, cliente.isActivo());
                stmt.setString(3, cliente.getAlta().toString());
                stmt.setString(4, cliente.getDireccion());
                stmt.setString(5, cliente.getEmail());
                stmt.setBoolean(6, cliente.isMoroso());
                stmt.setString(7, cliente.getNombre());
                stmt.setString(8, ((Empresa) cliente).getCif());
                if (((Empresa) cliente).getUnidadesNegocio() == null)
                    stmt.setString(9, null);
                else stmt.setString(9, ((Empresa) cliente).getUnidadesNegocio().toString());
                stmt.setString(10, null);

            }

            if (!cliente.validar()) {
                System.out.println("Cliente no valido en repositorio");
                throw new ClienteException("Cliente no válido", ErrorCode.INVALIDCLIENT);
            }

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                //cliente.getId(genKeys.getInt(1));
                cliente.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("Cliente creado erroneamente!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

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
