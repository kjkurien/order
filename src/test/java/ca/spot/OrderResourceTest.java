package ca.spot;
    
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class OrderResourceTest {


    
    @Test
    @Order(1)
    public void testCreateOrder() {
        FWOOrder order = new FWOOrder();
        order.customerName = "John Doe";
        order.orderStatus = "created";
        order.orderDate = "2022/11/10";
        given()
            .contentType(ContentType.JSON)
            .body(order)
        .when()
            .post("/orders")
        .then()
            .statusCode(201) // HTTP status code for successful creation
            .contentType(ContentType.JSON)
            .body("customerName", equalTo("John Doe")) // Validate the response JSON
            .body("orderDate", equalTo("2022/11/10"))
            .body("orderStatus", equalTo("created"));
    }

    

    @Test
    @Order(2)
    public void testGetAllOrders() {
        given()
            .when().get("/orders")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("$.size()", is(1)); // Adjust this based on your test data
    }

    @Test
    @Order(3)
    public void testGetOrder1() {
         given()
            .when().get("/orders/1")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("customerName", equalTo("John Doe")) // Validate the response JSON
            .body("orderDate", equalTo("2022/11/10"))
            .body("orderStatus", equalTo("created"));
    }


    @Test
    @Order(4)
    public void testUpdateOrder() {
        // Prepare a sample item and JSON representation
        FWOOrder originalItem = new FWOOrder();
        originalItem.id = 1L;
        originalItem.customerName = "Hacker Update" ;
        originalItem.orderStatus = "created";
        originalItem.orderDate = "2022/11/10";
        // Perform a PUT request to update the item
        given()
            .contentType(ContentType.JSON)
            .body(originalItem)
        .when()
            .put("/orders/{id}", originalItem.id)
        .then()
            .statusCode(200) // Assuming you return 200 OK for successful updates
            .contentType(ContentType.JSON)
            .body("id", equalTo(originalItem.id.intValue())) // Validate the response
            .body("customerName", equalTo(originalItem.customerName)); // Verify the updated value
    }

    @Test
    @Order(5)
    public void testGetOrder() {
         given()
            .when().get("/orders/1")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("customerName", equalTo("Hacker Update")) // Validate the response JSON
            .body("orderDate", equalTo("2022/11/10"))
            .body("orderStatus", equalTo("created"));
    }

    @Test
    @Order(6)
    public void testGetOrderList() {
         given()
            .when().get("/orders/customerName/Hacker Update")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("$.size()", is(1)); // Adjust this based on your test data
    }

}
