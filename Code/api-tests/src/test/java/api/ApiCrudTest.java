package api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiCrudTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:5000";
    }

    @Test @Order(1)
    void loginSuccessAndFailure() {
        given().json("{\"username\":\"user\",\"password\":\"pass\"}")
          .post("/login")
          .then().statusCode(200).body("token", equalTo("fake-token"));

        given().json("{\"username\":\"user\",\"password\":\"wrong\"}")
          .post("/login").then().statusCode(401);
    }

    static int createdId;

    @Test @Order(2)
    void crudItemsPositive() {
        get("/items").then().statusCode(200).body("", hasSize(0));

        createdId = given().json("{\"text\":\"foo\"}")
          .post("/items").then().statusCode(201)
          .body("text", equalTo("foo"))
          .extract().path("id");

        given().json("{\"text\":\"bar\"}")
          .put("/items/{id}", createdId)
          .then().statusCode(200).body("text", equalTo("bar"));

        delete("/items/{id}", createdId).then().statusCode(204);
        get("/items").then().statusCode(200).body("", hasSize(0));
    }

    @Test @Order(3)
    void invalidPutDeleteReturn404() {
        put("/items/999").then().statusCode(404);
        delete("/items/999").then().statusCode(404);
    }
}
