package co.founders.auth.repository;

import co.founders.auth.model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, java.util.UUID> {
}
