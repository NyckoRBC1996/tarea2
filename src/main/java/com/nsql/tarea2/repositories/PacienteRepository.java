package com.nsql.tarea2.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nsql.tarea2.entidades.Paciente;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
}
