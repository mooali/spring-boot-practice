package mo.bfh.hr;

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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    /**
     * EX1
     * Find all employees who live in the canton of Zurich
     */
    @Test
    public void findEmployeeByState(){
        List<Employee> zeurchers = employeeRepository.findByAddressState("ZH");
        assertEquals(3, zeurchers.size());

    }

    /**
     * EX2
     * Calculate the average salary of employees per department
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
     * Calculate the average salary of employees per department
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
     * Find the employee with the lowest salary
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
     * Find the employee with the lowest salary
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
     * EX3
     * Find the employee with the lowest salary
     * Native SQL
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
    * Create a query that returns the employee name and the complete address,
    * ordered by the employee’s name
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
     * Create a query that returns the employee name and the complete address,
     * ordered by the employee’s name
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
     * Ex5  Find employees who are not assigned to a project
     */
    @Test
    public void findAllEmployeesWithoutProject(){
        TypedQuery<Employee> query = em.createQuery("select e from Employee e where e.projects is empty", Employee.class);
        List<Employee> list = query.getResultList();

        assertEquals(3, list.size());
    }

    /**
     * Ex6	Find all business phone numbers ordered by number
     */

    @Test
    public void findAllWorkPhonesOrderedByNumber(){
        TypedQuery<String> query = em.createQuery("select p.phoneNumber from Phone p" +
                " where p.type = :type order by p.phoneNumber", String.class);
        query.setParameter("type", PhoneType.OFFICE);

        List<String> list = query.getResultList();

        assertEquals(5, list.size());

    }

    /**
     * Ex 7 Find employees who do not have a business phone number yet
     */
   @Test
    public void findAllEmployeWithoutWorkPhone(){
        TypedQuery<Employee> query = em.createQuery("select e from Employee e where e not in" +
                        " (select distinct e from Employee e join e.phones p where p.type = :type)",
                Employee.class);
       query.setParameter("type", PhoneType.OFFICE);

        List<Employee> list = query.getResultList();
        assertEquals(1, list.size());
    }

    /**
     * SQL-Injection
     */
    @Test
    public void sqlInjection(){
        //Parameter from UI
        String id = "1";
        // SQL Injection
        id += " OR 1 = 1";

        String sqlString = "select e from Employee e where e.id = " + id;

        TypedQuery<Employee> query = em.createQuery(sqlString, Employee.class);
        List<Employee> list = query.getResultList();

        //return all instead of only one Employee!!!!
        assertEquals(6, list.size());

        // thats why it's better to use Query Parameter (named) prepared statements
        //Hibernate and JDBC will check for sql-injection
        /*
        String sqlStringWithParameter = "select e from Employee e where e.id = :id ";
        TypedQuery<Employee> queryWithParam = em.createQuery(sqlStringWithParameter, Employee.class);
        queryWithParam.setParameter("id", id);
         */
    }

    @Test
    public void employeeIncludePhonesJpql() {
        TypedQuery<Employee> query = em.createQuery("select distinct e from Employee e left outer join fetch e.phones p", Employee.class);
        List<Employee> list = query.getResultList();

        assertEquals(6, list.size());
    }

    @Test
    public void employeeIncludePhonesNamedEntityGraph() {
        List<Employee> list = employeeRepository.findDistinctByNameLike("%");

        assertEquals(6, list.size());
    }

    @Test
    public void employeeIncludePhonesEntityGraph() {
        TypedQuery<Employee> query = em.createQuery("select distinct e from Employee e", Employee.class);
        query.setHint("javax.persistence.loadgraph", Employee.INCLUDE_PHONES);
        List<Employee> list = query.getResultList();

        assertEquals(6, list.size());
    }


}
