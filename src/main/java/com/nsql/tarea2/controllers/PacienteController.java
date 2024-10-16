package com.nsql.tarea2.controllers;

import com.nsql.tarea2.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.repositories.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    // Crear un nuevo paciente
    @PostMapping("add-paciente")
    public void crearPaciente(@RequestBody Paciente paciente) {
            pacienteService.agregarPaciente(paciente);
    }
    // Eliminar paciente
    @DeleteMapping("eliminar-paciente")
    public void eliminarPaciente(@RequestBody Paciente paciente) {
        pacienteService.eliminarPaciente(paciente);
    }

    // Obtener todos los pacientes
    @GetMapping("obtener-pacientes")
    public List<Paciente> obtenerPacientes() {
        return pacienteService.obtenerPacientes();
    }

    // Obtener un paciente por ID
    @GetMapping("buscar-paciente")
    public Paciente obtenerPaciente(@RequestParam Long id) {
        return pacienteService.buscarPacientePorId(id);
    }

}








