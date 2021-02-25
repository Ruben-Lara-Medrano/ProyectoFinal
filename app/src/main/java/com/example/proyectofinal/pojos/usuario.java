package com.example.proyectofinal.pojos;

public class usuario {
    private String nombre;
    private String correo;
    private String telefono;
    private String puesto;
    private String imagen;
    private String pass;

    public usuario() {
    }

    public usuario(String nombre, String correo, String telefono, String puesto, String imagen, String pass) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.puesto = puesto;
        this.imagen = imagen;
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
