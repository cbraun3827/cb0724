package com.toolrental.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
public class RentalAgreement {
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy");

    private Tool tool;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double dailyRentalCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private int discountPercent;
    private Double discountAmount;
    private Double finalCharge;

    public RentalAgreement() {
    }

    public void generateAgreement() {
        System.out.println( "Tool code: " + tool.getToolCode() + "\n" +
                "Tool type: " + tool.getToolType() + "\n" +
                "Tool brand: " + tool.getBrand() + "\n" +
                "Rental days: " + rentalDays + "\n" +
                "Checkout date: " + checkoutDate.format(dateFormat) + "\n" +
                "Due date: " + dueDate.format(dateFormat) + "\n" +
                "Daily rental charge: $" + formatMoney(dailyRentalCharge) + "\n" +
                "Charge days: " + chargeDays + "\n" +
                "Pre-discount charge: $" + formatMoney(preDiscountCharge) + "\n" +
                "Discount percent: " + discountPercent + "%\n" +
                "Discount amount: $" + formatMoney(discountAmount) + "\n" +
                "Final charge: $" + formatMoney(finalCharge));
    }

    private String formatMoney(double money) {
        return String.format("%.2f", money);
    }
}
