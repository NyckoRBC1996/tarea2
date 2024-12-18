package com.nsql.tarea2.repositories;

import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RegistroMedicoRepository extends MongoRepository<RegistroMedico, String> {
    List<RegistroMedico> findByCiPacienteOrderByFechaDesc(String ciPaciente);
    Page<RegistroMedico> findByCiPacienteOrderByFechaDesc(String ci, Pageable pageable);

    //List<RegistroMedico> findByTipoAndDiagnosticoAndMedicoAndInstitucion(String tipo, String diagnostico, String medico, String institucion);
}
