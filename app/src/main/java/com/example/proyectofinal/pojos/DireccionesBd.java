package com.example.proyectofinal.pojos;

public class DireccionesBd {
    private static final String URL="http://172.16.2.219:80/PhpMoviles/";

    public String cogerUsuario(){
        return URL+"sacar_usuario.php?id=";
    }
}
