package com.nsql.tarea2.controllers;

import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.repositories.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteRepository pacienteRepository;

    // Crear un nuevo paciente
    @PostMapping("add-paciente")
    public ResponseEntity<?> crearPaciente(@RequestBody Paciente paciente) {
           return pacienteService.agregarPaciente(paciente);
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

    @PostMapping("/{ci}/registros")
    public ResponseEntity<?> agregarRegistro(@PathVariable String ci, @RequestBody RegistroMedico registro) {
        return pacienteService.agregarRegistro(ci, registro);
    }

    @GetMapping("/{ci}/historial")
    public ResponseEntity<?> consultarHistorial(@PathVariable String ci) {
       return pacienteService.consultarHistorial(ci);
    }
    
}








