package com.nsql.tarea2.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document(collection = "pacientes")
public class Paciente {

    @Id
    private String ci;

    private String nombre;
    private String apellido;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private String sexo;
    private List<RegistroMedico> historialMedico;


    // Constructor
    public Paciente() {}

    // Getters y Setters
    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<RegistroMedico> getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(List<RegistroMedico> historialMedico) {
        this.historialMedico = historialMedico;
    }

}
