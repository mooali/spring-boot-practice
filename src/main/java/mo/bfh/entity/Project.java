package mo.bfh.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {



    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "projct_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    private Integer id;
    private String name;


    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    @ManyToMany
    private Set<Employee> employeeSet = new HashSet<>();


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

