import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDriven {
	public static void main(String[] args) throws IOException {
		/*
		 * Local Test :P DataDriven d = new DataDriven(); d.getData("test",
		 * "testcases");
		 * 
		 * ArrayList<Map<String, Object>> data = d.getData("test", "testcases");
		 * data.forEach(obj -> { System.out.println(obj.get("name")); });
		 */
	}

	@DataProvider(name = "data-books")
	@Test(dataProvider = "data-sheet")
	public ArrayList<Map<String, Object>> getData(String sheetName, String columnName) throws IOException {
		FileInputStream file = new FileInputStream("restapicourse.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		ArrayList<Map<String, Object>> array = new ArrayList<Map<String, Object>>();

		int sheets = workbook.getNumberOfSheets();
		System.out.println("Number of sheets: " + sheets);

		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				while (rows.hasNext()) {
					Row row = rows.next();
					Iterator<Cell> cell = row.cellIterator();
					int k = 0;
					int column = 0;

					while (cell.hasNext()) {
						Cell value = cell.next();

						if (value.getStringCellValue().equalsIgnoreCase(columnName)) // search some text in a cell
						{
							column = k;
						}
						k++;
					}

					while (rows.hasNext()) {
						Row r = rows.next();
						Map<String, Object> obj = new HashMap<>();
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {
							Cell c = cv.next();
							if (c.getCellType().equals(CellType.STRING)) {
								switch (c.getColumnIndex()) {
								case 1:
									obj.put("name", c.getStringCellValue());
									break;
								case 2:
									obj.put("isbn", c.getStringCellValue());
									break;
								case 3:
									obj.put("aisle", c.getStringCellValue());
									break;
								case 4:
									obj.put("author", c.getStringCellValue());
									break;
								}
							} else if (c.getCellType().equals(CellType.NUMERIC)) {
								switch (c.getColumnIndex()) {
								case 1:
									obj.put("name", c.getNumericCellValue());
									break;
								case 2:
									obj.put("isbn", c.getNumericCellValue());
									break;
								case 3:
									obj.put("aisle", c.getNumericCellValue());
									break;
								case 4:
									obj.put("author", c.getNumericCellValue());
									break;
								}
							}
						}
						array.add(obj);
					}
				}
			}
		}
		return array;
	}
}