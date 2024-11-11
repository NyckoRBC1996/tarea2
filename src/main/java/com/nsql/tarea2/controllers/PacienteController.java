package com.nsql.tarea2.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.nsql.tarea2.dataTypes.DtHistorialMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import com.nsql.tarea2.services.PacienteService;
import com.nsql.tarea2.services.RegistroMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private RegistroMedicoRepository registroMedicoRepository;
    @Autowired
    private RegistroMedicoService registroMedicoService;

    // Crear un nuevo paciente
    @PostMapping("agregar-paciente")
    public ResponseEntity<?> crearPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteGuardado = pacienteService.agregarPaciente(paciente);
        if (pacienteGuardado == null) {
            return ResponseEntity.status(401).body("El paciente ya existe");
        }
        else{
            return ResponseEntity.ok("Paciente agregado exitosamente");
        }
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
    public ResponseEntity<?> obtenerPaciente(@RequestBody JsonNode jsonNode) {
        String ci = jsonNode.get("ci").asText(); // Extraer 'ci' del JSON
        return pacienteService.buscarPacientePorCi(ci);
    }
    
}






