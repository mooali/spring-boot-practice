package mo.bfh.repository;

import mo.bfh.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepositroy extends JpaRepository<Project, Integer> {
}
