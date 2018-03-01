package com.file.service;

import com.file.entity.MultipartRsq;
import com.file.util.Result;

public interface UploadFileService {

	Result uploadFile(MultipartRsq multipartRsq) throws Throwable;

}
