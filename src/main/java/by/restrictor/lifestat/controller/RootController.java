package by.restrictor.lifestat.controller;

import by.restrictor.lifestat.model.Income;
import by.restrictor.lifestat.model.Spending;
import by.restrictor.lifestat.repository.IncomeRepository;
import by.restrictor.lifestat.repository.SpendingRepository;
import java.time.LocalDate;
import java.util.List;
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
    private SpendingRepository spendingRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @GetMapping
    public String root(Model model) {
        List<Spending> spendings = spendingRepository.findAll();
        model.addAttribute("hello", "hello");
        model.addAttribute("spendings", spendings);
        return "home";
    }

    @PostMapping
    @Transactional
    public String save(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam("category") String category, @RequestParam("amount") double amount) {
        Spending spending = new Spending(date, category, amount);
        spendingRepository.save(spending);
        return "redirect:home";
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

}
