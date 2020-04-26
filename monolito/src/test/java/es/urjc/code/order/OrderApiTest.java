package es.urjc.code.order;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.path.json.JsonPath.from;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApiTest {

    @LocalServerPort
    int port;
	
	@BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
	public void getOrders_shouldReturnListOfORders() {
		
		//When
		when()
			.get("/api/orders/").
		
		//Then
		then()
			.statusCode(200);
    }

    @Test
    public void addNewOrder_shouldReturn201() {

        Response response = given().
            contentType("application/json").
            body("{\"productId\":1, \"customerId\":1, \"quanty\":1}").
        when().
            post("/api/orders/")
        .andReturn();

        Assert.assertEquals(201, response.getStatusCode());

        int idOrder = from(response.getBody().asString()).get("orderId");

        //When
		when().
			get("/api/orders/{idOrder}", idOrder).
		then().
            statusCode(200);
    }
}