package com.nsql.tarea2.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.nsql.tarea2.dataTypes.DtHistorialMedico;
import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import com.nsql.tarea2.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> obtenerPaciente(@RequestBody JsonNode jsonNode) {
        String ci = jsonNode.get("ci").asText(); // Extraer 'ci' del JSON
        return pacienteService.buscarPacientePorCi(ci);
    }

    @PostMapping("agregar-registro")
    public ResponseEntity<?> agregarRegistro(@RequestBody DtHistorialMedico historialMedico) {
        return pacienteService.agregarRegistro(historialMedico.getPaciente().getCi(),historialMedico.getRegistroMedico());
    }

    @GetMapping("consultar-historal")
    public ResponseEntity<?> consultarHistorial(@RequestBody JsonNode jsonNode){
       String ci = jsonNode.get("ci").asText(); // Extraer 'ci' del JSON
       return pacienteService.consultarHistorial(ci);
    }

    @GetMapping("obtener-registros-por-criterio")
    public ResponseEntity<?> obtenerRegistrosPorCriterio(@RequestBody JsonNode jsonNode){
        /*TipoRegistroMedico tipo = jsonNode.has("tipo") ? Tipo.valueOf(jsonNode.get("tipo").asText()) : null;
        String diagnostico = jsonNode.has("diagnostico") ? jsonNode.get("diagnostico").asText() : null;
        String medico = jsonNode.has("medico") ? jsonNode.get("medico").asText() : null;
        String institucion = jsonNode.has("institucion") ? jsonNode.get("institucion").asText() : null;

        List<RegistroMedico> registros = registroMedicoRepository.findByCriterios(tipo, diagnostico, medico, institucion);
        return ResponseEntity.ok(registros);*/
        return ResponseEntity.ok("Ta Bien Goku");
    }
    
}






