package com.nsql.tarea2.dataTypes;

import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.entidades.RegistroMedico;


public class DtHistorialMedico {

    private Paciente paciente;
    private RegistroMedico registroMedico;

    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public RegistroMedico getRegistroMedico() {
        return registroMedico;
    }
    public void setRegistroMedico(RegistroMedico registroMedico) {
        this.registroMedico = registroMedico;
    }
}
