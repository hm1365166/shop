package com.shop.service.impl;

import com.file.entity.MyFile;
import com.shop.dao.FileDao;
import com.shop.entity.File;
import com.shop.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	FileDao fileDao;

	@Override
	public List<MyFile> getFileByUserId(String userId) throws Throwable {
		return fileDao.getFileByUserId(userId);
	}

	@Override public List<MyFile> getFileToShow() throws Throwable {
		return fileDao.getFileToShow();
	}

	@Override public void insertFile(File file) throws Throwable {
		fileDao.insertFile(file);
	}

	@Override public void updateByUserId(File file) throws Throwable {

	}
}
