package RatingApp.com.export;

import java.io.ByteArrayOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import RatingApp.com.entities.RatingStar;

public class ExportExcel {

	public void exportToExcel(List<RatingStar> dataList, ByteArrayOutputStream outputStream) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Data");
		
		
		
		CellStyle centerAlignment = workbook.createCellStyle();
	    centerAlignment.setAlignment(HorizontalAlignment.CENTER);
	    centerAlignment.setBorderTop(BorderStyle.THIN);
	    centerAlignment.setBorderBottom(BorderStyle.THIN);
	    centerAlignment.setBorderLeft(BorderStyle.THIN);
	    centerAlignment.setBorderRight(BorderStyle.THIN);
	    
	    CellStyle headerStyle = workbook.createCellStyle();
	    headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerStyle.setBorderTop(BorderStyle.THIN);
	    headerStyle.setBorderBottom(BorderStyle.THIN);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderRight(BorderStyle.THIN);
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Row headerRow = sheet.createRow(0);
		Cell headerCell0 = headerRow.createCell(0);
		headerCell0.setCellValue("Cơ sở");
		headerCell0.setCellStyle(centerAlignment);
		headerCell0.setCellStyle(headerStyle);
		
		Cell headerCell1 = headerRow.createCell(1);
		headerCell1.setCellValue("Đánh giá món ăn");
		headerCell1.setCellStyle(centerAlignment);
		headerCell1.setCellStyle(headerStyle);
		
		Cell headerCell2 = headerRow.createCell(2);
		headerCell2.setCellValue("Đánh giá phục vụ");
		headerCell2.setCellStyle(centerAlignment);
		headerCell2.setCellStyle(headerStyle);
		
		Cell headerCell3 = headerRow.createCell(3);
		headerCell3.setCellValue("Ngày");
		headerCell3.setCellStyle(centerAlignment);
		headerCell3.setCellStyle(headerStyle);
		
		int rowNum = 1;
		for (RatingStar data : dataList) {

			String originalDate = data.getDateRate();
//			String formattedDate = originalDate.substring(0, 5) + "/2024";
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
			String formattedDate = originalDate.formatted(formatter);
			
			Row row = sheet.createRow(rowNum++);
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(data.getDepartment());
			cell0.setCellStyle(centerAlignment);
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(data.getValueFood());
			cell1.setCellStyle(centerAlignment);
			
			
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(data.getValueService());
			cell2.setCellStyle(centerAlignment);
			
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(formattedDate);
			cell3.setCellStyle(centerAlignment);
			// Add more cells for other fields as needed
		}
		
		sheet.setColumnWidth(0, 27 * 256); // Column 0 width is set to 15 characters
	    sheet.setColumnWidth(1, 20 * 256); // Column 1 width is set to 15 characters
	    sheet.setColumnWidth(2, 20 * 256); // Column 2 width is set to 15 characters
	    sheet.setColumnWidth(3, 35 * 256); // Column 3 width is set to 15 characters

		workbook.write(outputStream);
		workbook.close();
	}
}
