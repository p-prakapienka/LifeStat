package by.restrictor.lifestat.service;

import by.restrictor.lifestat.model.DailyStatement;
import by.restrictor.lifestat.model.Spending;
import by.restrictor.lifestat.repository.DailyStatementRepository;
import by.restrictor.lifestat.repository.SpendingRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpendingService {

    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private DailyStatementRepository dailyStatementRepository;

    public List<Spending> getRecentSpendings() {
        return spendingRepository.findAll().stream()
            .sorted(Comparator.comparing(Spending::getDate).reversed())
            .collect(Collectors.toList());
    }

    public List<DailyStatement> getDailyStatements() {
        List<DailyStatement> dailyStatements = dailyStatementRepository.findAll();
        dailyStatements.addAll(getUnsubmitted());

        return dailyStatements.stream()
            .sorted(Comparator.comparing(DailyStatement::getDate))
            .collect(Collectors.toList());
    }

    public Spending save(Spending spending) {
        return spendingRepository.save(spending);
    }

    @Transactional
    public DailyStatement submitDate(LocalDate date) {
        List<Spending> spendings = spendingRepository.findAllByDate(date);
        DailyStatement dailyStatement = DailyStatement
            .fromSpendings(date, spendings);

        dailyStatement = dailyStatementRepository.save(dailyStatement);
        spendingRepository.delete(spendings);

        return dailyStatement;
    }

    private List<DailyStatement> getUnsubmitted() {
        return spendingRepository.findAll().stream()
            .collect(Collectors.groupingBy(Spending::getDate))
            .entrySet().stream()
            .map(e -> {
                DailyStatement ds = DailyStatement.fromSpendings(e.getKey(), e.getValue());
                ds.setGenerated(true);
                return ds;
            })
            .collect(Collectors.toList());
    }

}
