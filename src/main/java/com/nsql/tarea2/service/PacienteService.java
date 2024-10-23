package com.nsql.tarea2.service;

import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.repositories.PacienteRepository;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


import com.nsql.tarea2.entidades.Paciente;

@Service
public class PacienteService implements IPacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private RegistroMedicoRepository registroMedicoRepository;

    @Override
    public ResponseEntity<?> agregarPaciente(Paciente paciente) {
        if (pacienteRepository.existsById(paciente.getCi())) {
            return ResponseEntity.status(401).body("El paciente ya existe");
        }
        pacienteRepository.save(paciente);
        return ResponseEntity.ok("Paciente agregado exitosamente");
    }

    @Override
    public void eliminarPaciente(Paciente Paciente) {
        pacienteRepository.delete(Paciente);
    }

    @Override
    public List<Paciente> obtenerPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public ResponseEntity<?> buscarPacientePorCi(String ci){
        System.out.println(pacienteRepository.findById(ci));
        return ResponseEntity.ok(pacienteRepository.findById(ci));
    }

    @Override
    public ResponseEntity<?> agregarRegistro(String ci, RegistroMedico registro) {
        if (!pacienteRepository.existsById(ci)) {
            return ResponseEntity.status(402).body("No existe un paciente con la cédula aportada como parámetro");
        }
        registro.setCiPaciente(ci);
        registroMedicoRepository.save(registro);
        return ResponseEntity.ok("Registro médico agregado exitosamente");
    }

    @Override
    public ResponseEntity<?> consultarHistorial(String ci) {
        if (!pacienteRepository.existsById(ci)) {
            return ResponseEntity.status(402).body("No existe un paciente con la cédula aportada como parámetro");
        }

        List<RegistroMedico> historial = registroMedicoRepository.findByCiPacienteOrderByFechaDesc(ci);
        return ResponseEntity.ok(historial);
    }
}

/*spring.application.name=tarea2
# Configuración del puerto del servidor
server.port=8080
        # configuracion MongoDb
spring.datasource.url=jdbc:mysql://localhost:27017/NoSql
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update

# Habilitar la consola H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Mostrar consultas SQL en la consola
spring.jpa.show-sql=true

# Configuración de la ubicación de las entidades JPA
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true*/
