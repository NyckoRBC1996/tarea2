package com.nsql.tarea2.controllers;

import com.nsql.tarea2.dataTypes.DtHistorialMedico;
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
    @PostMapping("agregar-paciente")
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
    public ResponseEntity<?> obtenerPaciente(@RequestBody String ci) {
        System.out.println("La ci es: " + ci);
        return pacienteService.buscarPacientePorCi(ci);
    }

    @PostMapping("agregar-registro")
    public ResponseEntity<?> agregarRegistro(@RequestBody DtHistorialMedico historialMedico) {
        return pacienteService.agregarRegistro(historialMedico.getPaciente().getCi(),historialMedico.getRegistroMedico());
    }

    @GetMapping("consultar-historal")
    public ResponseEntity<?> consultarHistorial(@RequestBody String ci) {
       return pacienteService.consultarHistorial(ci);
    }
    
}








