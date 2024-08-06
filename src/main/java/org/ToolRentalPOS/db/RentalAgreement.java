package org.ToolRentalPOS.db;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RentalAgreement {
    private int rentalAgreementID;
    private Customer customer;
    private Tool tool;
    private int rentalDays; //The number of days the customer requests to rent the tool
    private Date CheckOutDate;  //The first day of the rental
    private Date dueDate;
    private int chargeDays;
    private int DailyChargeCents;
    private int PreDiscountChargeCents;
    private int discountPercent;
    //"Discount Amount" can easily be calculated from PreDiscountChargeCents and discountPercent whenever it's printed
    private int finalChargeCents;

    //This method is meant to print an easy to read report of the rental agreement
    public String toStringAsReport() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        return  "Rental Agreement:" + System.lineSeparator() +
                "  Tool Info:" + System.lineSeparator() +
                "    Tool Code: " +tool.getToolCode() + System.lineSeparator() +
                "    Tool Type: " +tool.getToolType() + System.lineSeparator() +
                "    Tool Brand: " +tool.getBrand() + System.lineSeparator() +
                "  Customer Info:" + System.lineSeparator() +
                "    First Name: " +customer.getFirstName() + System.lineSeparator() +
                "    Last Name: " +customer.getLastName() + System.lineSeparator() +
                "    CustomerID: " +customer.getCustomerID() + System.lineSeparator() +
                "  Rental Details:" + System.lineSeparator() +
                "    Checkout Date " + dateFormat.format(CheckOutDate) + System.lineSeparator() +
                "    Due Date: " + dateFormat.format(dueDate) + System.lineSeparator() +
                "    Daily Rental Charge: " + currencyFormat.format(DailyChargeCents/100.0) + System.lineSeparator() +
                "    Charge Days: " + chargeDays + System.lineSeparator() +
                "    Pre-Discount Charge: " + currencyFormat.format(PreDiscountChargeCents/100.0) + System.lineSeparator() +
                "    Discount: %" + discountPercent + System.lineSeparator() +
                "    Discount Amount: " + currencyFormat.format((PreDiscountChargeCents*(discountPercent/100.0)/100.0)) + System.lineSeparator() +
                "    Final Charge: " + currencyFormat.format(finalChargeCents/100.0);
    }



    public int getRentalAgreementID() {
        return rentalAgreementID;
    }

    public void setRentalAgreementID(int rentalAgreementID) {
        this.rentalAgreementID = rentalAgreementID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Date getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public int getDailyChargeCents() {
        return DailyChargeCents;
    }

    public void setDailyChargeCents(int dailyChargeCents) {
        DailyChargeCents = dailyChargeCents;
    }

    public int getPreDiscountChargeCents() {
        return PreDiscountChargeCents;
    }

    public void setPreDiscountChargeCents(int preDiscountChargeCents) {
        PreDiscountChargeCents = preDiscountChargeCents;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getFinalChargeCents() {
        return finalChargeCents;
    }

    public void setFinalChargeCents(int finalChargeCents) {
        this.finalChargeCents = finalChargeCents;
    }

    @Override
    public String toString() {
        return "RentalAgreement{" +
                "rentalAgreementID=" + rentalAgreementID +
                ", customer=" + customer +
                ", tool=" + tool +
                ", rentalDays=" + rentalDays +
                ", CheckOutDate=" + CheckOutDate +
                ", dueDate=" + dueDate +
                ", chargeDays=" + chargeDays +
                ", DailyChargeCents=" + DailyChargeCents +
                ", PreDiscountChargeCents=" + PreDiscountChargeCents +
                ", discountPercent=" + discountPercent +
                ", finalChargeCents=" + finalChargeCents +
                '}';
    }


}
