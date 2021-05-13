package mo.bfh.repository;

import mo.bfh.dto.DepartmentDTO;
import mo.bfh.dto.DepartmentSalaryStatistics;
import mo.bfh.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<DepartmentSalaryStatistics> avgSalary();

}
