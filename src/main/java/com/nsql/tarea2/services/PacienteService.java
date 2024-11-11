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

    @Override
    public Page<RegistroMedico> consultarHistorial(String ci, Pageable pageable) {
        if (!pacienteRepository.existsById(ci)) {
            return null;
        }
        return registroMedicoRepository.findByCiPacienteOrderByFechaDesc(ci, pageable);
    }

}
