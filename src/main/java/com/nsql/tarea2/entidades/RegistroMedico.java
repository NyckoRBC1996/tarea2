package com.nsql.tarea2.entidades;

import com.nsql.tarea2.enums.TipoRegistroMedico;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "registros_medicos")
public class RegistroMedico {
    @Id
    private String id;
    private String ciPaciente;
    private String medico;

    private LocalDate fecha;
    private TipoRegistroMedico tipo;
    private String diagnostico;
    private String institucion;
    private String descripcion;
    private String medicacion;

    // Constructores

    public RegistroMedico(){}

    // Getters y setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCiPaciente() {
        return ciPaciente;
    }
    public void setCiPaciente(String ciPaciente) {
        this.ciPaciente = ciPaciente;
    }

    public String getMedico() {
        return medico;
    }
    public void setMedico(String medico) {
        this.medico = medico;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getInstitucion() {
        return institucion;
    }
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMedicacion() {
        return medicacion;
    }
    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }

    public TipoRegistroMedico getTipo() {
        return tipo;
    }
    public void setTipo(TipoRegistroMedico tipo) {
        this.tipo = tipo;
    }


}
