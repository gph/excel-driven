import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ExcelDriven {
	@Test
	public void addBook() throws IOException {
		RestAssured.baseURI = "http://216.10.245.166";
		
		DataDriven d = new DataDriven();
		ArrayList data = d.getData("test", "testcases", "book1");
		
		
		Map<String, Object> book = new HashMap<>();
		book.put("name", data.get(1));
		book.put("isbn", data.get(2));
		book.put("aisle", data.get(3));
		book.put("author", data.get(4));
		
		// remember> for nested obj create another hashmap

		
		Response res = given().header("Content-Type", "application/json")
				.body(book)
				.when()
				.post("/Library/Addbook.php")
				.then().assertThat().statusCode(200).extract().response();
				
		JsonPath js = new JsonPath(res.asString());
		
		System.out.println("ID: " + js.get("ID"));
		
		//deleteBook(js.get("ID"));
	}
	
	public void deleteBook(String id) {
		RestAssured.baseURI = "http://216.10.245.166";
		
		Map<String, Object> book = new HashMap<>();
		book.put("ID", id);
		
		Response res = given().header("Content-Type", "application/json")
		.body(book)
		.when()
		.delete("/Library/DeleteBook.php")
		.then().assertThat().statusCode(200).extract().response();
		
		JsonPath js = new JsonPath(res.asString());
		
		System.out.println(js.get("msg").toString());
	}
}
