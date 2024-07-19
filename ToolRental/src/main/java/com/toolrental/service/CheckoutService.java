package com.toolrental.service;

import com.toolrental.entity.RentalAgreement;
import com.toolrental.entity.Tool;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class CheckoutService {
    public RentalAgreement checkout(String toolCode, LocalDate checkoutDate, int rentalDays, int discountPercent) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental days must be greater than 0");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        Tool tool = new Tool(toolCode);
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setTool(tool);
        rentalAgreement.setRentalDays(rentalDays);
        rentalAgreement.setDiscountPercent(discountPercent);
        rentalAgreement.setCheckoutDate(checkoutDate);
        rentalAgreement.setDueDate(checkoutDate.plusDays(rentalDays));
        rentalAgreement.setDailyRentalCharge(tool.getDailyCharge());
        rentalAgreement.setChargeDays(calculateChargeDays(tool, checkoutDate, rentalDays));
        rentalAgreement.setPreDiscountCharge(calculateCharge(tool, rentalAgreement.getChargeDays()));
        rentalAgreement.setDiscountAmount(round(calculateDiscountAmount(rentalAgreement.getPreDiscountCharge(), discountPercent)));
        rentalAgreement.setFinalCharge(rentalAgreement.getPreDiscountCharge() - rentalAgreement.getDiscountAmount());
        rentalAgreement.generateAgreement();
        return rentalAgreement;
    }

    private int calculateChargeDays(Tool tool, LocalDate checkoutDate, int rentalDays) {
        int chargeDays = 0;
        for (int i = 1; i <= rentalDays; i++) {
            LocalDate currentDate = checkoutDate.plusDays(i);
            if (isWeekday(currentDate) && !isHoliday(currentDate) && tool.isWeekdayCharge()) {
                chargeDays++;
            } else if (!isWeekday(currentDate) && tool.isWeekendCharge()) {
                chargeDays++;
            } else if (isHoliday(currentDate) && tool.isHolidayCharge()) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    private boolean isWeekday(LocalDate date) {
        return date.getDayOfWeek().getValue() < 6;
    }

    private boolean isHoliday(LocalDate currentDate) {
        if (isFourthOfJuly(currentDate) || isLaborDay(currentDate)) {
            return true;
        }
        return false;
    }

    private boolean isFourthOfJuly(LocalDate currentDate) {
        return currentDate.getMonth() == Month.JULY && currentDate.getDayOfMonth() == 4;
    }

    private boolean isLaborDay(LocalDate currentDate) {
        return currentDate.getMonth() == Month.SEPTEMBER && currentDate.getDayOfWeek() == DayOfWeek.MONDAY && currentDate.getDayOfMonth() < 8;
    }

    private double calculateCharge(Tool tool, int chargeDays) {
        return tool.getDailyCharge() * chargeDays;
    }

    private double calculateDiscountAmount(double preDiscountCharge, int discountPercent) {
        return preDiscountCharge * discountPercent / 100;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
