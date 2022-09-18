package ru.netology.domain;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class IbankTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void activeUserTest(){
        UserData user = UserGenerator.generateUser("active");
        UserRegistration.registration(user);
        $("[data-test-id='login'] input").val(user.getLogin());
        $("[data-test-id='password'] input").val(user.getPassword());
        $("[data-test-id='action-login']").click();
        $x("//h2").should(Condition.text("Личный кабинет"));
    }

    @Test
    public void blockedUserTest(){
        UserData user = UserGenerator.generateUser("blocked");
        UserRegistration.registration(user);
        $("[data-test-id='login'] input").val(user.getLogin());
        $("[data-test-id='password'] input").val(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

}
