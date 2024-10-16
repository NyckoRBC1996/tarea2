package com.nsql.tarea2.service;

import com.nsql.tarea2.entidades.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    public void agregarPaciente(Paciente Paciente);
    public void eliminarPaciente(Paciente Paciente);
    public List<Paciente> obtenerPacientes();
    public Paciente buscarPacientePorId(Long id);
}
