package mo.bfh.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@NamedQuery(name = Department.AVG_SALARY, query = "select new mo.bfh.dto.DepartmentSalaryStatistics(e.department.name, avg(e.salary))"+
        " from Employee e group by e.department.name")
public class Department {

    public static final String AVG_SALARY = "Department.avgSalary";

    @Id
    @SequenceGenerator(name = "dep_seq", sequenceName = "dep_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dep_seq")
    private Integer id;
    private String name;
    @OneToMany (mappedBy = "department")
    private Set<Employee> employees = new HashSet<>();


    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
