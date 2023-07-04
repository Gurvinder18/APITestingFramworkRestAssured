import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class oAuthTest {
	public static void main(String[] args) throws Exception {
		//This test is for OAuth2.0-------------------------------------------------------------------------
		
		/*System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("srinath19830");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("password");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		String url=driver.getCurrentUrl();*/ //Removed by Google
		
		//Recieving code from the Client url for sending it to recieve the access token--------------------
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
		String partialCode=url.split("code=")[1];
		String code=partialCode.split("&scope")[0];
		
		
		//Recieving the Access token from client and sending it the the appropriate place to get results-----
		String accessTokenResponse=given()
		.urlEncodingEnabled(false)
		.queryParams("code", code)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when()
		.log().all()
		.post("https://www.googleapis.com/oauth2/v4/token")
		.asString();
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken=js.getString("access_token");
		
		
		//Finally sending the request with access token to get our json response----------------------------
		GetCourse gc = given()
		.queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php")
		.as(GetCourse.class);
		
		
		
		gc.getCourses().getApi().get(1).getCourseTitle();//complex query for getting api course title with json parsing
		List<Api> apiCourses=gc.getCourses().getApi();//complex query for getting api course title with json parsing
		for(int i=0;i<apiCourses.size();i++) {
			apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing");
		}
		
		System.out.println(gc.getLinkedIn());
		
		//System.out.println(response);
	}
}
