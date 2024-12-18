package com.nsql.tarea2.services;

import com.nsql.tarea2.entidades.RegistroMedico;
import com.nsql.tarea2.enums.TipoRegistroMedico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRegistroMedicoService {
    public ResponseEntity<?> eliminarRegistro(RegistroMedico registro);
    public List<RegistroMedico>  obtenerRegistrosFiltrados(TipoRegistroMedico tipo, String diagnostico, String medico, String institucion);
    //public ResponseEntity<?> actualizarRegistro(String ci, RegistroMedico registro);
    //public ResponseEntity<?> buscarRegistro(String ci);
    public RegistroMedico agregarRegistro(String ci, RegistroMedico registro);

}
