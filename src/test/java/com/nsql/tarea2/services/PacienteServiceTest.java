package com.nsql.tarea2.services;

import com.nsql.tarea2.controllers.PacienteController;
import com.nsql.tarea2.entidades.Paciente;
import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.repositories.PacienteRepository;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import com.nsql.tarea2.services.RegistroMedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class PacienteServiceTest {

    @InjectMocks
    private PacienteService pacienteService;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private RegistroMedicoRepository registroMedicoRepository;

    @BeforeEach
    public void setup() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgregarPacienteOk() {
        // Crear un paciente de prueba
        Paciente paciente = new Paciente();
        paciente.setCi("12345");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));  // Se agrega la fecha de nacimiento
        paciente.setSexo("Curioso");  // Se agrega el sexo
        paciente.setHistorialMedico(null);

        when(pacienteRepository.existsById("12345")).thenReturn(false);

        // Ejecución
        Paciente resultado = pacienteService.agregarPaciente(paciente);

        // Verificación
        assertEquals(resultado, paciente);
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    void testAgregarPacienteError() {
        // Crear un paciente de prueba
        Paciente paciente = new Paciente();
        paciente.setCi("12345");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));  // Se agrega la fecha de nacimiento
        paciente.setSexo("Curioso");  // Se agrega el sexo
        paciente.setHistorialMedico(null);

        when(pacienteRepository.existsById("12345")).thenReturn(true);

        // Ejecución
        Paciente resultado = pacienteService.agregarPaciente(paciente);

        // Verificación
        assertNull(resultado);
    }

    @Test
    void testConsultarHistorialOK() {
        // Crear un paciente de prueba
        Paciente paciente = new Paciente();
        paciente.setCi("12345");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));  // Se agrega la fecha de nacimiento
        paciente.setSexo("Curioso");  // Se agrega el sexo
        paciente.setHistorialMedico(null);
        PageRequest pageable = PageRequest.of(0, 5);
        Page<RegistroMedico> historial = new PageImpl<>(Collections.emptyList());

        when(pacienteRepository.existsById(paciente.getCi())).thenReturn(true);
        when(registroMedicoRepository.findByCiPacienteOrderByFechaDesc(paciente.getCi(), pageable)).thenReturn(historial);

        // Ejecución
        Page<RegistroMedico> resultado = pacienteService.consultarHistorial(paciente.getCi(), pageable);

        // Verificación
        assertEquals(resultado, historial);
        verify(registroMedicoRepository, times(1)).findByCiPacienteOrderByFechaDesc(paciente.getCi(), pageable);
    }

    @Test
    void testConsultarHistorialError() {
        // Crear un paciente de prueba
        Paciente paciente = new Paciente();
        paciente.setCi("12345");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));  // Se agrega la fecha de nacimiento
        paciente.setSexo("Curioso");  // Se agrega el sexo
        paciente.setHistorialMedico(null);
        PageRequest pageable = PageRequest.of(0, 5);

        when(pacienteRepository.existsById(paciente.getCi())).thenReturn(false);

        // Ejecución
        Page<RegistroMedico> resultado = pacienteService.consultarHistorial(paciente.getCi(), pageable);

        // Verificación
        assertNull(resultado);
    }
}
