Gestión de Historial Médico - Backend NoSQL
Descripción del Proyecto

Este proyecto consiste en un backend diseñado para gestionar historiales médicos de pacientes, desarrollado como parte del Segundo Laboratorio de Bases de Datos NoSQL 2024. Ofrece servicios REST para la administración y consulta de datos almacenados en una base de datos NoSQL, con soporte para operaciones clave como agregar pacientes, registrar datos médicos y realizar consultas avanzadas.
Funcionalidades
Servicios REST implementados:

    Agregar Paciente
        Descripción: Permite registrar un nuevo paciente en el sistema.
        Endpoint: POST /api/pacientes
        Formato de entrada:

    {
      "ci": "12345678",
      "nombre": "Juan",
      "apellido": "Pérez",
      "fechaNacimiento": "1990-01-01",
      "sexo": "M"
    }

    Respuestas:
        201 Created: Paciente agregado con éxito.
        401 Conflict: "El paciente ya existe".

Agregar Registro Médico

    Descripción: Registra información médica asociada a un paciente.
    Endpoint: POST /api/pacientes/{ci}/registros
    Formato de entrada:

        {
          "fecha": "2024-11-01",
          "tipo": "Consulta",
          "diagnostico": "Dolor de cabeza",
          "medico": "Dra. Ana López",
          "institucion": "Clínica Central",
          "descripcion": "Dolor recurrente en la sien",
          "medicacion": "Paracetamol"
        }

        Respuestas:
            201 Created: Registro agregado con éxito.
            402 Not Found: "No existe un paciente con la cédula aportada como parámetro".

    Consultar Historial Médico
        Descripción: Obtiene los registros médicos de un paciente, con soporte para paginación.
        Endpoint: GET /api/pacientes/{ci}/historial
        Parámetros opcionales:
            page: Página a consultar (por defecto: 0).
            size: Cantidad de resultados por página (por defecto: 10).
        Respuestas:
            200 OK: Lista de registros médicos.
            402 Not Found: "No existe un paciente con la cédula aportada como parámetro".

    Obtener Registros por Criterio
        Descripción: Filtra registros médicos según criterios combinados (tipo, diagnóstico, médico, institución).
        Endpoint: GET /api/registros
        Parámetros:
            tipo, diagnostico, medico, institucion.
        Respuestas:
            200 OK: Lista de registros médicos filtrados.

Instalación y Configuración
Requisitos previos

    Java 21 (o superior).
    Maven 3.9.9.
    Base de datos NoSQL: MongoDB.
    Postman para pruebas.

Pasos para ejecutar

    Clona el repositorio:

git clone https://github.com/tu_usuario/nombre_proyecto.git
cd nombre_proyecto

Configura la base de datos MongoDB en el archivo application.properties:

spring.data.mongodb.uri=mongodb://localhost:27017/historial_medico

Compila y ejecuta la aplicación:

    mvn spring-boot:run

Modelo de Base de Datos

La solución utiliza MongoDB para gestionar datos NoSQL, lo que permite:

    Flexibilidad en la estructura de documentos.
    Escalabilidad para manejar grandes volúmenes de datos.

Esquema de Colecciones

    Pacientes

{
  "_id": "12345678",
  "nombre": "Juan",
  "apellido": "Pérez",
  "fechaNacimiento": "1990-01-01",
  "sexo": "M",
  "historial": []
}

Registros Médicos

    {
      "_id": "unique-id",
      "pacienteId": "12345678",
      "fecha": "2024-11-01",
      "tipo": "Consulta",
      "diagnostico": "Dolor de cabeza",
      "medico": "Dra. Ana López",
      "institucion": "Clínica Central",
      "descripcion": "Dolor recurrente en la sien",
      "medicacion": "Paracetamol"
    }

Pruebas
Casos de prueba

Los casos de prueba se encuentran documentados y organizados en un archivo de colección de Postman:

    Ruta: /postman/HistorialMedico.postman_collection.json.
    Importa la colección en Postman y utiliza el entorno preconfigurado para realizar pruebas de los endpoints.

Requerimientos Opcionales

    Dockerización:
        Contenedor para la aplicación Spring Boot.
        Contenedor para MongoDB.
    Pruebas de carga:
        Realizadas con JMeter. Resultados disponibles en /reports/jmeter.

Créditos

Autores: [Tu nombre y el de tu equipo].
Contacto: [Correo electrónico].





// Agregar Registro si hay que pasar parametros o request body
// Ci en el mongo que hace _id
// Si puede Registros medicos iguales o no importa
// Si tenemos q pasar paginacion por parametro y si esta bien
// Se pueden poner mas de un tipo como criterio
// Si precisamos validaciones como por ejemplo el tipo

//cada vez que se hace un commit hay que hacer restart al jenkins 
//sudo systemctl restart jenkins
