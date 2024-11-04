package com.nsql.tarea2.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.nsql.tarea2.dataTypes.DtHistorialMedico;
import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import com.nsql.tarea2.service.PacienteService;
import com.nsql.tarea2.service.RegistroMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private RegistroMedicoService registroMedicoService;

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
        // Extraer 'pageNumber' y 'size' del JSON
        int pageNumber = jsonNode.has("pageNumber") ? jsonNode.get("pageNumber").asInt() : 0; // Valor por defecto a 0
        int pageSize = jsonNode.has("pageSize") ? jsonNode.get("pageSize").asInt() : 10; // Valor por defecto a 10

        // Crear Pageable usando los valores extraídos
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Llamar al servicio pasando el 'ci' y el 'pageable'
        return pacienteService.consultarHistorial(ci, pageable);
    }

    @GetMapping("obtener-registros-por-criterio")
    public ResponseEntity<?> obtenerRegistrosPorCriterio(@RequestBody JsonNode jsonNode){
        TipoRegistroMedico tipoValido;

        if (jsonNode.has("tipo") && !jsonNode.get("tipo").isNull()) {
            try {
                // Intenta convertir el texto recibido a un valor del enum TipoRegistroMedico
                tipoValido = TipoRegistroMedico.valueOf(jsonNode.get("tipo").asText().toUpperCase());
                System.out.println(tipoValido);
            } catch (IllegalArgumentException e) {
                // Si el valor no coincide con ningún valor en el enum, devuelve un error
                return ResponseEntity.badRequest().body("Tipo de registro médico inválido. Valores permitidos: CONSULTA, EXAMEN, INTERNACION.");
            }
        }
        String diagnostico = jsonNode.has("diagnostico") ? jsonNode.get("diagnostico").asText() : null;
        String medico = jsonNode.has("medico") ? jsonNode.get("medico").asText() : null;
        String institucion = jsonNode.has("institucion") ? jsonNode.get("institucion").asText() : null;
        TipoRegistroMedico tipo = TipoRegistroMedico.valueOf(jsonNode.get("tipo").asText().toUpperCase());
        return registroMedicoService.obtenerRegistrosFiltrados(tipo, diagnostico, medico, institucion);
    }
    
}






