package by.restrictor.lifestat.model;

import static by.restrictor.lifestat.util.DoubleUtil.sum;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class DailyStatement {

    @Id
    @SequenceGenerator(name="daily_stmt_generator", sequenceName="daily_stmt_generator", initialValue = 100)
    @GeneratedValue(generator = "daily_stmt_generator")
    private Long id;

    @Column(name = "date", nullable = false)
    @OrderBy
    private LocalDate date;

    @Column(name = "total")
    private double total;

    @Column(name = "food_amount")
    private double foodAmount;

    @Column(name = "cig_amount")
    private double cigAmount;

    @Column(name = "entmt_amount")
    private double entmtAmount;

    @Column(name = "transp_amount")
    private double transpAmount;

    @Column(name = "house_amount")
    private double houseAmount;

    @Column(name = "comm_amount")
    private double commAmount;

    @Column(name = "hobby_amount")
    private double hobbyAmount;

    @Column(name = "major_amount")
    private double majorAmount;

    @Column(name = "debt_amount")
    private double debtAmount;

    @Column(name = "rent_amount")
    private double rentAmount;

    @Column(name = "bank_amount")
    private double bankAmount;

    @Transient
    private boolean generated;

    public DailyStatement() {
    }

    public DailyStatement(LocalDate date) {
        this.date = date;
    }

    public static DailyStatement fromSpendings(LocalDate date, List<Spending> spendings) {
        DailyStatement dailyStatement = new DailyStatement(date);
        spendings.forEach(s -> {
            switch (Categories.CATEGORIES.indexOf(s.getCategory())) {
                case 0:
                    dailyStatement.setFoodAmount(sum(dailyStatement.getFoodAmount(), s.getAmount()));
                    break;
                case 1:
                    dailyStatement.setCigAmount(sum(dailyStatement.getCigAmount(), s.getAmount()));
                    break;
                case 2:
                    dailyStatement.setEntmtAmount(sum(dailyStatement.getEntmtAmount(), s.getAmount()));
                    break;
                case 3:
                    dailyStatement.setTranspAmount(sum(dailyStatement.getTranspAmount(), s.getAmount()));
                    break;
                case 4:
                    dailyStatement.setHouseAmount(sum(dailyStatement.getHouseAmount(), s.getAmount()));
                    break;
                case 5:
                    dailyStatement.setCommAmount(sum(dailyStatement.getCommAmount(), s.getAmount()));
                    break;
                case 6:
                    dailyStatement.setHobbyAmount(sum(dailyStatement.getHobbyAmount(), s.getAmount()));
                    break;
                case 7:
                    dailyStatement.setMajorAmount(sum(dailyStatement.getMajorAmount(), s.getAmount()));
                    break;
                case 8:
                    dailyStatement.setDebtAmount(sum(dailyStatement.getDebtAmount(), s.getAmount()));
                    break;
                case 9:
                    dailyStatement.setRentAmount(sum(dailyStatement.getRentAmount(), s.getAmount()));
                    break;
                case 10:
                    dailyStatement.setBankAmount(sum(dailyStatement.getBankAmount(), s.getAmount()));
                    break;
            }
            dailyStatement.setTotal(sum(dailyStatement.getTotal(), s.getAmount()));
        });
        return dailyStatement;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(double foodAmount) {
        this.foodAmount = foodAmount;
    }

    public double getCigAmount() {
        return cigAmount;
    }

    public void setCigAmount(double cigAmount) {
        this.cigAmount = cigAmount;
    }

    public double getEntmtAmount() {
        return entmtAmount;
    }

    public void setEntmtAmount(double entmtAmount) {
        this.entmtAmount = entmtAmount;
    }

    public double getTranspAmount() {
        return transpAmount;
    }

    public void setTranspAmount(double transpAmount) {
        this.transpAmount = transpAmount;
    }

    public double getHouseAmount() {
        return houseAmount;
    }

    public void setHouseAmount(double houseAmount) {
        this.houseAmount = houseAmount;
    }

    public double getCommAmount() {
        return commAmount;
    }

    public void setCommAmount(double commAmount) {
        this.commAmount = commAmount;
    }

    public double getHobbyAmount() {
        return hobbyAmount;
    }

    public void setHobbyAmount(double hobbyAmount) {
        this.hobbyAmount = hobbyAmount;
    }

    public double getMajorAmount() {
        return majorAmount;
    }

    public void setMajorAmount(double majorAmount) {
        this.majorAmount = majorAmount;
    }

    public double getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public double getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(double bankAmount) {
        this.bankAmount = bankAmount;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    @Override
    public String toString() {
        return "DailyStatement{" +
            "date=" + date +
            ", food=" + foodAmount +
            ", cigarettes=" + cigAmount +
            ", entertainment=" + entmtAmount +
            ", transport=" + transpAmount +
            ", household=" + houseAmount +
            ", communication=" + commAmount +
            ", hobby=" + hobbyAmount +
            ", major=" + majorAmount +
            ", debt=" + debtAmount +
            ", rent=" + rentAmount +
            ", bank charges=" + bankAmount +
            '}';
    }
}
