import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
public class serializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		AddPlace p= new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName("Frontline house");
		List<String> myList=new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		p.setLocation(location);
		String res=given()
		.queryParam("key", "qaclick123")
		.body(p)
		.when()
		.log().all()
		.post("/maps/api/place/add/json")
		.then()
		.log().all()
		.assertThat()
		.statusCode(200)
		.extract()
		.response()
		.asString();
		
	}

}
