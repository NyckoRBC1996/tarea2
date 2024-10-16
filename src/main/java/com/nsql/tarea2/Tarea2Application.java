package com.nsql.tarea2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class Tarea2Application {

	public static void main(String[] args) {
		SpringApplication.run(Tarea2Application.class, args);
	}

	@Component
	public class DatabaseTest implements CommandLineRunner {

		@Autowired
		private MongoTemplate mongoTemplate;

		@Override
		public void run(String... args) throws Exception {
			// Crear un objeto de prueba
			Paciente paciente = new Paciente();
			paciente.setCi("12345678");
			paciente.setNombre("Juan");
			paciente.setApellido("Pérez");
			paciente.setFechaNacimiento("1990-01-01");
			paciente.setSexo("M");

			// Guardar el objeto en la base de datos
			mongoTemplate.save(paciente);

			// Recuperar todos los pacientes
			List<Paciente> pacientes = mongoTemplate.findAll(Paciente.class);

			// Imprimir los pacientes para verificar la conexión
			pacientes.forEach(p -> System.out.println(p.getNombre() + " " + p.getApellido()));
		}
	}

	@Document(collection = "pacientes")
	public static class Paciente {
		@org.springframework.data.annotation.Id
		private String ci; // Cédula de Identidad
		private String nombre;
		private String apellido;
		private String fechaNacimiento;
		private String sexo;

		// Getters y Setters
		public String getCi() { return ci; }
		public void setCi(String ci) { this.ci = ci; }

		public String getNombre() { return nombre; }
		public void setNombre(String nombre) { this.nombre = nombre; }

		public String getApellido() { return apellido; }
		public void setApellido(String apellido) { this.apellido = apellido; }

		public String getFechaNacimiento() { return fechaNacimiento; }
		public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

		public String getSexo() { return sexo; }
		public void setSexo(String sexo) { this.sexo = sexo; }
	}
}
