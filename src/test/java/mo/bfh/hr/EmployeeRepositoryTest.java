package mo.bfh.hr;

import mo.bfh.entity.*;
import mo.bfh.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private ProjectRepositroy projectRepositroy;

    private Integer employeeSupervisorId;
    private Integer employeeId;

    @BeforeEach
    public void insertTestData() {

        Department itDep = new Department("IT");
        departmentRepository.saveAndFlush(itDep);

        Address address1 = new Address("Kirchbergstrasse 6", "Burgdorf", "BE", "3400");
        addressRepository.saveAndFlush(address1);

        Address address2= new Address("Uetikon 6", "Uetikon am See", "ZH", "8707");
        addressRepository.saveAndFlush(address2);

        Phone phone2 = new Phone("0782200400", PhoneType.MOBILE);
        phoneRepository.saveAndFlush(phone2);


        Project bookstore = new Project("Bookstore");
        Phone phone1 = new Phone("0782100200", PhoneType.MOBILE);
        phoneRepository.saveAndFlush(phone1);


        Employee employee1 = new Employee("Mo", 300);
        employee1.setDepartment(itDep);
        employee1.setAddress(address1);
        employee1.addPhone(phone1);
        employeeRepository.saveAndFlush(employee1);

        Employee employeeSupervisor = new Employee("Chef", 1000);
        employeeSupervisor.setAddress(address2);
        employee1.getProjectSet().add(bookstore);
        employeeSupervisor.addDirect(employee1);
        employeeRepository.saveAndFlush(employeeSupervisor);


        employeeSupervisorId = employeeSupervisor.getId();
        employeeId = employee1.getId();

        projectRepositroy.saveAndFlush(bookstore);

    }

    @Test
    public void findEmployees() {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        assertTrue(optionalEmployee.isPresent());

        Optional<Employee> employeeSupervisor = employeeRepository.findById(employeeSupervisorId);
        assertTrue(employeeSupervisor.isPresent());
    }
}
