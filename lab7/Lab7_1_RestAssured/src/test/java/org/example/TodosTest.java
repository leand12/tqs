package org.example;


import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TodosTest {

    @Test
    public void testIsAvailable(){
        given()
        .when().get("https://jsonplaceholder.typicode.com/todos")
        .then().statusCode(200);
    }
    @Test
    public void testTODO4Title(){
        given()
        .when().get("https://jsonplaceholder.typicode.com/todos/4")
        .then().statusCode(200)
        .and().body("title", equalTo("et porro tempora"));
    }

    @Test
    public void testAllTODOSHasItems(){
        given()
        .when().get("https://jsonplaceholder.typicode.com/todos")
        .then().statusCode(200)
        .and().body("id", hasItems(198, 199));
    }
}
