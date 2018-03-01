package com.shop.service;

import com.file.entity.MyFile;
import com.shop.entity.File;

import java.util.List;

public interface FileService {
	List<MyFile> getFileByUserId(String userId) throws Throwable;

	List<MyFile> getFileToShow() throws Throwable;

	void insertFile(File file) throws Throwable;

	void updateByUserId(File file) throws Throwable;
}
