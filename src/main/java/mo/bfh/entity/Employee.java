package mo.bfh.entity;


import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NamedQuery(name = Employee.MIN_SALARY, query = "select new mo.bfh.dto.EmployeeDTO(e.name, e.salary)" +
        "from Employee e where e.salary = " +
        "(select min(e.salary) from Employee e)" )
@NamedEntityGraph(name = Employee.INCLUDE_PHONES, attributeNodes = {@NamedAttributeNode("phones")})
@Entity
public class Employee {

    public static final String MIN_SALARY = "Employee.minSalary";

    public static final String INCLUDE_PHONES = "Employee.includePhones";


    @Id
    @SequenceGenerator(name = "emp_seq", sequenceName = "emp_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    private Integer id; //initial wert null, hingegen bei int ist initial Wert 0;
    private String name;
    private double salary; //bigDecimal wäre besser, da wir mit double probleme mit der Aufrundung haben können.

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Department department;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Address address;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "employee_id")
    private Set<Phone> phones = new HashSet<>();;

    @ManyToMany (mappedBy = "employees",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<Project> projects = new HashSet<>();;

    @ManyToOne
    private Employee supervisor;

    @OneToMany(mappedBy = "supervisor")
    private Set<Employee> supervise = new HashSet<>();;


    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public Employee() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Set<Employee> getSupervise() {
        return supervise;
    }

    public void setSupervise(Set<Employee> supervise) {
        this.supervise = supervise;
    }

    public void addDirect(Employee employee){
        employee.setSupervisor(this);
        supervise.add(employee);
    }

    public void addPhone(Phone phone){
        phones.add(phone);
    }

    public void removeSupervisor(Employee employee){
        employee.setSupervisor(null);
        supervise.remove(employee);
    }

    public void removePhone(Phone phone){
        phones.remove(phone);
    }

}
