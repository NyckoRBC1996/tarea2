package com.nsql.tarea2.services;

import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.repositories.PacienteRepository;
import com.nsql.tarea2.repositories.RegistroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Paciente agregarPaciente(Paciente paciente) {
        if (pacienteRepository.existsById(paciente.getCi())) {
            return null;
        }
        pacienteRepository.save(paciente);
        return paciente;
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
        if (!pacienteRepository.existsById(ci)) {
            return ResponseEntity.status(401).body("El paciente no existe");
        }
        return ResponseEntity.ok(pacienteRepository.findById(ci));
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
