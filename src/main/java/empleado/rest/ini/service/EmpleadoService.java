package empleado.rest.ini.service;

import java.util.List;

import empleado.rest.ini.entity.Empleado;

public interface EmpleadoService {
   public Empleado guardarEmpleado(Empleado empleado);
   public Empleado editarEmpleado(Empleado empleado);
   public Empleado buscarEmpleadoPorDocumento(String nroDocumento);
   public void eliminarEmpleado(String nroDocumento);
   public List<Empleado> listarEmpleado();
}
