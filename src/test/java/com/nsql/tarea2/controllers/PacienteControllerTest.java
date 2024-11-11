package com.nsql.tarea2.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsql.tarea2.controllers.PacienteController;
import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.services.PacienteService;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.Null;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PacienteControllerTest {

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    public void setup() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearPacienteOk(){
        // Crear un paciente de prueba
        Paciente paciente = new Paciente();
        paciente.setCi("12345");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));  // Se agrega la fecha de nacimiento
        paciente.setSexo("Curioso");  // Se agrega el sexo
        paciente.setHistorialMedico(null);

        when(pacienteService.agregarPaciente(paciente)).thenReturn(paciente);

        // When
        ResponseEntity<?> response = pacienteController.crearPaciente(paciente);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Paciente agregado exitosamente", response.getBody());
    }

    @Test
    public void testCrearPacienteYaExiste(){

        Paciente paciente = new Paciente();
        paciente.setCi("12345");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));  // Se agrega la fecha de nacimiento
        paciente.setSexo("Curioso");  // Se agrega el sexo
        paciente.setHistorialMedico(null);

        // Mocko
        when(pacienteService.agregarPaciente(paciente)).thenReturn(null);

        // When
        ResponseEntity<?> response = pacienteController.crearPaciente(paciente);

        // Then
        assertEquals(401, response.getStatusCode().value());
        assertEquals("El paciente ya existe", response.getBody());
    }

    @Test
    public void testConsultarHistorialOk() throws Exception {

        // Crear algunos objetos de RegistroMedico para simular la respuesta
        RegistroMedico registro1 = new RegistroMedico();
        registro1.setId("1");
        registro1.setCiPaciente("12345");
        registro1.setMedico("Dr. A");
        registro1.setFecha(LocalDate.now());
        registro1.setDiagnostico("Diagnóstico 1");
        registro1.setInstitucion("Institución 1");
        registro1.setDescripcion("Descripción 1");
        registro1.setMedicacion("Medicamento 1");

        RegistroMedico registro2 = new RegistroMedico();
        registro2.setId("2");
        registro2.setCiPaciente("12345");
        registro2.setMedico("Dr. B");
        registro2.setFecha(LocalDate.now());
        registro2.setDiagnostico("Diagnóstico 2");
        registro2.setInstitucion("Institución 2");
        registro2.setDescripcion("Descripción 2");
        registro2.setMedicacion("Medicamento 2");

        // Crear la página de RegistroMedico
        Page<RegistroMedico> page = new PageImpl<>(Arrays.asList(registro1, registro2), PageRequest.of(0, 10), 2);

        // Crear un objeto JSON con los datos necesarios
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonRequest = "{ \"ci\": \"12345\", \"pageNumber\": 0, \"pageSize\": 10 }";
        JsonNode jsonNode = objectMapper.readTree(jsonRequest);

        // Crear Pageable usando los valores extraídos
        Pageable pageable = PageRequest.of(0, 10);

        when(pacienteService.consultarHistorial("12345",pageable)).thenReturn(page);

        // When
        ResponseEntity<?> response = pacienteController.consultarHistorial(jsonNode);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());

    }

    @Test
    public void testConsultarHistorialNoExistePaciente() throws Exception {


        // Crear un objeto JSON con los datos necesarios
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonRequest = "{ \"ci\": \"12345\", \"pageNumber\": 0, \"pageSize\": 10 }";
        JsonNode jsonNode = objectMapper.readTree(jsonRequest);

        // Crear Pageable usando los valores extraídos
        Pageable pageable = PageRequest.of(0, 10);

        when(pacienteService.consultarHistorial("12345",pageable)).thenReturn(null);

        // When
        ResponseEntity<?> response = pacienteController.consultarHistorial(jsonNode);

        // Then
        assertEquals(402, response.getStatusCode().value());
        assertEquals("No existe un paciente con la cédula aportada como parámetro", response.getBody());

    }
}