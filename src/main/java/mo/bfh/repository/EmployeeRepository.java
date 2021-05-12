package mo.bfh.repository;

import mo.bfh.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByAddressState(String state);

    Optional<Employee> findEmployeesByDepartmentName(String dept);
}
