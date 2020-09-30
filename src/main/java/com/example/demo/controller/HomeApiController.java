package com.example.demo.controller;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.FileAndMessage;
import com.example.demo.entity.FileAttributes;
import com.example.demo.entity.MessageFile;

import com.example.demo.service.FileService;
import com.example.demo.service.MessageService;

@RestController
public class HomeApiController {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private FileAttributes tempFileObj;
	private MessageFile tempMessageObj;
	private List<MessageFile> tempMessageList;
	@Autowired
	private FileService fileSaves;
	@Autowired
	private MessageService messageSaves;
	private String fileName = null;

	@Autowired
	private FileService fileSave;

	@GetMapping("/readFile")
	public String readFile() {
		return "index.html";
	}

	/*
	 * @GetMapping("/line") public void getline(@RequestParam("getFile") String x) {
	 * List<FileAttributes> e=fileSave.getRelationalData(x); int rowCount = 1;
	 * fileName=x; CellStyle style = workbook.createCellStyle(); XSSFFont font =
	 * workbook.createFont(); font.setFontHeight(14); style.setFont(font);
	 * 
	 * for (FileAttributes fileAttributes : e) { Row row =
	 * sheet.createRow(rowCount++); int columnCount = 0;
	 * System.out.println(fileAttributes.getFilePath());
	 * 
	 * createCell(row, columnCount++, fileAttributes.getFilePath(), style);
	 * createCell(row, columnCount++, fileAttributes.getSingle(0).getMessage(),
	 * style);
	 * 
	 * 
	 * } }
	 */
	@GetMapping("/printItNow")
	public void export(HttpServletResponse response, @RequestParam("fileName") String z) throws IOException {
		Workbook work = new XSSFWorkbook();

		Sheet sheet = work.createSheet("Code File");
		Row header = sheet.createRow(0);
		CellStyle headerStyle = work.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont font = ((XSSFWorkbook) work).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);

		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Requirement ID");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("Start Line");
		headerCell.setCellStyle(headerStyle);


		headerCell = header.createCell(1);
		headerCell.setCellValue("End Line");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(2);
		headerCell.setCellValue("Description");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(3);
		headerCell.setCellValue("Query");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(4);
		headerCell.setCellValue("Message");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(5);
		headerCell.setCellValue("Short Description");
		headerCell.setCellStyle(headerStyle);
		CellStyle style = work.createCellStyle();
		style.setWrapText(true);
		int getNam = Integer.parseInt(fileSave.getFileName(z));
		List<MessageFile> msg = messageSaves.printExcelFromId(getNam);
		int r = 1;
		Row rows = null;

		for (MessageFile f : msg) {
			int i = 0;
			rows = sheet.createRow(r);
			Cell cell = rows.createCell(i);

			cell = rows.createCell(i++);
			cell.setCellValue(f.getRequirementid());
			cell.setCellStyle(style);

			cell = rows.createCell(i++);
			cell.setCellValue(f.getStart_line());
			cell.setCellStyle(style);

			cell = rows.createCell(i++);
			cell.setCellValue(f.getEnd_line());
			cell.setCellStyle(style);

			cell = rows.createCell(i++);
			cell.setCellValue((f.getDescription() == null) ? null : f.getDescription());
			cell.setCellStyle(style);

			cell = rows.createCell(i++);
			cell.setCellValue((f.getQuery() == null) ? null : f.getQuery());
			cell.setCellStyle(style);

			cell = rows.createCell(i++);
			cell.setCellValue(f.getMessage());
			cell.setCellStyle(style);
			
			cell = rows.createCell(i++);
			cell.setCellValue(f.getShort_description());
			cell.setCellStyle(style);

			r++;
		}

		response.setContentType("application/vnd.ms-excel");
		String filename=z.substring(0, z.length()-5);
		response.setHeader("Content-Disposition", "attachment; filename="+filename+".xls");
		ServletOutputStream outputStreams = response.getOutputStream();

		work.write(outputStreams);
		work.close();

		outputStreams.close();
	}

	// This Methods Get The File Name with Correspondence to ID
	@GetMapping("/checkResult")
	public String GetFileName(@RequestParam("fileName") String z) {
		String toprint = fileSave.getFileName(z);
		return toprint;
	}
	
	//It gets the lines according to that file record saved in DB
@GetMapping("/getLinesFromDb")
public List<MessageFile> getNumberOfLines(@RequestParam("FileName") String s) {
	int fileId=(fileSave.getFileName(s)==null)?0:Integer.parseInt(fileSave.getFileName(s));
	
	List<MessageFile> lines=messageSaves.printExcelFromId(fileId);
	
return lines;
}
	// This Method Sends Two Request Body Object and stores it in DB
	@ResponseBody
	@PostMapping(value = "/sendJson")
	public String post(@RequestBody FileAndMessage files) {
		String paths = files.file.getFilePath();
		System.out.print(paths);
		System.out.print(files.msgFile.getMessage());

		int checkIfExist = (GetFileName(paths) == null) ? 0 : Integer.parseInt(GetFileName(paths));

		if (checkIfExist == 0) {

			tempFileObj = new FileAttributes();
			tempMessageObj = new MessageFile();
			tempMessageList = new ArrayList<MessageFile>();

			tempFileObj.setFilePath(files.file.getFilePath());
			tempMessageObj.setQuery(files.msgFile.getQuery());
			tempMessageObj.setMessage(files.msgFile.getMessage());
			tempMessageObj.setDescription(files.msgFile.getDescription());
			tempMessageObj.setStart_line(files.msgFile.getStart_line());
			tempMessageObj.setEnd_line(files.msgFile.getEnd_line());
            tempMessageObj.setShort_description(files.msgFile.getShort_description());
			tempMessageList.add(tempMessageObj);

			tempFileObj.setMessageObj(tempMessageList);

			fileSaves.saveFile(tempFileObj);
			return "First Saved";
		} else {
			messageSaves.saveExistingFile(checkIfExist, files.msgFile.getStart_line(), files.msgFile.getEnd_line(),
					files.msgFile.getDescription(), files.msgFile.getQuery(),files.msgFile.getShort_description(), files.msgFile.getMessage()
					,files.msgFile.getRequirementid());

			return "Second Saved";
		}

	}

}
