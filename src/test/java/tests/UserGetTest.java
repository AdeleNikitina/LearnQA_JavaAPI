package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {

    @Test
    public void testGetUserDataNotAuth() {
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        String[] unexpectedFieldNames = {"firstName", "lastName", "email"};
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotFields(responseUserData, unexpectedFieldNames);
    }

    @Test
    public void  testGetUserDataDetailsAuthAsSomeUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = RestAssured
                .given()
                .log().all()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .when()
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        String[] expectedFieldNames = {"username"};
        Assertions.assertJsonHasFields(responseUserData, expectedFieldNames);
    }
}
