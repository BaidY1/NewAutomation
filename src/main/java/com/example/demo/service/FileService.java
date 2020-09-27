package com.example.demo.service;

import java.awt.List;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FileAttributes;
import com.example.demo.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;

	public FileAttributes saveFile(FileAttributes file) {
      return fileRepository.save(file);
	}
	public Iterable<FileAttributes> getFiles() {
		return fileRepository.findAll();
	}
	
	
	public int getFileById(String ids) {
		return fileRepository.getFileObject(ids);
	}
	
	public java.util.List<FileAttributes> getRelationalData(int x){
		return fileRepository.printExcel(x);
	}
	public String getFileName(String x) {
		return (String) fileRepository.getByName(x);
	}
	
	public FileAttributes SaveFileJson(String z) {
		return fileRepository.saveObject(z);
	}
}
