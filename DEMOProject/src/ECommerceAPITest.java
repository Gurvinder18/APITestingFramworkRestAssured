import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class ECommerceAPITest {
	public static void main(String[] args) {
		
		//Create Login--------------------------------------------------------------------------------------
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("gurvi1888@gmail.com");
		loginRequest.setUserPassword("Spider18#");
		RequestSpecification reqLogin=given().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse=reqLogin.when().post("/api/ecom/auth/login")
				.then().log().all().extract().response().as(LoginResponse.class);
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		
		//Add Product---------------------------------------------------------------------------------------
		RequestSpecification addProductBaseReq=new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		RequestSpecification reqAddProduct=given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
		.param("productAddedBy", userId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "11500")
		.param("productDescription", "Adidas Originals")
		.param("productFor", "women")
		.multiPart("productImage", new File("C:\\Users\\g.padam\\Desktop\\Images\\wallpaper.jpg"));
		
		String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all()
		.extract()
		.response().asString();
		JsonPath js= new JsonPath(addProductResponse);
		String productId=js.get("productId");
		
		
		//Create Order--------------------------------------------------------------------------------------
		RequestSpecification createOrderBaseReq=new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", token)
				.build();
		
		OrderDetail orderDetail=new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderId(productId);
		List<OrderDetail> orderDetailList=new ArrayList();
		
		Orders orders=new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification createOrderReq=given().log().all()
		.spec(createOrderBaseReq)
		.body(orders);
		
		String responseAddOrder=createOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all()
		.extract()
		.response()
		.asString();
		
		
		//Delete Order -------------------------------------------------------------------------------------
		RequestSpecification deleteProductBaseReq=new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", token)
				.build();
		
		RequestSpecification deleteProductReq=given().log().all()
				.spec(deleteProductBaseReq)
				.pathParam("productId", productId);
		
		String deleteProductResponse=deleteProductReq.delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all()
		.extract().response()
		.asString();
		JsonPath js1=new JsonPath(deleteProductResponse);
		
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
		
		
	}
}
