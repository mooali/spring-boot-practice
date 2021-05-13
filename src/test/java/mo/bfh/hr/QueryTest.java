package mo.bfh.hr;

import mo.bfh.dto.DepartmentDTO;
import mo.bfh.dto.DepartmentSalaryStatistics;
import mo.bfh.entity.Department;
import mo.bfh.entity.Employee;
import mo.bfh.repository.DepartmentRepository;
import mo.bfh.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class QueryTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityManager em;


    /*
    EX1
     */
    @Test
    public void findAllZeurcher(){
        TypedQuery<Employee> query = em.createQuery("SELECT e from Employee e WHERE e.address.state = 'ZH'", Employee.class);
        List<Employee> zeurcher = query.getResultList();

        assertEquals(3, zeurcher.size());

    }

    /*
    EX1
     */
    @Test
    public void findEmployeeByState(){
        List<Employee> zeurchers = employeeRepository.findByAddressState("ZH");
        assertEquals(3, zeurchers.size());

    }


    @Test
    public void getAverageSalaryPerDepartment(){
        TypedQuery<DepartmentSalaryStatistics> query = em.createNamedQuery(Department.AVG_SALARY, DepartmentSalaryStatistics.class);
        List<DepartmentSalaryStatistics> list = query.getResultList();

        assertEquals(2, list.size());
        for(DepartmentSalaryStatistics dept : list){
            if(dept.getDepartmentName().equals("IT")){
                assertEquals(97200.0, dept.getAvgSalary(),0);
            }
            if(dept.getDepartmentName().equals("HR")){
                assertEquals(95000.0, dept.getAvgSalary(),0);
            }
        }


    }



    @Test
    public void getAverageSalaryPerDepartmentAsNamedQueryWithSpringData(){
        List<DepartmentSalaryStatistics> avg = departmentRepository.avgSalary();
        assertEquals(2, avg.size());
        for(DepartmentSalaryStatistics dept : avg){
            if(dept.getDepartmentName().equals("IT")){
                assertEquals(97200.0, dept.getAvgSalary(),0);
            }
            if(dept.getDepartmentName().equals("HR")){
                assertEquals(95000.0, dept.getAvgSalary(),0);
            }
        }
    }









}
