package com.toolrental;

import com.toolrental.service.CheckoutService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        CheckoutService checkoutService = new CheckoutService();
        try {
            checkoutService.checkout("LADW", LocalDate.now(), 5, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}