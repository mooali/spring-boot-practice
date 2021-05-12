package mo.bfh.hr;

import mo.bfh.entity.Employee;
import mo.bfh.repository.DepartmentRepository;
import mo.bfh.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class QueryTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityManager em;


    @Test
    public void findAllZeurcher(){
        TypedQuery<Employee> query = em.createQuery("SELECT e from Employee e WHERE e.address.state = 'ZH'", Employee.class);
        List<Employee> zeurcher = query.getResultList();

        assertEquals(3, zeurcher.size());


    }


}
