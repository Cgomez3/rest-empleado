package empleado.rest.ini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import empleado.rest.ini.entity.Empleado;
import empleado.rest.ini.repository.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Override
	public Empleado guardarEmpleado(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	@Override
	public Empleado editarEmpleado(Empleado empleado) {
			return empleadoRepository.save(empleado);
		
	}

	@Override
	public Empleado buscarEmpleadoPorDocumento(String nroDocumento) {
		// TODO Auto-generated method stub
		return empleadoRepository.findById(nroDocumento).orElse(null);
	}

	@Override
	public void eliminarEmpleado(String nroDocumento) {
		Empleado empleadoBD=empleadoRepository.findById(nroDocumento).orElse(null);
		if(empleadoBD != null) {
			 empleadoRepository.deleteById(nroDocumento);
		}
	}

	@Override
	public List<Empleado> listarEmpleado() {
		// TODO Auto-generated method stub
		return empleadoRepository.findAll();
	}

}
