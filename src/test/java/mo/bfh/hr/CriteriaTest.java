package mo.bfh.hr;

import mo.bfh.dto.DepartmentSalaryStatistics;
import mo.bfh.entity.*;
import mo.bfh.entity.Address_;
import mo.bfh.entity.Employee_;
import mo.bfh.repository.DepartmentRepository;
import mo.bfh.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CriteriaTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EntityManager em;



    @Test
    public void findEmployeeByNameWithCriteriaApi(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> employeeRoot = cq.from(Employee.class);
        cq.select(employeeRoot).where(cb.equal(employeeRoot.get("name"),"Luca Traugott"));

        TypedQuery<Employee> query = em.createQuery(cq);
        List<Employee> list = query.getResultList();

        assertEquals("Luca Traugott", list.get(0).getName());

    }



    @Test
    public void findAllZuercher() {
        List<Employee> zuercher = employeeRepository.findAll((employee, cq, cb) -> {
            Join<Employee, Address> address = employee.join(Employee_.address);
            return cb.equal(address.get(Address_.state), "ZH");
        });

        assertEquals(3, zuercher.size());
    }


    @Test
    public void getAvarageSalaryPerDept(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentSalaryStatistics> cq = cb.createQuery(DepartmentSalaryStatistics.class);

        Root<DepartmentSalaryStatistics> root = cq.from(DepartmentSalaryStatistics.class);


    }




}
