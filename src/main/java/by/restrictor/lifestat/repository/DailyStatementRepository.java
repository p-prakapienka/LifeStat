package by.restrictor.lifestat.repository;

import by.restrictor.lifestat.model.DailyStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyStatementRepository extends JpaRepository<DailyStatement, Long> {

}
