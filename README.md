Sistema de Gestión de Historial Médico (En la pestaña Code se ve Bien)

Ofrece servicios REST para la administración y consulta de datos almacenados en MongoDB, con soporte para operaciones clave como agregar pacientes, registrar datos médicos y realizar consultas avanzadas.w

Funcionalidades

API REST

Agregar Paciente
    Descripción: Agrega un Paciente a MongoDB.
    Endpoint: POST /pacientes/agregar-paciente
    Formato de entrada:
        {
        "ci": "12345",
        "nombre": "Ruben",
        "apellido": "Rada",
        "fechaNacimiento": "1990-01-01",
        "sexo": "Hombre",
        "historialMedico": null
        }
    Respuestas:
        201 Created: Paciente agregado con éxito.
        401 Conflict: "El paciente ya existe".

Agregar Registro Médico
    Descripción: Agrega un Registro Medico a un Paciente.
    Endpoint: POST /registros/agregar-registro
    Formato de entrada:
        {
            "paciente": {
            "ci": "12345678"
            },
            "registroMedico": {
                "fecha": "2024-10-15",
                "tipo": "CONSULTA",
                "diagnostico": "Dolor de Cabeza",
                "medico": "Dr. RAMBO",
                "institucion": "Funeraria Mercedez",
                "descripcion": "Consulta por Balacera",
                "medicacion": "Bala calibre 22"
            }
        }
    Respuestas:
        201 Created: Registro agregado con éxito.
        402 Not Found: "No existe un paciente con la cédula aportada como parámetro".
    
Consultar Historial Médico
    Descripción: Obtiene los registros médicos de un paciente, con paginación.
    Endpoint: GET /pacientes/consultar-historal 
    opcionales: pageNumber: Página a consultar (por defecto: 0)
                pageNumber: Cantidad de resultados por página (por defecto: 10)
    Formato de entrada:
        {
            "ci": "123456789",
            "pageSize" : "5",
            "pageNumber" : "1"
        }
    Respuestas:
        200 OK: Lista de registros médicos
        402 Not Found: "No existe un paciente con la cédula aportada como parámetro".
    
Obtener Registros por Criterio
    Descripción: Filtra registros médicos según criterios combinados(tipo, diagnóstico, médico, institución) o ningun criterio.
    Endpoint: GET /registros/obtener-registros-por-criterio
    Formato de entrada:
        {   
            "tipo":"Consulta",
            "medico": "Dr. RAMBO"
        }
    Respuestas:
        200 OK: Lista de registros médicos filtrados.
        400 BAD REQUEST: Tipo de registro médico inválido. Valores permitidos: CONSULTA, EXAMEN, INTERNACION.
        
Instalación y Configuración

    Requisitos previos
        Java 21 (o superior).
        Maven 3.9.9.
        Base de datos NoSQL: MongoDB.
        Postman para pruebas.
        
    Pasos para ejecutar
    
        Clona el repositorio:
            git clone https://github.com/NyckoRBC1996/tarea2.git cd nombre_proyecto

        Limpiar Maven:
            mvn clean install
        
        Ejecuta la aplicación desde comandos:
            mvn spring-boot:run

        Ejecuta la aplicación desde Ide:
            boton play en el Ide
        
    Modelo de Base de Datos
        
        Se eligió MongoDB debido a la experiencia previa adquirida en proyectos anteriores, lo que facilitó la implementación.
        Su curva de aprendizaje es más sencilla en comparación con otras bases de datos NoSQL, permitiendo un desarrollo más rápido y eficiente. 
        Además, MongoDB cumple con los requisitos del proyecto, como la capacidad de manejar datos no estructurados y la posibilidad de realizar                             consultas flexibles.

        Otro factor clave fue que MongoDB es una base de datos gratuita, lo que reduce costos sin sacrificar rendimiento. Su escalabilidad y 
        adecuación al modelo de datos, con documentos JSON, la hacen ideal para este tipo de aplicaciones.
        
        Esquema de Colecciones
        
            Pacientes
                {
                  "_id": "string", //CI
                  "nombre": "string",
                  "apellido": "string",
                  "fechaNacimiento": "date",
                  "sexo": "string",
                  "_class": "string"
                }
                
            Registros Médicos
                {
                  "_id": "ObjectId",
                  "_class": "string",
                  "ciPaciente": "string",
                  "descripcion": "string",
                  "diagnostico": "string",
                  "fecha": "date",
                  "institucion": "string",
                  "medicacion": "string",
                  "medico": "string",
                  "tipo": "string"
                }
                
                Pruebas con Postman:
                
                    Coleccion: https://www.postman.com/security-astronaut-21830912/workspace/tarea2/collection/35029060-d2f441a4-08bb-434f-88d6-555fd381522d?action=share&creator=35029060

                    
                    
                    Los casos de prueba se encuentran documentados y organizados en un archivo de colección de Postman:Ruta: /postman/HistorialMedico.postman_collection.json.Importa la colección en Postman y utiliza el entorno preconfigurado para realizar pruebas de los endpoints.Requerimientos OpcionalesDockerización:Contenedor para la aplicación Spring Boot.Contenedor para MongoDB.Pruebas de carga:Realizadas con JMeter. Resultados disponibles en /reports/jmeter.CréditosAutores: [Tu nombre y el de tu equipo].Contacto: [Correo electrónico].cada vez que se hace un commit hay que hacer restart al jenkinssudo systemctl restart jenkins./jmeter.sh//Para crear el JARmvn clean package//Buildear el dockersudo docker build -t tarea2 .//Levantarsudo docker run -p 8080:8080 tarea2//Frenarsudo docker stop focused_leakey
