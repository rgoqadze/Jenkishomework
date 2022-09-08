import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

public class RestAssureBasic3 {

    @Test
    public void AuthAndDelete() {
        JSONObject auth = new JSONObject();
        auth.put("username", "admin");
        auth.put("password", "password123");

        String token = given()
                .header("Content-Type", "application/json")
                .when()
                .body(auth)
                .post("https://restful-booker.herokuapp.com/auth")
                .jsonPath().getString("token");

        RequestSpecification req=given()
                .header("Content-Type", "application/json")
                .cookie("token", token);

        int bookId = given()
                .spec(req)
                .get("https://restful-booker.herokuapp.com/booking")
                .jsonPath()
                .getInt("bookingid[0]");
        System.out.println(bookId);


        JSONObject request = new JSONObject();
        String delete = String.valueOf(given()
                .header("Content-Type", "application/json")
                .cookie("token", token)
                .delete("https://restful-booker.herokuapp.com/booking{id}", bookId)
                .then()
                .extract()
                .statusCode());
        Assert.assertEquals(delete, "204");
    }

    @Test
    public void Hamcrestassertions1() {
        given()
                .when()
                .get(" http://ergast.com/api/f1/2017/circuits.json")
                .then()
                .assertThat()
                .body("MRData.CircuitTable.Circuits.circuitId" , anyOf(hasItem("marina_bay")));
    }

    @Test
    public void Hamcrestassertions2() {
        given()
                .when()
                .get(" http://ergast.com/api/f1/2017/circuits.json")
                .then()
                .assertThat()
                .body("MRData.CircuitTable.Circuits.Location[-1].long",greaterThan("1"),
                        "MRData.CircuitTable.Circuits.Location[-1].long", equalTo("10") );

    }

    @Test
    public void Hamcrestassertions3() {
        given()
                .when()
                .get(" http://ergast.com/api/f1/2017/circuits.json")
                .then()
                .assertThat()
                .body("MRData.CircuitTable.Circuits.country[1]", equalTo("USA"),
                        "MRData.CircuitTable.Circuits.country[-1]", "UAE" );

    }

}
