package mo.bfh.dto;

public class EmployeeDTO {

    private final String name;
    private final double salary;

    public EmployeeDTO(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

}
