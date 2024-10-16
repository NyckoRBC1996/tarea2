package com.nsql.tarea2.service;

import com.nsql.tarea2.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import com.nsql.tarea2.entidades.Paciente;

@Service
public class PacienteService implements IPacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void agregarPaciente(Paciente Paciente) {

        pacienteRepository.save(Paciente);
    }

    @Override
    public void eliminarPaciente(Paciente Paciente) {
        pacienteRepository.delete(Paciente);
    }

    @Override
    public List<Paciente> obtenerPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPacientePorId(Long id){
        return pacienteRepository.findById(String.valueOf(id)).orElse(null);
    }

}
