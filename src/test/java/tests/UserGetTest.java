package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.ApiCoreRequests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Get user data cases")
@Feature("User data")
public class UserGetTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("Get user data w/o login")
    @DisplayName("Try get user data w/o login")
    @TmsLinks({@TmsLink(value = "CCRC-004"), @TmsLink(value = "CCRC-005")})
    @Severity(value = SeverityLevel.NORMAL)
    @Owner("Никитина Аделия")
    @Test
    public void testGetUserDataNotAuth() {
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        String[] unexpectedFieldNames = {"firstName", "lastName", "email"};
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotFields(responseUserData, unexpectedFieldNames);
    }


    @DisplayName("Get own user data w/o login")
    @Description("Positive Test - Get own user data")
    @TmsLinks({@TmsLink(value = "CCRC-004"), @TmsLink(value = "CCRC-005")})
    @Severity(value = SeverityLevel.CRITICAL)
    @Owner("Никитина Аделия")
    @Test
    public void testGetUserDataDetailsAuthAsSomeUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login",
                authData
        );

        Response responseUserData = apiCoreRequests.makeGetRequest(
                "https://playground.learnqa.ru/api/user/2",
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        String[] expectedFieldNames = {"username"};
        Assertions.assertJsonHasFields(responseUserData, expectedFieldNames);
    }

    @DisplayName("Get  user data by other user")
    @Description("Get other user data")
    @TmsLinks({@TmsLink(value = "CCRC-004"), @TmsLink(value = "CCRC-005")})
    @Severity(value = SeverityLevel.CRITICAL)
    @Owner("Никитина Аделия")
    @Test
    public void testGetUserDataDetailsAuthAsOtherUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login",
                authData
        );

        Response responseUserData = apiCoreRequests.makeGetRequest(
                "https://playground.learnqa.ru/api/user/1",
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        String[] expectedFieldNames = {"username"};
        Assertions.assertJsonHasFields(responseUserData, expectedFieldNames);
    }
}
