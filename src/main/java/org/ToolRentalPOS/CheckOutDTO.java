package org.ToolRentalPOS;

import org.ToolRentalPOS.db.Customer;
import org.ToolRentalPOS.db.Price;
import org.ToolRentalPOS.db.Tool;


import java.sql.Date;

public class CheckOutDTO {

    private int rentalDayCount;
    private int discountPercent;
    private Date checkOutDate;
    private Customer customer;
    private Tool tool;
    private Price price;

    public int getRentalDayCount() {
        return rentalDayCount;
    }

    public void setRentalDayCount(int rentalDayCount) {
        this.rentalDayCount = rentalDayCount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
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

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CheckOutDTO{" +
                "rentalDayCount=" + rentalDayCount +
                ", discountPercent=" + discountPercent +
                ", checkOutDate=" + checkOutDate +
                ", customer=" + customer +
                ", tool=" + tool +
                ", price=" + price +
                '}';
    }
}
