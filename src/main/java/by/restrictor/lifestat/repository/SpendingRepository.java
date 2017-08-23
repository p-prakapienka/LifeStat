package by.restrictor.lifestat.repository;

import by.restrictor.lifestat.model.Spending;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpendingRepository extends JpaRepository<Spending, Long> {

    List<Spending> findAllByDate(LocalDate date);

}
