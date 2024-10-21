package com.nsql.tarea2.service;

import com.nsql.tarea2.entidades.RegistroMedico;
import org.springframework.http.ResponseEntity;

public interface IRegistroMedicoService {
    public ResponseEntity<?> agregarRegistro(String ci, RegistroMedico registro);
    public ResponseEntity<?> eliminarRegistro(RegistroMedico registro);

    //public ResponseEntity<?> actualizarRegistro(String ci, RegistroMedico registro);
    //public ResponseEntity<?> buscarRegistro(String ci);
}
