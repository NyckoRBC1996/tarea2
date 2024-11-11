package com.nsql.tarea2.services;

import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.repositories.PacienteRepository;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroMedicoService implements IRegistroMedicoService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private RegistroMedicoRepository registroMedicoRepository;

    @Override
    public ResponseEntity<?> eliminarRegistro(RegistroMedico registro) {
        if (pacienteRepository.existsById(registro.getCiPaciente())) {
            registroMedicoRepository.delete(registro);
            return ResponseEntity.ok("Registro médico eliminado exitosamente");
        }

        return ResponseEntity.status(402).body("No existe un paciente con la cédula del registro");
    }

    @Override
    public List<RegistroMedico> obtenerRegistrosFiltrados(TipoRegistroMedico tipo, String diagnostico, String medico, String institucion) {
        // Llama al repositorio usando los parámetros según su valor
        return registroMedicoRepository.findByCriterios(
                tipo != null ? tipo : null,
                diagnostico != null ? diagnostico : "",
                medico != null ? medico : "",
                institucion != null ? institucion : ""
        );
    }

    @Override
    public RegistroMedico agregarRegistro(String ci, RegistroMedico registro) {
        if (!pacienteRepository.existsById(ci)) {
            return null;
        }
        registro.setCiPaciente(ci);
        registroMedicoRepository.save(registro);
        return registro;
    }



}
