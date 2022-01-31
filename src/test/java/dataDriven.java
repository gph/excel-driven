import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

public class dataDriven {
	public static void main(String[] args) throws IOException {
		dataDriven d = new dataDriven();
		d.getData("test", "purchase").forEach((cell) -> {
			System.out.println(cell);
		});

		ArrayList data = d.getData("test", "purchase");
		System.out.println(data.get(0));
		
	}
	
	public ArrayList<String> getData(String sheetName, String columnName) throws IOException {
		FileInputStream file = new FileInputStream("restapicourse.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		ArrayList<String> array = new ArrayList<String>();
		
		int sheets = workbook.getNumberOfSheets();
		System.out.println("Number of sheets: " + sheets);
		
		for (int i = 0; i < sheets; i++) {
			if(workbook.getSheetName(i).equalsIgnoreCase(sheetName))
			{
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				while(rows.hasNext())
				{
					Row row = rows.next();
					Iterator<Cell> cell = row.cellIterator();
					int k = 0;
					int column = 0;
					
					while(cell.hasNext())
					{
						Cell value = cell.next();
						
						if(value.getStringCellValue().equalsIgnoreCase("testcases")) // search some text in a cell
						{
							column = k;
						}
						k++;
					}
					System.out.println();
					
					while(rows.hasNext()) 
					{
						Row r = rows.next();
						if(r.getCell(column).getStringCellValue().toString().equalsIgnoreCase(columnName))
						{
							Iterator<Cell> cv = r.cellIterator();
							while(cv.hasNext())
							{
									Cell c = cv.next();
									if(c.getCellType().equals(CellType.STRING))
									{
										array.add(c.getStringCellValue());
									}
									else if(c.getCellType().equals(CellType.NUMERIC))
									{
										array.add(c.getNumericCellValue()+"");
									}
							}
						}
					}
				}
			}
		}
		return array;
	}
}
