package com.jobd.solapp.model;

public class Usuario {

    private int id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String contrasena;
    private String creado_en;

    // Constructor vacío (necesario para Retrofit/Gson)
    public Usuario() {}

    // --- Getters ---
    public int getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getContrasena() { return contrasena; }
    public String getCreado_en() { return creado_en; }

    // --- Setters ---
    public void setId(int id) { this.id = id; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setCreado_en(String creado_en) { this.creado_en = creado_en; }
}
