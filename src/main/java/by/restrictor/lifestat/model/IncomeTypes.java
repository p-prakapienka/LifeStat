package by.restrictor.lifestat.model;

import java.util.Arrays;
import java.util.List;

public class IncomeTypes {

    public static final List<String> TYPES = Arrays.asList(
        "Salary", "Prepayment", "Premium", "Side Job", "Flea Market", "Credit", "Parents", "Gift"
    );

    public static boolean isSalary(Income income) {
        return income.getType().equalsIgnoreCase(TYPES.get(0))
            || income.getType().equalsIgnoreCase(TYPES.get(1));
    }

}
