# Sistema de Gestión de Historial Médico

Ofrece servicios REST para la administración y consulta de datos almacenados en MongoDB, con soporte para operaciones clave como agregar pacientes, registrar datos médicos y realizar consultas avanzadas.

## Funcionalidades

### API REST

- **Agregar Paciente**  
  **Descripción**: Agrega un Paciente a MongoDB.  
  **Endpoint**: POST /pacientes/agregar-paciente  
  **Formato de entrada**:
  ```json
  {
    "ci": "12345",
    "nombre": "Ruben",
    "apellido": "Rada",
    "fechaNacimiento": "1990-01-01",
    "sexo": "Hombre",
    "historialMedico": null
  }
