import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class DynamicJson {
	@Test(dataProvider="BooksData")
	public void addBook(String aisle, String isbn) {
		RestAssured.baseURI="http://216.10.245.166";
		//AddBook------------------------------------------------------------------------------------------
		String response = given().header("Contetn-Type","application/json").body(payload.Addbook(aisle,isbn))
		.when().post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
		
	}
	//For providing datasets--------------------------------------------------------------------------------
	@DataProvider(name="BooksData")
	public Object getData() {
		return new Object[][]{{"gurvi","9876"},{"guris","9454"},{"varshi","5436"}};
		
	}
	
}
