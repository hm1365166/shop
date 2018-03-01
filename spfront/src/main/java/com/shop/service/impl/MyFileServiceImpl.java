package com.shop.service.impl;

import com.file.entity.MyFile;
import com.shop.dao.MyFileDao;
import com.shop.service.MyFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyFileServiceImpl implements MyFileService {

	@Autowired
	MyFileDao myFileDao;

	@Override
	public void insertMyFile(MyFile myFile) throws Throwable {
		myFileDao.insertMyFile(myFile);
	}


	@Override public List<MyFile> getMyFileByUserId(String userId) throws Throwable {
		return myFileDao.getMyFileByUserId(userId);
	}

}
