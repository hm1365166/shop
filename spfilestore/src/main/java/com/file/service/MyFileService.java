package com.file.service;

import com.file.entity.CheckFileChanges;
import com.file.entity.MyFile;
import com.file.entity.ResponseRsp;

import java.util.List;

/**
 * MyFileService
 *
 * @author:HM
 * @date: 17-12-22 09:45:21
 * @since v1.0.0
 */
public interface MyFileService {

	void insertMyFile(MyFile myFile) throws Throwable;

	List<MyFile> getMyFileByUserId(String userId) throws Throwable;

	List<MyFile> getMyFileByUserIdWithPage(int pageNum, String userId) throws Throwable;

	List<String> getFileRealPathById(List<Integer> id) throws Throwable;

	ResponseRsp downloadMulFile(List<Integer> id);

	/**
	 * getFileChangesStatus
	 *
	 * @param userId
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-12-25 10:27:31
	 * @since v1.0.0
	 */
	CheckFileChanges getFileChangesStatus(String userId) throws Throwable;
}
