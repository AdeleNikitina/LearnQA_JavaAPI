import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeadersTest {

    @Test
    void TestHeaderEx12() {
        String expectedHeader = "Some secret value";
        Response response = RestAssured
                .given()
                .log().all()
                .when()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        assertEquals(expectedHeader, response.getHeader("x-secret-homework-header"), "The header is not equal '" + expectedHeader + "'");
    }
}
