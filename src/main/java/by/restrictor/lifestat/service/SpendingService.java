package by.restrictor.lifestat.service;

import static by.restrictor.lifestat.model.Categories.CATEGORIES;

import by.restrictor.lifestat.model.Spending;
import by.restrictor.lifestat.repository.SpendingRepository;
import java.time.LocalDate;
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
        return spendingRepository.findAll();
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
                row[index] = row[index] + s.getAmount();
            });
        });
        return spendingsByCategory;
    }

    public Spending save(Spending spending) {
        return spendingRepository.save(spending);
    }

}
