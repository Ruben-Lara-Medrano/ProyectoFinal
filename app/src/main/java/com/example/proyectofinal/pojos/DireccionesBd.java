package com.example.proyectofinal.pojos;

public class DireccionesBd {
    private static final String URL="http://192.168.8.120:80/PhpMoviles/";
    //http://192.168.8.119/PhpMoviles/sacar_usuario.php?id=iGdD8vdidHSW0qhuC5w4dV136VR2
    public String cogerUsuario(){
        return URL+"sacar_usuario.php?id=";
    }
    public String actualizarUsuario(){
        return URL+"actualizar_usuario.php";
    }
    public String insertarUsuario(){
        return URL+"insertar.php";
    }
    public String buscar_usuarios(){
        return URL+"buscar_usuarios.php";
    }
    public String buscarPublicaciones(){
        return URL+"buscar_publicaciones.php";
    }
    public String anadirPublicaciones(){
        return URL+"añadir_publicacion.php";
    }
    public String subirFoto(){
        return URL+"subir_foto.php";
    }
    public String actualizarFoto(){
        return URL+"Fotos/";
    }
}

