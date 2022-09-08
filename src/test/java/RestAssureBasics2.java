import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

public class RestAssureBasics2 {
    JSONObject params = new JSONObject();


    @Test
    public void BookingUpdate() {

        params.put("name", "rezo");
        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .body(params);
        Response response = given().spec(request)
                .put("https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-UpdateBooking");

        int responceCode = response
                .then()
                .extract()
                .statusCode();
        if (responceCode == 201){
            response.then().log();

        }
    }


    @Test
    public void CallMethod() {
        baseURI = "https://chercher.tech/sample/api/product/read";
        RequestSpecification request = RestAssured.given();

        Response response = request.queryParam("id", "6947")
                .get("/name")
                .then().log().body().extract().response();
        Assert.assertEquals("", response.jsonPath().toString());
    }

}
