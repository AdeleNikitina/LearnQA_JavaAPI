import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class RedirectTest {

    //Ex6: Редирект
    @Test
    public void TestRedirectEx6(){
        Response firstResponse = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String firstLocationHeaders = firstResponse.getHeader("location");
        System.out.println(firstLocationHeaders);

        Response secondResponse = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get(firstLocationHeaders)
                .andReturn();

        String secondLocationHeaders = secondResponse.getHeader("location");
        System.out.println(secondLocationHeaders);
    }
}
