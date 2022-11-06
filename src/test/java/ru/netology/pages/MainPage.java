package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    final private static SelenideElement paymentButton = $(byText("Купить"));
    final private static SelenideElement creditButton = $(byText("Купить в кредит"));

    public static PaymentPage checkPaymentButton() {
        paymentButton.click();
        PaymentPage.verifyBuy();
        return new PaymentPage();
    }

    public static PaymentPage checkCreditButton() {
        creditButton.click();
        PaymentPage.verifyCredit();
        return new PaymentPage();
    }
}