package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MessageFile;
import com.example.demo.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageObj;
	
	public java.util.List<MessageFile> printExcelFromId(int id){
		return messageObj.printExcelMessage(id);
	}	
	public MessageFile saveMessage(MessageFile msg) {
	
		return messageObj.save(msg);
		}
public void saveExistingFile(int file_id,String startlines,String endlines,
		String desc,String query,String message) 
{
	 messageObj.saveExisting(startlines, endlines, desc, query, message, file_id);
}

}
