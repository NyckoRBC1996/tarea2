package com.nsql.tarea2.services;

import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.entidades.RegistroMedico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPacienteService {
    public Paciente agregarPaciente(Paciente paciente);
    public void eliminarPaciente(Paciente Paciente);
    public List<Paciente> obtenerPacientes();
    public ResponseEntity<?> buscarPacientePorCi(String ci);
    public Page<RegistroMedico> consultarHistorial(String ci, Pageable pageable);
}
