package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieTest {

    @Test
    void TestCookieEx11(){
        String expectedCookie = "hw_value";
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        System.out.println(response.getCookies());
        assertEquals(expectedCookie, response.getCookie("HomeWork"), "Cookie is not equal " + expectedCookie);
    }
}
