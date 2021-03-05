package com.example.proyectofinal.pojos;
public class Publicacion {
    public int FotoPerfil;
    public String textoPublicacion;

    public Publicacion(int fotoPerfil, String textoPublicacion) {
        FotoPerfil = fotoPerfil;
        this.textoPublicacion = textoPublicacion;
    }

    public int getFotoPerfil() {
        return FotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {

        FotoPerfil = fotoPerfil;
    }

    public String getTextoPublicacion() {
        return textoPublicacion;
    }

    public void setTextoPublicacion(String textoPublicacion) {
        this.textoPublicacion = textoPublicacion;
    }
}
