package com.example.proyectofinal;
public class listview {
    public int FotoPerfil;
    public String textoPublicacion;

    public listview(int fotoPerfil, String textoPublicacion) {
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
