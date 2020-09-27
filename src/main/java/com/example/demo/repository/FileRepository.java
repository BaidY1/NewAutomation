package com.example.demo.repository;

import java.lang.annotation.Native;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FileAttributes;

@Repository
public interface FileRepository extends JpaRepository<FileAttributes, Integer>  {
	
	@Query(value="Select DISTINCT id from save_document where file_path=?1",nativeQuery=true)
	public String getByName(@Param("fileName") String s);
	
	@Query(value="SELECT e.start_line,e.end_line,e.message,e.description,e.query,f.file_path from message_file as e " + 
			"inner join save_document as f on f.Id=e.message_Id " + 
			"where e.file_id=?1",nativeQuery=true)
	public List<FileAttributes> printExcel(@Param("fileName") int x);
	
	@Query(value="SELECT Id from save_document where file_path=?1",nativeQuery=true)
	public int getFileObject(@Param("id") String id);

@Query(value="INSERT into save_document(file_path) VALUES(:filepath)",nativeQuery=true)
public FileAttributes saveObject(@Param("filepath") String filepath);
} 

