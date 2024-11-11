package com.nsql.tarea2.controllers;

import com.nsql.tarea2.dataTypes.DtHistorialMedico;
import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.services.RegistroMedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RegistroMedicoControllerTest {

    @Mock
    private RegistroMedicoService registroMedicoService;

    @InjectMocks
    private RegistroMedicoController registroMedicoController;

    @BeforeEach
    public void setup() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAgregarRegistroOK(){
        // Crear un registro médico
        RegistroMedico registroMedico = new RegistroMedico();
        registroMedico.setId("1");
        registroMedico.setCiPaciente("12345");
        registroMedico.setMedico("Dr. Pérez");
        registroMedico.setFecha(LocalDate.of(2024, 11, 11));
        registroMedico.setTipo(TipoRegistroMedico.CONSULTA);
        registroMedico.setDiagnostico("Gripe");
        registroMedico.setInstitucion("Hospital General");
        registroMedico.setDescripcion("Consulta por fiebre y tos");
        registroMedico.setMedicacion("Paracetamol");

        // Crear un paciente
        Paciente paciente = new Paciente();
        paciente.setCi("12345");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        paciente.setSexo("Masculino");
        paciente.setHistorialMedico(null);

        DtHistorialMedico dtHistorialMedico = new DtHistorialMedico();
        dtHistorialMedico.setRegistroMedico(registroMedico);
        dtHistorialMedico.setPaciente(paciente);

        when(registroMedicoService.agregarRegistro(dtHistorialMedico.getPaciente().getCi(),registroMedico)).thenReturn(registroMedico);

        // When
        ResponseEntity<?> response = registroMedicoController.agregarRegistro(dtHistorialMedico);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registro médico agregado exitosamente", response.getBody());
    }

    @Test
    public void testAgregarRegistroNoExistePaciente(){
        // Crear un registro medico
        RegistroMedico registroMedico = new RegistroMedico();
        registroMedico.setId("1");
        registroMedico.setCiPaciente("89");
        registroMedico.setMedico("Dr. Pérez");
        registroMedico.setFecha(LocalDate.of(2024, 11, 11));
        registroMedico.setTipo(TipoRegistroMedico.CONSULTA);
        registroMedico.setDiagnostico("Gripe");
        registroMedico.setInstitucion("Hospital General");
        registroMedico.setDescripcion("Consulta por fiebre y tos");
        registroMedico.setMedicacion("Paracetamol");

        // Crear un paciente
        Paciente paciente = new Paciente();
        paciente.setCi("12345");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        paciente.setSexo("Masculino");
        paciente.setHistorialMedico(null);

        DtHistorialMedico dtHistorialMedico = new DtHistorialMedico();
        dtHistorialMedico.setRegistroMedico(registroMedico);
        dtHistorialMedico.setPaciente(paciente);

        when(registroMedicoService.agregarRegistro(dtHistorialMedico.getPaciente().getCi(),registroMedico)).thenReturn(null);

        // When
        ResponseEntity<?> response = registroMedicoController.agregarRegistro(dtHistorialMedico);

        // Then
        assertEquals(402, response.getStatusCode().value());
        assertEquals("No existe un paciente con la cédula aportada como parámetro", response.getBody());
    }

    @Test
    public void testObtenerRegistrosPorCriterioOk() throws Exception{

        // Crear algunos objetos de RegistroMedico para simular la respuesta
        RegistroMedico registro1 = new RegistroMedico();
        registro1.setId("1");
        registro1.setCiPaciente("12345");
        registro1.setMedico("Dr. A");
        registro1.setDiagnostico("Diagnóstico 1");
        registro1.setInstitucion("Institución 1");

        RegistroMedico registro2 = new RegistroMedico();
        registro2.setId("2");
        registro2.setCiPaciente("12345");
        registro2.setMedico("Dr. B");
        registro2.setDiagnostico("Diagnóstico 2");
        registro2.setInstitucion("Institución 2");

        List<RegistroMedico> registros = Arrays.asList(registro1, registro2);

        // Crear un objeto JSON con los filtros
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonRequest = "{ \"tipo\": \"CONSULTA\", \"diagnostico\": \"Diagnóstico 1\", \"medico\": \"Dr. A\", \"institucion\": \"Institución 1\" }";

        JsonNode jsonNode = objectMapper.readTree(jsonRequest);

        TipoRegistroMedico tipo = TipoRegistroMedico.CONSULTA;

        when(registroMedicoService.obtenerRegistrosFiltrados(tipo,"Diagnóstico 1","Dr. A", "Institución 1" )).thenReturn(registros);

        // When
        ResponseEntity<?> response = registroMedicoController.obtenerRegistrosPorCriterio(jsonNode);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registros, response.getBody());
    }

    @Test
    public void testObtenerRegistrosPorCriterioTipoIncorrecto() throws Exception{

        // Crear un objeto JSON con los filtros
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonRequest = "{ \"tipo\": \"Magia\", \"diagnostico\": \"Diagnóstico 1\", \"medico\": \"Dr. A\", \"institucion\": \"Institución 1\" }";

        JsonNode jsonNode = objectMapper.readTree(jsonRequest);

        // When
        ResponseEntity<?> response = registroMedicoController.obtenerRegistrosPorCriterio(jsonNode);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Tipo de registro médico inválido. Valores permitidos: CONSULTA, EXAMEN, INTERNACION.", response.getBody());
    }
}
