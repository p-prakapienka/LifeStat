package by.restrictor.lifestat.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtil {

    private DoubleUtil() {}

    public static double sum(double d1, double d2) {
        BigDecimal result = new BigDecimal(d1).add(new BigDecimal(d2));
        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
