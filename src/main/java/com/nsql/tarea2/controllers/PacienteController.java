package com.nsql.tarea2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.repositories.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    // Crear un nuevo paciente
    @PostMapping
    public Paciente crearPaciente(@RequestBody Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    // Obtener todos los pacientes
    @GetMapping
    public List<Paciente> obtenerPacientes() {
        return pacienteRepository.findAll();
    }

    // Obtener un paciente por ID
    @GetMapping("/{id}")
    public Paciente obtenerPaciente(@PathVariable String id) {
        return pacienteRepository.findById(id).orElse(null);
    }

}








