package com.nsql.tarea2.repositories;

import com.nsql.tarea2.entidades.RegistroMedico;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegistroMedicoRepository extends MongoRepository<RegistroMedico, String> {
    List<RegistroMedico> findByCiPacienteOrderByFechaDesc(String ciPaciente);
    List<RegistroMedico> findByTipoAndDiagnosticoAndMedicoAndInstitucion(String tipo, String diagnostico, String medico, String institucion);
}
