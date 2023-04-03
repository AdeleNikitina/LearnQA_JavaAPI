import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {

    @Test
    public void TokenTestEx8() throws InterruptedException {
        JsonPath createResponse = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String token = createResponse.get("token");

        JsonPath responseNotReady = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String responseNotReadyMessage = responseNotReady.get("status");
        assertEquals(responseNotReadyMessage, "Job is NOT ready");

        Thread.sleep(18000);

        JsonPath responseReady = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String responseReadyMessage = responseReady.get("status");
        String result = responseReady.get("result");
        assertEquals(responseReadyMessage, "Job is ready");
        assertNotNull(result);

    }

}
