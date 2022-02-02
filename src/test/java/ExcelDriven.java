import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ExcelDriven {

	@Test(dataProvider = "books")
	public void addBook(Map<String, Object> book) throws IOException {
		RestAssured.baseURI = "http://216.10.245.166";

		Response res = given().header("Content-Type", "application/json").body(book).when().log().all()
				.post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(res.asString());
	}

	public void deleteBook(String id) {
		RestAssured.baseURI = "http://216.10.245.1661";

		Map<String, Object> book = new HashMap<>();
		book.put("ID", id);

		Response res = given().header("Content-Type", "application/json").body(book).when()
				.delete("/Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(res.asString());

		System.out.println(js.get("msg").toString());
	}

	@DataProvider(name = "books")
	public Object[][] dataProvider() throws IOException {
		DataDriven data = new DataDriven();
		ArrayList<Map<String, Object>> booksList = data.getData("test", "testcases");
		Object[][] books = new Object[booksList.size()][1];
		int index = 0;
		for (Iterator iterator = booksList.iterator(); iterator.hasNext();) {
			Map<String, Object> book = (Map<String, Object>) iterator.next();
			books[index++] = new Object[] {book};
		}
		return books;
	}
}