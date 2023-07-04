import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourses(){
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");
		int totalAmount=0;
		for(int i=0;i<count;i++) {
			int prices=js.get("courses["+i+"].price");
			int copies=js.get("courses["+i+"].copies");
			int amount = prices*copies;
			totalAmount+=amount;
		}
		System.out.println(totalAmount);
		int purchaseAmount=0;
		purchaseAmount=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(totalAmount, purchaseAmount);
	}
}
