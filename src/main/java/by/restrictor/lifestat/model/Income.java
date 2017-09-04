package by.restrictor.lifestat.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Income {

    @Id
    @SequenceGenerator(name="spending_generator", sequenceName="spending_sequence", initialValue = 100)
    @GeneratedValue(generator = "spending_generator")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "amount", nullable = false)
    private double amount;

    public Income() {
    }

    public Income(LocalDate date, String source, String type, double amount) {
        this.date = date;
        this.source = source;
        this.type = type;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
