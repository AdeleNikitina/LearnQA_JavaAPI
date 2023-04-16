package tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lib.ApiCoreRequests;
import lib.Assertions;

import java.util.HashMap;
import java.util.Map;

@Epic("Deleting user")
@DisplayName("Success delete user")
public class UserDeleteTest extends BaseTestCase {

    int userId;
    final static ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("Deleting user with id 2")
    @DisplayName("Delete user with id 2")
    @TmsLinks({@TmsLink(value = "CCRC-001"), @TmsLink(value = "CCRC-002")})
    @Severity(value = SeverityLevel.NORMAL)
    @Owner("Никитина Аделия")
    void testDeleteUserWithId2() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login",
                authData);

        Response responseDeleteUser = apiCoreRequests.makeDeleteRequest(
                "https://playground.learnqa.ru/api/user/2",
                responseGetAuth.getHeader("x-csrf-token"),
                responseGetAuth.getCookie("auth_sid")
        );

        Assertions.assertResponseTextEquals(responseDeleteUser,
                "Please, do not delete test users with ID 1, 2, 3, 4 or 5."
        );
    }

    @Test
    @Description("Positive Test - Success delete user")
    @DisplayName("Success delete user")
    @TmsLinks({@TmsLink(value = "CCRC-001"), @TmsLink(value = "CCRC-002")})
    @Severity(value = SeverityLevel.CRITICAL)
    @Owner("Никитина Аделия")
    void testSuccessDeleteUser() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user",
                userData
        );

        this.userId = this.getIntFromJson(responseCreateAuth, "id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login",
                authData);

        //DELETE
        Response responseDeleteUser = apiCoreRequests.makeDeleteRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                responseGetAuth.getHeader("x-csrf-token"),
                responseGetAuth.getCookie("auth_sid")
        );

        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);
        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid")
        );

        Assertions.assertResponseTextEquals(responseUserData, "User not found");

    }

    @Test
    @Description("Negative Test - Delete user by other user")
    @TmsLinks({@TmsLink(value = "CCRC-001"), @TmsLink(value = "CCRC-002")})
    @Severity(value = SeverityLevel.CRITICAL)
    @Owner("Никитина Аделия")
    @DisplayName("Delete user by other user")
    void testDeleteUserByOtherUser() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user",
                userData
        );

        this.userId = this.getIntFromJson(responseCreateAuth, "id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/login",
                authData);

        //DELETE
        Response responseDeleteUser = apiCoreRequests.makeDeleteRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                responseGetAuth.getHeader("x-csrf-token"),
                responseGetAuth.getCookie("auth_sid")
        );

        Assertions.assertResponseCodeEquals(responseDeleteUser, 400);
    }

}
