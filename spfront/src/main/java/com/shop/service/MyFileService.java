package com.shop.service;

import com.file.entity.MyFile;

import java.util.List;

public interface MyFileService {

	void insertMyFile(MyFile myFile) throws Throwable;

	List<MyFile> getMyFileByUserId(String userId) throws Throwable;

}
