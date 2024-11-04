package com.nsql.tarea2.service;

import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.entidades.RegistroMedico;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    public ResponseEntity<?> agregarPaciente(Paciente paciente);
    public void eliminarPaciente(Paciente Paciente);
    public List<Paciente> obtenerPacientes();
    public ResponseEntity<?> buscarPacientePorCi(String ci);

    public ResponseEntity<?> agregarRegistro(String ci, RegistroMedico registro);
    public ResponseEntity<?> consultarHistorial(String ci, Pageable pageable);
}
