package es.netmind.mypersonalbankapi.modelos.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString

public class Usuario {
    private Integer idUsuario;
    private String rol;
    private String password;
    private String mail;



}
