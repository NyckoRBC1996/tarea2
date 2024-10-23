package com.nsql.tarea2.repositories;

import com.nsql.tarea2.entidades.RegistroMedico;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.nsql.tarea2.entidades.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
}