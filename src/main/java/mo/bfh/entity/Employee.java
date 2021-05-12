package mo.bfh.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

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
    private Set<Phone> phoneSet = new HashSet<>();;

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

    public Set<Phone> getPhoneSet() {
        return phoneSet;
    }

    public void setPhoneSet(Set<Phone> phoneSet) {
        this.phoneSet = phoneSet;
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
        phoneSet.add(phone);
    }

    public void removeSupervisor(Employee employee){
        employee.setSupervisor(null);
        supervise.remove(employee);
    }

    public void removePhone(Phone phone){
        phoneSet.remove(phone);
    }

}
