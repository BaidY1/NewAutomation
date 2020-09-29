package com.example.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FileAttributes;
import com.example.demo.entity.MessageFile;

@Repository
public interface MessageRepository extends JpaRepository<MessageFile, Integer> {
	
	@Query(value="SELECT e.message_id,e.start_line,e.end_line,e.message,e.description,e.short_description,e.query,f.file_path from message_file as e " + 
			"inner join save_document as f on f.id=e.file_id " + 
			"where e.file_id=?1",nativeQuery=true)
	public List<MessageFile> printExcelMessage(@Param("fileName") int x);
	
	
	
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO message_file(description,end_line,message,query,start_line,short_description,file_id) "
			+ "VALUES (:desc,:end_line,:msg,:quer,:start,:short_desc,:file)",nativeQuery=true)
	public void saveExisting(@Param("start") String startline,
			@Param("end_line") String endline,
			@Param("desc") String desc,
			@Param("quer") String query,
			@Param("msg") String message,
			@Param("short_desc") String shortdesc,
			@Param("file") int fileId);
	
 

}
