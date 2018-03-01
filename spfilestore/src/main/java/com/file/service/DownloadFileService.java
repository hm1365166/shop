package com.file.service;

import com.file.entity.ResponseRsp;

import java.util.List;

/**
 * DownloadFileService
 *
 * @author:HM
 * @date: 17-12-23 10:53:10
 * @since v1.0.0
 */
public interface DownloadFileService {

	/**
	 *  downloadMulFile
	 *
	 * @author:HM
	 * @date: 17-12-26 18:43:10
	 * @since v1.0.0
	 * @param id 文件id
	 * @return
	 */
	ResponseRsp downloadMulFile(List<Integer> id);

}
