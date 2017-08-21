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

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "creditor")
    private String creditor;

    @Column(name = "currency")
    private String currency;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private int amount;

    public Debt() {
    }

    public Debt(LocalDate date, String creditor, String currency, String description, int amount) {
        this.date = date;
        this.creditor = creditor;
        this.currency = currency;
        this.description = description;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
