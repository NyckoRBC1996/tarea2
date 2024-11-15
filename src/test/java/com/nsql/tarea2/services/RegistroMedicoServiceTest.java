package com.nsql.tarea2.services;


import com.nsql.tarea2.controllers.PacienteController;
import com.nsql.tarea2.controllers.RegistroMedicoController;
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
import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import com.nsql.tarea2.repositories.PacienteRepository;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


public class RegistroMedicoServiceTest {

    @InjectMocks
    private RegistroMedicoService registroMedicoService;

    @Mock
    private RegistroMedicoRepository registroMedicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private MongoTemplate mongoTemplate;


    @BeforeEach
    public void setup() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgregarRegistroOk() {
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

        // Simulación de la existencia del paciente
        when(pacienteRepository.existsById(paciente.getCi())).thenReturn(true);

        // Ejecución del método
        RegistroMedico resultado = registroMedicoService.agregarRegistro(paciente.getCi(), registroMedico);

        // Verificación
        assertEquals(resultado, registroMedico);
        verify(registroMedicoRepository, times(1)).save(registroMedico);
    }

    @Test
    void testAgregarRegistroError() {
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

        // Simulación de la existencia del paciente
        when(pacienteRepository.existsById(paciente.getCi())).thenReturn(false);

        // Ejecución del método
        RegistroMedico resultado = registroMedicoService.agregarRegistro(paciente.getCi(), registroMedico);

        // Verificación
        assertNull(resultado);
    }

