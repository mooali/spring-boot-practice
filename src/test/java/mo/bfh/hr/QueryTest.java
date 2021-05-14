package mo.bfh.hr;

import mo.bfh.dto.DepartmentDTO;
import mo.bfh.dto.DepartmentSalaryStatistics;
import mo.bfh.dto.EmployeeDTO;
import mo.bfh.dto.EmployeeNameWithAddress;
import mo.bfh.entity.Department;
import mo.bfh.entity.Employee;
import mo.bfh.entity.PhoneType;
import mo.bfh.repository.DepartmentRepository;
import mo.bfh.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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


    /**
     * EX2
     */
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


    /**
     * EX2
     */
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


    /**
     * EX3
     */

    @Test
    public void getMinSalaryPerEmployeeWithDTO(){
        List<EmployeeDTO> employees = employeeRepository.minSalary();
        assertEquals(2, employees.size());

        assertEquals("Luca Traugott", employees.get(0).getName());
        assertEquals("Lea Schulze", employees.get(1).getName());

    }

    /**
     * EX3
     */
    @Test
    public void getMinSalaryPerEmployee(){
        TypedQuery<Employee> query = em.createQuery("select e from Employee e " +
                        "where e.salary = (select min(e.salary) from Employee e)",
                Employee.class
        );
        List<Employee> employees = query.getResultList();
        assertEquals(2, employees.size());

        assertEquals("Luca Traugott", employees.get(0).getName());
        assertEquals("Lea Schulze", employees.get(1).getName());

    }

    /**
     * EX3 Native SQL
     */
    @Test
    public void getMinSalaryPerEmployeeWithSql(){
        Query query = em.createNativeQuery("select * from employee where salary = (select min(salary) from employee)", Employee.class);
        List<Employee> employees = query.getResultList();
        assertEquals(2, employees.size());

        assertEquals("Luca Traugott", employees.get(0).getName());
        assertEquals("Lea Schulze", employees.get(1).getName());

    }


    @Test
    public void findEmployeeByName(){
        List<Employee> employees = employeeRepository.findByNameLikeWithQuery("Luca Traugott");
        assertEquals("Luca Traugott", employees.get(0).getName());
    }


    /**
    * EX 4
    * this result is danger, because we get address as an Entity, we could modify it but that's
    * not what we want
    * this query is bad
    * we should use DTO/Interface Projection instead
     */
    @Test
    public void findAllEmployeeNameWithAddress(){
        Query query = em.createQuery("select e.name, e.address from Employee e order by e.name");
        List<Employee> list = query.getResultList();
        assertEquals(6, list.size());

    }

    /**
     * EX4
     * using interface projection
     * better
     */
    @Test
    public void findEmployeeNameWithAddressWithInterfaceProjection(){
        List<EmployeeNameWithAddress> list = employeeRepository.findNameWithAddress();

        assertEquals(6, list.size());
        list.forEach(e -> {
            assertNotNull(e.getName());
            assertNotNull(e.getAddress());
        });
    }


    /**
     * EX5
     * Employee without Project
     */
    @Test
    public void findAllEmployeesWithoutProject(){
        TypedQuery<Employee> query = em.createQuery("select e from Employee e where e.projects is empty", Employee.class);
        List<Employee> list = query.getResultList();

        assertEquals(3, list.size());
    }

    /**
     * EX6
     * Office Phone Numbers ordered by Number
     */
    @Test
    public void findAllWorkPhonesOrderedByNumber(){
        TypedQuery<String> query = em.createQuery("select p.phoneNumber from Phone p" +
                " where p.type = :type order by p.phoneNumber", String.class);
        query.setParameter("type", PhoneType.OFFICE);

        List<String> list = query.getResultList();

        assertEquals(5, list.size());

    }











}
