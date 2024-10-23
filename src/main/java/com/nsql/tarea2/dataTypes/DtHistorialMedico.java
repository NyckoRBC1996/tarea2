package com.nsql.tarea2.dataTypes;

import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.entidades.RegistroMedico;


public class DtHistorialMedico {

    private String ciPaciente;
    private RegistroMedico Registro_Medico;

    public String getCiPaciente() {
        return ciPaciente;
    }
    public void setCiPaciente(String ciPaciente) {
        this.ciPaciente = ciPaciente;
    }

    public RegistroMedico getRegistro_Medico() {
        return Registro_Medico;
    }
    public void setRegistro_Medico(RegistroMedico registro_Medico) {
        Registro_Medico = registro_Medico;
    }

}
