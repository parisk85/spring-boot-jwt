package gr.parisk85.springbootjwt.repository;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> getByUsername(String username);
    ApplicationUser save(ApplicationUser applicationUser);
}
