package com.nsql.tarea2.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nsql.tarea2.entidades.Paciente;

import java.util.Optional;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
    Optional<Paciente> findByCi(String ci);
}
