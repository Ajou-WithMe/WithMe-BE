package ajou.withme.main.Repository;

import ajou.withme.main.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
    Party findByCode(String code);

    void deleteByCode(String code);
}
