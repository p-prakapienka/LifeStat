package by.restrictor.lifestat.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Debt {

    @Id
    @SequenceGenerator(name="spending_generator", sequenceName="spending_sequence", initialValue = 100)
    @GeneratedValue(generator = "spending_generator")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "creditor", nullable = false)
    private String creditor;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "description")
    private String description;

    @Column(name = "init_amount", nullable = false)
    private int initAmount;

    @Column(name = "amount", nullable = false)
    private int amount;

    public Debt() {
    }

    public Debt(LocalDate date, String creditor, String currency, String description, int initAmount) {
        this.date = date;
        this.creditor = creditor;
        this.currency = currency;
        this.description = description;
        this.initAmount = initAmount;
        this.amount = initAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(int initAmount) {
        this.initAmount = initAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