    @Test
    void testObtenerRegistrosFiltradosOk() {
        // Configuración de la prueba
        TipoRegistroMedico tipo = TipoRegistroMedico.CONSULTA;
        String diagnostico = "Gripe";
        String medico = "Dr. Pérez";
        String institucion = "Hospital General";

        // Crear un registro médico
        RegistroMedico registro1 = new RegistroMedico();
        registro1.setId("1");
        registro1.setCiPaciente("12345678");
        registro1.setMedico("Dr. Pérez");
        registro1.setFecha(LocalDate.of(2024, 11, 11));
        registro1.setTipo(TipoRegistroMedico.CONSULTA);
        registro1.setDiagnostico("Gripe");
        registro1.setInstitucion("Hospital General");
        registro1.setDescripcion("Consulta por fiebre y tos");
        registro1.setMedicacion("Paracetamol");

        RegistroMedico registro2 = new RegistroMedico();
        registro2.setId("2");
        registro2.setCiPaciente("87654321");
        registro2.setMedico("Dr. Pérez");
        registro2.setFecha(LocalDate.of(2024, 11, 11));
        registro2.setTipo(TipoRegistroMedico.CONSULTA);
        registro2.setDiagnostico("Gripe");
        registro2.setInstitucion("Hospital General");
        registro2.setDescripcion("Consulta por fiebre y tos");
        registro2.setMedicacion("Paracetamol");

        List<RegistroMedico> registros = Arrays.asList(registro1, registro2);

        // Simulación de la llamada a mongoTemplate.find()
        when(mongoTemplate.find(any(Query.class), eq(RegistroMedico.class))).thenReturn(registros);

        // Ejecución del método
        List<RegistroMedico> resultado = registroMedicoService.obtenerRegistrosFiltrados(tipo, diagnostico, medico, institucion);

        // Verificación
        assertEquals(resultado, registros);  // Aseguramos que se devuelven los registros correctos
        assertEquals(2, resultado.size());   // Verificamos que sean los dos registros
        assertEquals("12345678", resultado.get(0).getCiPaciente());  // Verificamos que el primer paciente sea el correcto
        assertEquals("87654321", resultado.get(1).getCiPaciente());  // Verificamos que el segundo paciente sea el correcto

        // Verificamos que se haya llamado una vez a mongoTemplate.find()
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(RegistroMedico.class));
    }

    @Test
    void testObtenerRegistrosFiltradosTipoNull() {
        // Configuración de la prueba con tipo null
        TipoRegistroMedico tipo = null;
        String diagnostico = "Gripe";
        String medico = "Dr. Pérez";
        String institucion = "Hospital General";

        // Crear registros médicos
        RegistroMedico registro1 = new RegistroMedico();
        registro1.setId("1");
        registro1.setCiPaciente("12345678");
        registro1.setMedico("Dr. Pérez");
        registro1.setFecha(LocalDate.of(2024, 11, 11));
        registro1.setTipo(TipoRegistroMedico.CONSULTA);
        registro1.setDiagnostico("Gripe");
        registro1.setInstitucion("Hospital General");
        registro1.setDescripcion("Consulta por fiebre y tos");
        registro1.setMedicacion("Paracetamol");

        List<RegistroMedico> registros = Arrays.asList(registro1);

        // Simulación de la llamada a mongoTemplate.find()
        when(mongoTemplate.find(any(Query.class), eq(RegistroMedico.class))).thenReturn(registros);

        // Ejecución del método con tipo null
        List<RegistroMedico> resultado = registroMedicoService.obtenerRegistrosFiltrados(tipo, null, null, null);

        // Verificación
        assertEquals(1, resultado.size());
        assertEquals("12345678", resultado.get(0).getCiPaciente());

        // Verificamos que se haya llamado una vez a mongoTemplate.find()
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(RegistroMedico.class));
    }

    @Test
    void testObtenerRegistrosFiltradosSinDiagnostico() {
        // Configuración de la prueba sin diagnostico
        TipoRegistroMedico tipo = TipoRegistroMedico.CONSULTA;
        String diagnostico = "";  // Diagnóstico vacío
        String medico = "Dr. Pérez";
        String institucion = "Hospital General";

        // Crear registros médicos
        RegistroMedico registro1 = new RegistroMedico();
        registro1.setId("1");
        registro1.setCiPaciente("12345678");
        registro1.setMedico("Dr. Pérez");
        registro1.setFecha(LocalDate.of(2024, 11, 11));
        registro1.setTipo(TipoRegistroMedico.CONSULTA);
        registro1.setDiagnostico("Gripe");  // Diagnóstico con valor
        registro1.setInstitucion("Hospital General");
        registro1.setDescripcion("Consulta por fiebre y tos");
        registro1.setMedicacion("Paracetamol");

        List<RegistroMedico> registros = Arrays.asList(registro1);

        // Simulación de la llamada a mongoTemplate.find()
        when(mongoTemplate.find(any(Query.class), eq(RegistroMedico.class))).thenReturn(registros);

        // Ejecución del método con diagnóstico vacío
        List<RegistroMedico> resultado = registroMedicoService.obtenerRegistrosFiltrados(tipo, diagnostico, medico, institucion);

        // Verificación
        assertEquals(1, resultado.size());
        assertEquals("12345678", resultado.get(0).getCiPaciente());

        // Verificamos que se haya llamado una vez a mongoTemplate.find()
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(RegistroMedico.class));
    }

    @Test
    void testObtenerRegistrosFiltradosSinMedico() {
        // Configuración de la prueba sin medico
        TipoRegistroMedico tipo = TipoRegistroMedico.CONSULTA;
        String diagnostico = "Gripe";
        String medico = "";  // Medico vacío
        String institucion = "Hospital General";

        // Crear registros médicos
        RegistroMedico registro1 = new RegistroMedico();
        registro1.setId("1");
        registro1.setCiPaciente("12345678");
        registro1.setMedico("Dr. Pérez");  // Médico con valor
        registro1.setFecha(LocalDate.of(2024, 11, 11));
        registro1.setTipo(TipoRegistroMedico.CONSULTA);
        registro1.setDiagnostico("Gripe");
        registro1.setInstitucion("Hospital General");
        registro1.setDescripcion("Consulta por fiebre y tos");
        registro1.setMedicacion("Paracetamol");

        List<RegistroMedico> registros = Arrays.asList(registro1);

        // Simulación de la llamada a mongoTemplate.find()
        when(mongoTemplate.find(any(Query.class), eq(RegistroMedico.class))).thenReturn(registros);

        // Ejecución del método con médico vacío
        List<RegistroMedico> resultado = registroMedicoService.obtenerRegistrosFiltrados(tipo, diagnostico, medico, institucion);

        // Verificación
        assertEquals(1, resultado.size());
        assertEquals("12345678", resultado.get(0).getCiPaciente());

        // Verificamos que se haya llamado una vez a mongoTemplate.find()
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(RegistroMedico.class));
    }

    @Test
    void testObtenerRegistrosFiltradosSinInstitucion() {
        // Configuración de la prueba sin institucion
        TipoRegistroMedico tipo = TipoRegistroMedico.CONSULTA;
        String diagnostico = "Gripe";
        String medico = "Dr. Pérez";
        String institucion = "";  // Institución vacía

        // Crear registros médicos
        RegistroMedico registro1 = new RegistroMedico();
        registro1.setId("1");
        registro1.setCiPaciente("12345678");
        registro1.setMedico("Dr. Pérez");
        registro1.setFecha(LocalDate.of(2024, 11, 11));
        registro1.setTipo(TipoRegistroMedico.CONSULTA);
        registro1.setDiagnostico("Gripe");
        registro1.setInstitucion("Hospital General");  // Institución con valor
        registro1.setDescripcion("Consulta por fiebre y tos");
        registro1.setMedicacion("Paracetamol");

        List<RegistroMedico> registros = Arrays.asList(registro1);

        // Simulación de la llamada a mongoTemplate.find()
        when(mongoTemplate.find(any(Query.class), eq(RegistroMedico.class))).thenReturn(registros);

        // Ejecución del método con institución vacía
        List<RegistroMedico> resultado = registroMedicoService.obtenerRegistrosFiltrados(tipo, diagnostico, medico, institucion);

        // Verificación
        assertEquals(1, resultado.size());
        assertEquals("12345678", resultado.get(0).getCiPaciente());

        // Verificamos que se haya llamado una vez a mongoTemplate.find()
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(RegistroMedico.class));
    }


}
