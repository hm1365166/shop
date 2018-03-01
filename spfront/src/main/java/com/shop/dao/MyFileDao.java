package com.shop.dao;


import com.file.entity.MyFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyFileDao {

	void insertMyFile(MyFile myFile) throws Throwable;

	List<MyFile> getMyFileByUserId(String userId) throws Throwable;

}
