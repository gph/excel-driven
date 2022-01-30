import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {
	public static void main(String[] args) throws IOException {
		
		FileInputStream file = new FileInputStream("restapicourse.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		int sheets = workbook.getNumberOfSheets();
		System.out.println("Number of sheets: " + sheets);
		
		for (int i = 0; i < sheets; i++) {
			if(workbook.getSheetName(i).equalsIgnoreCase("test"))
			{
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				while(rows.hasNext())
				{
					Row row = rows.next();
					Iterator<Cell> cell = row.cellIterator();
					while(cell.hasNext())
					{
						Cell value = cell.next();
						if(value.toString().equalsIgnoreCase("Testcases")) // search some text in a cell
						{}
						// print like a sheet on console
						System.out.print(value.toString());
						for (int j = 0; j < (10 - value.toString().length()); j++) {
							System.out.print(" ");
						}
					}
					System.out.println();
				}
			}
		}
	}
}
