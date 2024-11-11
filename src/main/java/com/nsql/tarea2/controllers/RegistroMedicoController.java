package com.nsql.tarea2.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.nsql.tarea2.dataTypes.DtHistorialMedico;
import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.services.RegistroMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroMedicoController {

    @Autowired
    private RegistroMedicoService registroMedicoService;

    @PostMapping("agregar-registro")
    public ResponseEntity<?> agregarRegistro(@RequestBody DtHistorialMedico historialMedico) {
        RegistroMedico registroMedico = registroMedicoService.agregarRegistro(historialMedico.getPaciente().getCi(),historialMedico.getRegistroMedico());
        if (registroMedico == null) {
            return ResponseEntity.status(402).body("No existe un paciente con la cédula aportada como parámetro");
        }
        else {
            return ResponseEntity.ok("Registro médico agregado exitosamente");
        }
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
        List<RegistroMedico> registros = registroMedicoService.obtenerRegistrosFiltrados(tipo, diagnostico, medico, institucion);
        return ResponseEntity.ok(registros);
    }

}
