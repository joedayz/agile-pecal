package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FruitResourceTest {

    @Test
    void listReturnsFruits() {
        given()
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .body("size()", is(3))
                .body("[0].name", is("Manzana"));
    }

    @Test
    void getByIdOk() {
        given()
                .when()
                .get("/fruits/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Manzana"));
    }

    @Test
    void getByIdNotFound() {
        given().when().get("/fruits/99").then().statusCode(404);
    }
}
