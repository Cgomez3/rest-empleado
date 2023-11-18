
package empleado.rest.ini.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import empleado.rest.ini.entity.Empleado;
import empleado.rest.ini.service.EmpleadoService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200/")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService empleadoService;

	@GetMapping("/listado-empleados")
	public ResponseEntity<?> listarEmpleado() {
		return ResponseEntity.ok(empleadoService.listarEmpleado());
	}

	@GetMapping("/obtener-empleado-por-documento/{nro-doc}")
	public ResponseEntity<?> buscarEmpleado(@PathVariable("nro-doc") String nroDoc) {
		Empleado empleado = null;
		Map<String, Object> response = new HashMap<>();
		try {
			empleado = empleadoService.buscarEmpleadoPorDocumento(nroDoc);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error:" + e.getMessage());
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (empleado == null) {
			response.put("mensaje", "El Empleado no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}

	@PostMapping("/crear-empleado")
	public ResponseEntity<?> crearEmpleado(@Valid @RequestBody Empleado empleado, BindingResult result) {

		Empleado empleadoLocal= null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors().stream().map(err -> "El campo'" + err.getField() +"' "+ err.getDefaultMessage()).toList();
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} 
		try {
			empleadoLocal = empleadoService.guardarEmpleado(empleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al registrar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Empleado se ha credo");
		response.put("empleado", empleadoLocal);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/editar-empleado/{nro-doc}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> editarEmpleado(@Valid @RequestBody Empleado empleado,BindingResult result, @PathVariable(name = "nro-doc") String nroDoc) {
		Empleado empleadoActual= empleadoService.buscarEmpleadoPorDocumento(nroDoc);
		Empleado empleadoUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err -> "El campo'" + err.getField() +"' "+ err.getDefaultMessage()).toList();
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (empleadoActual == null) {
			response.put("mensaje", "El Empleado no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			empleadoActual.setNombres(empleado.getNombres());
			empleadoActual.setApellidos(empleado.getApellidos());
			empleadoActual.setEdad(empleado.getEdad());
			empleadoActual.setFechaNacimiento(empleado.getFechaNacimiento());
			empleadoActual.setSalario(empleado.getSalario());
			
			empleadoUpdate =empleadoService.editarEmpleado(empleadoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El Empleado se ha modificado con exito!");
		response.put("empleado", empleadoUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/eliminar-empleado/{nro-doc}")
	public ResponseEntity<?> eliminarEmpleado(@PathVariable("nro-doc") String nroDoc) {
		Map<String, Object> response = new HashMap<>();
		try {
			empleadoService.eliminarEmpleado(nroDoc);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Elimnar el Empleado en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El Empleado se ha Eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
