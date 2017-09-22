package by.restrictor.lifestat.controller;

import static by.restrictor.lifestat.model.IncomeTypes.TYPES;

import by.restrictor.lifestat.model.Income;
import by.restrictor.lifestat.model.IncomeTypes;
import by.restrictor.lifestat.repository.IncomeRepository;
import by.restrictor.lifestat.util.DoubleUtil;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/income")
public class IncomesController {

    @Autowired
    private IncomeRepository incomeRepository;

    @GetMapping
    public String getIncome(Model model) {
        List<Income> incomes = incomeRepository.findAll(new Sort(Direction.DESC,"date"));
        model.addAttribute("salary", incomes.stream().filter(IncomeTypes::isSalary)
            .collect(Collectors.toList()));

        model.addAttribute("types", TYPES);

        Map<Month, double[]> incomesGrouped = new TreeMap<>();
        for (Income income : incomes) {
            Month m = income.getDate().getMonth();
            incomesGrouped.putIfAbsent(m, new double[TYPES.size()]);
            incomesGrouped.computeIfPresent(m, (month, values) -> {
                values[TYPES.indexOf(income.getType())] = DoubleUtil
                    .sum(values[TYPES.indexOf(income.getType())], income.getAmount());
                return values;
            });
        }
        model.addAttribute("incomes", incomesGrouped);

        return "incomes";
    }

    @PostMapping
    public String saveIncome(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam("source") String source, @RequestParam("type") String type,
        @RequestParam("amount") double amount) {
        incomeRepository.save(new Income(date, source, type, amount));
        return "redirect:income";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@PathVariable("id") long id, @RequestBody @Valid Income income) {
        Income original = incomeRepository.findOne(id);
        original.setDate(income.getDate());
        incomeRepository.save(original);
    }

}
