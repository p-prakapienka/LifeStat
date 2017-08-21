package by.restrictor.lifestat.controller;

import static by.restrictor.lifestat.model.Categories.CATEGORIES;

import by.restrictor.lifestat.model.Income;
import by.restrictor.lifestat.model.Spending;
import by.restrictor.lifestat.repository.IncomeRepository;
import by.restrictor.lifestat.service.SpendingService;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RootController {

    @Autowired
    private SpendingService spendingService;

    @Autowired
    private IncomeRepository incomeRepository;

    @GetMapping("/")
    public String root(Model model) {
        List<Spending> spendings = spendingService.getRecentSpendings();
        model.addAttribute("spendings", spendings);

        model.addAttribute("categories", CATEGORIES);

        Map<LocalDate, double[]> dailySpendings = spendingService.getDailySpendings();
        model.addAttribute("dailySpendings", dailySpendings);

        double total = countTotal(dailySpendings.values());
        double incomes = incomeRepository.findAll().stream().findFirst().orElse(new Income()).getAmount();
        model.addAttribute("balance", incomes - total);
        return "home";
    }

    @PostMapping("/")
    @Transactional
    public String save(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam("category") String category, @RequestParam("amount") double amount) {
        Spending spending = new Spending(date, category, amount);
        spendingService.save(spending);
        return "redirect:/";
    }

    @GetMapping("/income")
    public String getIncome(Model model) {
        List<Income> incomes = incomeRepository.findAll();
        model.addAttribute("incomes", incomes);
        return "salary";
    }

    @PostMapping("/income")
    public String saveIncome(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @RequestParam("source") String source, @RequestParam("type") String type,
                             @RequestParam("amount") double amount) {
        incomeRepository.save(new Income(date, source, type, amount));
        return "redirect:income";
    }

    private Double countTotal(Collection<double[]> spendingsArrays) {
        return spendingsArrays.stream()
            .mapToDouble(a -> {
                double dailyTotal = 0d;
                for (double amount : a) {
                    dailyTotal += amount;
                }
                return dailyTotal;
            })
            .sum();
    }

}
