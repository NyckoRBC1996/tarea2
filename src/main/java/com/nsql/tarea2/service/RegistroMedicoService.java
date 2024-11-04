package com.nsql.tarea2.service;

import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.repositories.PacienteRepository;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RegistroMedicoService implements IRegistroMedicoService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private RegistroMedicoRepository registroMedicoRepository;

    @Override
    public ResponseEntity<?> agregarRegistro(String ci, RegistroMedico registro) {
        if (!pacienteRepository.existsById(ci)) {
            return ResponseEntity.status(402).body("No existe un paciente con la cédula aportada como parámetro");
        }
        registro.setCiPaciente(ci);
        registroMedicoRepository.save(registro);
        return ResponseEntity.ok("Registro médico agregado exitosamente");
    }

    @Override
    public ResponseEntity<?> eliminarRegistro(RegistroMedico registro) {
        if (pacienteRepository.existsById(registro.getCiPaciente())) {
            registroMedicoRepository.delete(registro);
            return ResponseEntity.ok("Registro médico eliminado exitosamente");
        }

        return ResponseEntity.status(402).body("No existe un paciente con la cédula del registro");
    }

    @Override
    public ResponseEntity<?> obtenerRegistrosFiltrados(TipoRegistroMedico tipo, String diagnostico, String medico, String institucion) {
        // Llama al repositorio usando los parámetros según su valor
        return ResponseEntity.ok(registroMedicoRepository.findByCriterios(
                tipo != null ? tipo : null,
                diagnostico != null ? diagnostico : "",
                medico != null ? medico : "",
                institucion != null ? institucion : ""
        ));
    }

}
