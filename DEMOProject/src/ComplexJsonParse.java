import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(payload.CoursePrice());//dummy json response
		int count = js.getInt("courses.size()");//get number of courses
		System.out.println(count);
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");//print total amount
		System.out.println(totalAmount);
		
		String title =js.get("courses[0].title");//print first title
		System.out.println(title);
		//print all titles and price
		for(int i =0;i<count;i++) {
			String courseTitles=js.get("courses["+i+"].title");
			js.get("courses["+i+"].price");
			System.out.println(courseTitles);
			System.out.println(js.get("courses["+i+"].price").toString());
		}
		
		//print RPA copies
		System.out.println("print RPA copies");
		for(int i =0;i<count;i++) {
			String courseTitles=js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				//copies sold
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;//stops loop when RPA is found
			}
		}
		//verify sum matches total purchase price
		System.out.println("The last assignment is:");
		int totalSum=0;		
		for(int i =0;i<count;i++) {
			int sum = js.get("courses["+i+"].price");
			int copies1 = js.get("courses["+i+"].copies");
			totalSum+=(sum*copies1);
		}
		System.out.println(totalSum);
		
	}
}
