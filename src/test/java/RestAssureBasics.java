import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class RestAssureBasics {

    @Test
    public void GetCircuits(){
        String circuit1 = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                extract().
                path("MRData.CircuitTable.Circuits.circuitId[1]");

        String circuit2 = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                extract().
                path("MRData.CircuitTable.Circuits.circuitId[5]");

        String CircuitParam1 = "circuitId[1]";
        String CircuitParam2 = "circuitId[5]";
                given().
                pathParam("countryName", CircuitParam1).
                when().
                get(" http://ergast.com/api/f1/circuits/{countryName}.json").
                then().
                assertThat().
                equals(circuit1);


        String country1 = "americas";
        String country2 = "hungaroring";
        given().
                pathParam("circuitId", CircuitParam1).
                when().
                get("http://ergast.com/api/f1/circuits/{circuitId}.json").
                then().
                assertThat().

                body("MRData.CircuitTable.Circuits.Location[0].country", equalTo(circuit1));

        given().
                pathParam("circuitId", CircuitParam2).
                when().
                get("http://ergast.com/api/f1/circuits/{circuitId}.json").
                then().
                assertThat().

        body("MRData.CircuitTable.Circuits.Location[0].country", equalTo(circuit1));

    }


    @DataProvider(name="countryParameter")
    public Object[][] createTestDataRecords() {
        return new Object[][]{
                {"americas", "USA",
                "hungaroring", "Hungary"},
        };
    }

    @Test(dataProvider = "countryParameter")
    public void circuitAndCountry(String circuitId1, String country1, String circuit2, String country2 ) {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                extract().
                path(" http://ergast.com/api/f1/2017/circuits.json[1]");

        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                extract().
                path("MRData.CircuitTable.Circuits.circuitId[5]");
    }
}

