package mo.bfh.repository;

import mo.bfh.dto.EmployeeDTO;
import mo.bfh.dto.EmployeeNameWithAddress;
import mo.bfh.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByAddressState(String state);

    Optional<Employee> findEmployeesByDepartmentName(String dept);

    List<EmployeeDTO> minSalary();

    @Query(value = "select * from employee where name like :name", nativeQuery = true)
    List<Employee> findByNameLikeWithQuery(String name);

    @Query("select e.name as name, e.address as address from Employee e")
    List<EmployeeNameWithAddress> findNameWithAddress();
}
