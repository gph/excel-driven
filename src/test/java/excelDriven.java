import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class excelDriven {
	@Test
	public void addBook() {
		RestAssured.baseURI = "http://216.10.245.166";
		
		Map<String, Object> book = new HashMap<>();
		book.put("name", "Java 12345");
		book.put("isbn", "123k");
		book.put("aisle", "123j");
		book.put("author", "Vac");
		
		// remember> for nested obj create another hashmap

		
		Response res = given().header("Content-Type", "application/json")
				.body(book)
				.when()
				.post("/Library/Addbook.php")
				.then().assertThat().statusCode(200).extract().response();
				
		JsonPath js = new JsonPath(res.asString());
		
		System.out.println("id: " + js.get("ID"));
		
	}
}
