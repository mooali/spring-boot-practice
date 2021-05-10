package mo.bfh.dto;

public class DepartmentDTO {

    private final String name;
    private final Long numberOfEmployee;

    public DepartmentDTO(String name, Long numberOfEmployee) {
        this.name = name;
        this.numberOfEmployee = numberOfEmployee;
    }

    public String getName() {
        return name;
    }

    public Long getNumberOfEmployee() {
        return numberOfEmployee;
    }
}
