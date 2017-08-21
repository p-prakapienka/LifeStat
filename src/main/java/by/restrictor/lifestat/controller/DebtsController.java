package by.restrictor.lifestat.controller;

import by.restrictor.lifestat.model.Debt;
import by.restrictor.lifestat.repository.DebtRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/debts")
public class DebtsController {

    @Value("${debts.interest}")
    private int interest;

    @Autowired
    private DebtRepository debtRepository;

    @GetMapping
    public String getDebts(Model model) {
        List<Debt> debts = debtRepository.findAll();

        Map<String, List<Debt>> debtsByCreditor = debts.stream()
            .sorted(Comparator.comparing(Debt::getDate))
            .collect(Collectors.groupingBy(Debt::getCreditor, Collectors.mapping(d -> d, Collectors.toList())));

        model.addAttribute("debts", debtsByCreditor);

        return "debts";
    }

    @PostMapping
    public String add(
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam("creditor") String creditor, @RequestParam("currency") String currency,
        @RequestParam("description") String description, @RequestParam("amount") int amount) {

        Debt debt = new Debt(date, creditor, currency, description, amount);
        debtRepository.save(debt);

        return "redirect:/debts";
    }

    @PostMapping("/{id}/subtract")
    @Transactional
    public String subtract(@PathVariable("id") long id, @RequestParam("amount") int amount) {

        Debt debt = debtRepository.findOne(id);

        debt.setAmount(debt.getAmount() - amount);
        debtRepository.save(debt);

        return "redirect:/debts";
    }

}
