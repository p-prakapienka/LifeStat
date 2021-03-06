package by.restrictor.lifestat.controller;

import static by.restrictor.lifestat.model.Categories.CATEGORIES;
import static by.restrictor.lifestat.util.DoubleUtil.sum;

import by.restrictor.lifestat.model.Balance;
import by.restrictor.lifestat.model.DailyStatement;
import by.restrictor.lifestat.model.Spending;
import by.restrictor.lifestat.service.BalanceService;
import by.restrictor.lifestat.service.SpendingService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RootController {

    @Autowired
    private SpendingService spendingService;

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/")
    public String root(Model model) {
        List<Spending> spendings = spendingService.getRecentSpendings();
        model.addAttribute("spendings", spendings);

        model.addAttribute("categories", CATEGORIES);

        List<DailyStatement> dailyStatements = spendingService.getDailyStatements();
        model.addAttribute("dailySpendings", dailyStatements);

        List<Double> categoryTotals = getCategoryTotals(dailyStatements);
        model.addAttribute("catTotals", categoryTotals);

        //TODO 25.09.2018 use this to count difference with balance
//        double total = countTotal(dailyStatements);
//        double incomes = incomeRepository.findAll().stream().mapToDouble(Income::getAmount).sum();
//        model.addAttribute("balance", DoubleUtil.subtract(incomes, total));

        model.addAttribute("balance", balanceService.load());

        return "home";
    }

    @PostMapping("/")
    public String save(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam("category") String category, @RequestParam("amount") double amount) {
        Spending spending = new Spending(date, category, amount);
        spendingService.save(spending);
        return "redirect:/";
    }

    @RequestMapping("/submit")
    public String submitDate(
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        spendingService.submitDate(date);
        return "redirect:/";
    }

    @PostMapping("/balance")
    public String saveBalance(@ModelAttribute Balance balance) {
        balanceService.save(balance);
        return "redirect:/";
    }

    private Double countTotal(Collection<DailyStatement> dailyStatements) {
        return dailyStatements.stream()
            .mapToDouble(DailyStatement::getTotal)
            .sum();
    }

    private List<Double> getCategoryTotals(List<DailyStatement> dailyStatements) {
        double[] sums = new double[CATEGORIES.size() + 1];
        dailyStatements.forEach(ds -> {
            sums[0] = sum(sums[0], ds.getFoodAmount());
            sums[1] = sum(sums[1], ds.getCigAmount());
            sums[2] = sum(sums[2], ds.getEntmtAmount());
            sums[3] = sum(sums[3], ds.getTranspAmount());
            sums[4] = sum(sums[4], ds.getHouseAmount());
            sums[5] = sum(sums[5], ds.getCommAmount());
            sums[6] = sum(sums[6], ds.getHobbyAmount());
            sums[7] = sum(sums[7], ds.getMajorAmount());
            sums[8] = sum(sums[8], ds.getDebtAmount());
            sums[9] = sum(sums[9], ds.getRentAmount());
            sums[10] = sum(sums[10], ds.getBankAmount());
            sums[11] = sum(sums[11], ds.getTotal());
        });
        return Arrays.stream(sums).boxed().collect(Collectors.toList());
    }

}
