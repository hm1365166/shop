package com.shop.dao;

import com.file.entity.MyFile;
import com.shop.entity.File;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


public interface FileDao {

	/**
	 *
	 * @param userId
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/10 08:38:08
	 */
	List<MyFile> getFileByUserId(String userId) throws Throwable;

	/**
	 *
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/11 11:09:21
	 */
	List<MyFile> getFileToShow() throws Throwable;

	/**
	 *
	 * @param file
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/10 10:15:20
	 *
	 */
	void insertFile(File file) throws Throwable;

	/**
	 *
	 * @param file
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/10 15:59:25
	 */
	void updateByUserId(File file) throws Throwable;



}
