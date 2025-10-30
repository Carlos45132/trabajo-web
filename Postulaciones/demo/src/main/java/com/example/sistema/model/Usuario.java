package com.example.sistema.model;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

public class Usuario {


    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @Pattern(regexp = "\\d{9}", message = "Teléfono inválido (solo números, 9 dígitos)")
    private String telefono;

    @NotBlank(message = "Debes seleccionar un tipo de documento")
    private String doc;

    @NotBlank(message = "Número de documento obligatorio")
    private String docNumero;

    public String getDocNumeroError() {
        if (doc == null || docNumero == null || doc.isEmpty() || docNumero.isEmpty()) {
            return null;
        }

        switch (doc.toLowerCase()) {
            case "dni":
                if (!docNumero.matches("\\d{8}"))
                    return "DNI debe tener exactamente 8 dígitos";
                break;
            case "ce":
                if (!docNumero.matches("[ABC]\\d{7,9}"))
                    return "CE debe empezar con A, B o C seguido de 7 a 9 dígitos";
                break;
            case "pasaporte":
                if (!docNumero.matches("\\d{9,11}"))
                    return "N° Pasaporte debe tener entre 9 y 11 dígitos";
                break;
            default:
                return "Tipo de documento desconocido";
        }
        return null;
    }

    @NotBlank(message = "Debes seleccionar un sexo")
    private String sexo;

    @NotBlank(message = "Fecha de nacimiento obligatoria")
    private String fechaNac;

    @AssertTrue(message = "Debes ser mayor de 18 años")
    public boolean isMayorDeEdad() {
        if (fechaNac == null || fechaNac.isEmpty())
            return false;

        try {
            LocalDate fecha = LocalDate.parse(fechaNac, DateTimeFormatter.ISO_DATE);
            return Period.between(fecha, LocalDate.now()).getYears() >= 18;
        } catch (Exception e) {
            return false; // formato inválido
        }
    }

    @Size(min = 6, message = "Debe tener al menos 6 caracteres")
    private String contrasena;

    @Size(min = 6, message = "Debe tener al menos 6 caracteres")
    private String confirmarContrasena;

    private Long id;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String apellido, String correo,
            String telefono, String doc, String docNumero,
            String sexo, String fechaNac, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.doc = doc;
        this.docNumero = docNumero;
        this.sexo = sexo;
        this.fechaNac = fechaNac;
        this.contrasena = contrasena;
    }

    // "ADMIN" o "RECLUTADOR" o "POSTULANTE"
    private String rol;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDocNumero() {
        return docNumero;
    }

    public void setDocNumero(String docNumero) {
        this.docNumero = docNumero;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getConfirmarContrasena() {
        return confirmarContrasena;
    }

    public void setConfirmarContrasena(String confirmarContrasena) {
        this.confirmarContrasena = confirmarContrasena;
    }




}
