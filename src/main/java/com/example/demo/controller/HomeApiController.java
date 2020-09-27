package com.example.demo.controller;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.FileNotFoundException;
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
import org.apache.poi.ss.usermodel.Row;
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

import com.example.demo.entity.FileAttributes;
import com.example.demo.entity.MessageFile;

import com.example.demo.service.FileService;
import com.example.demo.service.MessageService;

@RestController
public class HomeApiController {

	  	private XSSFWorkbook workbook;
	    private XSSFSheet sheet;
	    
	    private FileAttributes tempFileObj;
	    private MessageFile  tempMessageObj;
	    private List<MessageFile> tempMessageList;
	    
	    @Autowired
	    private FileService fileSaves;
	    @Autowired
	    private MessageService messageSaves;
	    private String fileName=null;
	    private void createExcelFile() {
	    	  workbook = new XSSFWorkbook();
	    	  sheet = workbook.createSheet("First");
	          
	          Row row = sheet.createRow(0);
	           
	          CellStyle style = workbook.createCellStyle();
	          XSSFFont font = workbook.createFont();
	          font.setBold(true);
	          font.setFontHeight(16);
	          style.setFont(font);
	           
	          createCell(row, 0, "File Name", style);      
	          createCell(row, 1, "Start Line", style);       
	          createCell(row, 2, "End Line", style);    
	    }
	    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	        sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        cell.setCellValue((String)value);
	        cell.setCellStyle(style);
	    }

	@Autowired
	private FileService fileSave;
   
	@GetMapping("/readFile")
	public String readFile() {
		return "index.html";
	}
@GetMapping("/printExcel")
public String getFile(@RequestParam("getFileName") String z) {
	fileSave.getFileName(z);
	return "YESS";
}
	
@GetMapping("/linees")
public int getDataFromDb(@RequestParam("getFile") String z){
	
	int ze=fileSave.getFileById(z);
	System.out.print(ze);
	List<MessageFile> tempTocheck=messageSaves.printExcelFromId(ze);
	
	for(MessageFile e:tempTocheck) {
		System.out.print("YES");
		System.out.print(e.getMessage());
	}
	return ze;
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
 */	@GetMapping("/printItNow")
	public void export(HttpServletResponse response) throws IOException {
		createExcelFile();
		fileName="ReadCsvConvertJsonApplication.java";
        //getline(fileName);
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
	@PostMapping("/SaveItAll")
	public String saveDocument(@RequestParam("start_line") String start_line, @RequestParam("end_line") String z,
			@RequestParam("description") String s, @RequestParam("files") String paths,
			@RequestParam("query") String queries, @RequestParam("message") String msg) {

		
		FileAttributes obj = new FileAttributes();
		obj.setFilePath(paths);

		MessageFile obje = new MessageFile();
		obje.setMessage(msg);
		
		List<MessageFile> list = new ArrayList<MessageFile>();
		list.add(obje);
		obj.setMessageObj(list);
		fileSave.saveFile(obj);

		/*
		 * if(f.getStart_line().contains(null) || f.getEnd_line().contains(null) ||
		 * f.getDescription().contains(null)) { return "Values Cannot Be Nulled"; }
		 * 
		 */
		// else {
		// fileSave.saveFile(obj);
		// }

		return "OKAY";
	}

	@GetMapping("/checkResult")
	public String getItYar(@RequestParam("fileName") String z) {
		String toprint=fileSave.getFileName(z);
        return	toprint;
          
	}
	
	@PostMapping("/api/upload")
	public String saveIt(@RequestParam("start_line") String start_line, @RequestParam("end_line") String z,
			@RequestParam("description") String s, @RequestParam("files") String paths,
			@RequestParam("query") String queries, @RequestParam("message") String msg) {
	
	int checkIfExist=(getItYar(paths)==null) ? 0 : Integer.parseInt(getItYar(paths)) ;
	if(checkIfExist==0) {
		
		tempFileObj=new FileAttributes();
		tempMessageObj=new MessageFile();
		tempMessageList=new ArrayList<MessageFile>();
		
		
		tempFileObj.setFilePath(paths);
		tempMessageObj.setQuery(queries);
		tempMessageObj.setMessage(msg);
		tempMessageObj.setDescription(s);
		tempMessageObj.setStart_line(start_line);
		tempMessageObj.setEnd_line(z);
		
		tempMessageList.add(tempMessageObj);
		
		tempFileObj.setMessageObj(tempMessageList);

		fileSaves.saveFile(tempFileObj);
	return "First Saved";
	}
	else {
	//	tempFileObj=fileSaves.getFileById(checkIfExist);
		tempMessageObj=new MessageFile();
		tempMessageList=new ArrayList<MessageFile>();
		tempMessageObj.setQuery(queries);
		tempMessageObj.setMessage(msg);
		tempMessageObj.setDescription(s);
		tempMessageObj.setStart_line(start_line);
		tempMessageObj.setEnd_line(z);
		messageSaves.saveExistingFile(checkIfExist, start_line, z, s, queries, msg);

return "Second Saved";
	}
	
	
	}
	
	@GetMapping("/getCheck")
	public Iterable<FileAttributes> getFile() {
		return fileSave.getFiles();
	}

	@PostMapping("/api/postCheck/{data}")
	public String check(@RequestParam("data") String data) {
		System.out.print(data);
		return "OK";
	}
	@ResponseBody
	@PostMapping(value="/sendJson")
	public String post(@RequestBody FileAttributes file) {
	System.out.print(file.getFilePath());
		return "YES";
	}
	
}
