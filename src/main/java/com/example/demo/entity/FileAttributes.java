package com.example.demo.entity;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.NoArgsConstructor;

@Entity(name="SaveDocument")
@NoArgsConstructor
public class FileAttributes {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
String filePath;

@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name="fileId",referencedColumnName = "id")
private List<MessageFile> messageObj;

public MessageFile getSingle(int i) {
	return messageObj.get(i);
}



public List<MessageFile> getMessageObj() {
	return messageObj;
}
public void setMessageObj(List<MessageFile> messageObj) {
	this.messageObj = messageObj;
}
public String getFilePath() {
	return filePath;
}
public void setFilePath(String filePath) {
	this.filePath = filePath;
}
public FileAttributes(){
	
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
}