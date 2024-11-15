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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

@Service
public class RegistroMedicoService implements IRegistroMedicoService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private RegistroMedicoRepository registroMedicoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

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
        Criteria criteria = new Criteria();

        if (tipo != null) {
            criteria.and("tipo").is(tipo);  // Solo se filtra por 'tipo' si no es null
        }

        if (diagnostico != null && !diagnostico.isEmpty()) {
            criteria.and("diagnostico").regex(diagnostico, "i");  // Se filtra por 'diagnostico' si no es null o vacío
        }

        if (medico != null && !medico.isEmpty()) {
            criteria.and("medico").regex(medico, "i");  // Se filtra por 'medico' si no es null o vacío
        }

        if (institucion != null && !institucion.isEmpty()) {
            criteria.and("institucion").regex(institucion, "i");  // Se filtra por 'institucion' si no es null o vacío
        }

        // Usar MongoTemplate para ejecutar la consulta
        Query query = new Query(criteria);
        return mongoTemplate.find(query, RegistroMedico.class);
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
