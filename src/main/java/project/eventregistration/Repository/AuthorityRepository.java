package project.eventregistration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.eventregistration.Models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
