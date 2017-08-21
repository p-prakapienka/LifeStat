package by.restrictor.lifestat.service;

import static by.restrictor.lifestat.model.Categories.CATEGORIES;

import by.restrictor.lifestat.model.Spending;
import by.restrictor.lifestat.repository.SpendingRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpendingService {

    @Autowired
    private SpendingRepository spendingRepository;

    public List<Spending> getRecentSpendings() {
        return spendingRepository.findAll().stream()
            .sorted(Comparator.comparing(Spending::getDate).reversed())
            .collect(Collectors.toList());
    }

    public Map<LocalDate, double[]> getDailySpendings() {
        List<Spending> spendings = spendingRepository.findAll();
        Map<LocalDate, List<Spending>> spendingsByDate = spendings
            .stream().collect(Collectors.groupingBy(Spending::getDate));
        Map<LocalDate, double[]> spendingsByCategory = new TreeMap<>();
        spendingsByDate.forEach((k, v) -> {
            spendingsByCategory.putIfAbsent(k, new double[CATEGORIES.size()]);
            v.forEach(s -> {
                double[] row = spendingsByCategory.get(k);
                int index = CATEGORIES.indexOf(s.getCategory());
                BigDecimal result = new BigDecimal(row[index]).add(new BigDecimal(s.getAmount()));
                row[index] = result.setScale(2, RoundingMode.HALF_UP).doubleValue();
            });
        });
        return spendingsByCategory;
    }

    public Spending save(Spending spending) {
        return spendingRepository.save(spending);
    }

}
