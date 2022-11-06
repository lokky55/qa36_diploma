package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void ClearAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void shouldStart() {
        open("http://localhost:8080/");
    }

    @AfterEach
    void afterEach() {
        DataHelper.clearSUTData();
    }

    @Test
    void shouldBuyWithValidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.checkSuccessPay();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        String expected = DataHelper.getIdFromPaymentObject();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldBuyWithNotRegisteredCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setCard("1111 1111 1111 1111", month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.checkUnSuccessPay();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithInvalidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setInvalidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.checkUnSuccessPay();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    //негативные сценарии
    @Test
    void shouldBuyWithEmptyCardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123",
                emptyCardNumber = "";

        var cardInfo = DataHelper.setCard(emptyCardNumber, month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithShortCardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123",
                shortCardNumber = "4444 4444 4444 444";

        var cardInfo = DataHelper.setCard(shortCardNumber, month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWith0CardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123",
                zeroCardNumber = "0";

        var cardInfo = DataHelper.setCard(zeroCardNumber, month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardPastMonth() {
        String[] date = DataHelper.generateDate(-30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldCardDateError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardIncorrect0Month() {
        String[] date = DataHelper.generateDate(30);
        String month = "00",
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldCardDateError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardIncorrect13Month() {
        String[] date = DataHelper.generateDate(30);
        String month = "13",
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldCardDateError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardEmptyFiledMonth() {
        String[] date = DataHelper.generateDate(30);
        String month = "",
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardShortMonth() {
        String[] date = DataHelper.generateDate(30);
        String month = "2",
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardEmptyYear() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = "",
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardPastYear() {
        String[] date = DataHelper.generateDate(-365);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldCardDateExpiredError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardFromFutureOn10Years() {
        String[] date = DataHelper.generateDate(3650);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldCardDateError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardEmptyOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = "",
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldCardRequiredField();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardCyrillicOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("Ru"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardSymbolsOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = "&\"№;!\"%",
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardNumbersOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = "1234567890",
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCard0Owner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = "0",
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardEmptyCVVField() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardNumberCVVIs1() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "3";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardNumberCVVIs0() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "0";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldBuyWithCardTwoNumbersCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "12";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = MainPage.checkPaymentButton();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);
        paymentPage.verifyFieldError();
        String actual = DataHelper.getPaymentIdFromOrderObject();
        Assertions.assertNull(actual);
    }
}