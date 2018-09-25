package by.restrictor.lifestat.model;

import by.restrictor.lifestat.util.DoubleUtil;

import javax.persistence.*;

@Entity
public class Balance {

    public static final Balance EMPTY = new Balance();

    @Id
    @SequenceGenerator(name="daily_stmt_generator", sequenceName="daily_stmt_generator", initialValue = 100)
    @GeneratedValue(generator = "daily_stmt_generator")
    private Long id;

    @Column(name = "card")
    private double cardBalance;

    @Column(name = "cash")
    private double cash;

    public Balance() {
    }

    public Balance(double cardBalance, double cash) {
        this.cardBalance = cardBalance;
        this.cash = cash;
    }

    public double getTotal() {
        return DoubleUtil.sum(cardBalance, cash);
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

}
