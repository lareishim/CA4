
package DTOs;

import java.util.Date;

public class Income {
    private int incomeID;
    private String title;
    private double amount;
    private Date dateEarned;

    // Constructor
    public Income(int incomeID, String title, double amount, Date dateEarned) {
        this.incomeID = incomeID;
        this.title = title;
        this.amount = amount;
        this.dateEarned = dateEarned;
    }

    // Getters and Setters
    public int getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(int incomeID) {
        this.incomeID = incomeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateEarned() {
        return dateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        this.dateEarned = dateEarned;
    }

    @Override
    public String toString() {
        return "Income ID: " + incomeID +
                ", Title: " + title +
                ", Amount: $" + amount +
                ", Date Earned: " + dateEarned;
    }

}
