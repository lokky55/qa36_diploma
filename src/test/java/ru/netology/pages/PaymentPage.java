package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.byText;

public class PaymentPage {
    final private static SelenideElement headingBuy = $(byText("Оплата по карте"));
    final private static SelenideElement headingCredit = $(byText("Кредит по данным карты"));
    final private SelenideElement notificationStatusOk = $(".notification_status_ok ." +
            "notification__content");
    final private SelenideElement notificationStatusError = $(".notification_status_error ." +
            "notification__content");
    final private static SelenideElement fieldCardNumber = $x("//*[.='Номер карты'] //input");
    final private static SelenideElement fieldCardMonth = $x("//*[.='Месяц'] //input");
    final private static SelenideElement fieldCardYear = $x("//*[.='Год'] //input");
    final private static SelenideElement fieldCardOwner = $x("//*[.='Владелец'] //input");
    final private static SelenideElement fieldCardCVC = $x("//*[.='CVC/CVV'] //input");
    final private static SelenideElement buttonContinue = $(".form-field button");
    final private static SelenideElement fieldWrongFormat = $(byText("Неверный формат"));
    final private static SelenideElement fieldWrongCardDate = $(byText("Неверно указан срок действия карты"));
    final private static SelenideElement fieldCardDateExpired = $(byText("Истёк срок действия карты"));
    final private static SelenideElement fieldRequired = $(byText("Поле обязательно для заполнения"));

    public static void verifyBuy() {
        headingBuy.shouldBe(Condition.visible);
    }

    public static void verifyCredit() {
        headingCredit.shouldBe(Condition.visible);
    }

    public void checkSuccessPay() {
        notificationStatusOk.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void checkUnSuccessPay() {
        notificationStatusError.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public final void enterCardData(DataHelper.CardInfo info) {   //
        fieldCardNumber.setValue(info.getCardNumber());
        fieldCardMonth.setValue(info.getCardMonth());
        fieldCardYear.setValue(info.getCardYear());
        fieldCardOwner.setValue(info.getCardOwner());
        fieldCardCVC.setValue(info.getCardCVV());
        buttonContinue.click();
    }

    public final void cleanAllFields() {
        fieldCardNumber.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardNumber.sendKeys(Keys.DELETE);
        fieldCardMonth.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardMonth.sendKeys(Keys.DELETE);
        fieldCardYear.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardYear.sendKeys(Keys.DELETE);
        fieldCardOwner.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardOwner.sendKeys(Keys.DELETE);
        fieldCardCVC.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardCVC.sendKeys(Keys.DELETE);
    }

    public void verifyFieldError() {
        fieldWrongFormat.shouldBe(Condition.visible);
    }

    public void verifyFieldCardDateError() {
        fieldWrongCardDate.shouldBe(Condition.visible);
    }

    public void verifyFieldCardDateExpiredError() {
        fieldCardDateExpired.shouldBe(Condition.visible);
    }

    public void verifyFieldCardRequiredField() {
        fieldRequired.shouldBe(Condition.visible);
    }
}