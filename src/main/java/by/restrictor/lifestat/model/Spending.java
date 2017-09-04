package by.restrictor.lifestat.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

@Entity
public class Spending {

    @Id
    @SequenceGenerator(name="spending_generator", sequenceName="spending_sequence", initialValue = 100)
    @GeneratedValue(generator = "spending_generator")
    private Long id;

    @Column(name = "date", nullable = false)
    @OrderBy(value = "DESC")
    private LocalDate date;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "amount", nullable = false)
    private double amount;

    public Spending() {
    }

    public Spending(LocalDate date, String category, double amount) {
        this.date = date;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
