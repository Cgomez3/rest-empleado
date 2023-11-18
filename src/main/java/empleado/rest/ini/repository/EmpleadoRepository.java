package empleado.rest.ini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import empleado.rest.ini.entity.Empleado;
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String>{

}
