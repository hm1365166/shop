package com.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.file.entity.CheckFileChanges;
import com.file.entity.Download;
import com.file.entity.MultipartFileEntity;
import com.file.entity.MultipartRsq;
import com.file.entity.MyFile;
import com.file.entity.ResponseRsp;
import com.file.service.DownloadFileService;
import com.file.service.MyFileService;
import com.file.service.UploadFileService;
import com.file.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.entity.Enum.FileStatueEnum;
import com.shop.entity.File;
import com.shop.entity.TotalInvest;
import com.shop.entity.rpc.OpResponse;
import com.shop.service.FileService;
import com.shop.service.user.UserService;
import com.shop.util.FileUtil;
import com.shop.util.JacksonUtil;
import com.shop.util.RedisTemplateUtil;
import com.shop.util.ResultCode;
import com.shop.util.Rpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/file")
public class FileController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("User_Rpc")
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Autowired
	@Qualifier("MyFileService")
	private MyFileService myFileService;

	@Autowired
	@Qualifier("DownloadFileService")
	private DownloadFileService downloadFileService;

	@Value("${person_file_path}")
	private String File_PATH;

	@Autowired
	@Qualifier("UploadFileService")
	private UploadFileService UploadFileService;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, MyFile> fileTemplate;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Integer> stringTemplate;

	@RequestMapping(value = "/toFile", method = RequestMethod.GET)
	public String toFileOperate(HttpServletRequest request, @RequestParam(value = "pageNum", required = false) Integer pageNum) {
		final String filesStringKey = "files";
		final String filesIdKey = "idKey";
		final String filesIdCountKey = "idCountKey";
		if (StringUtils.isEmpty(pageNum)) {
			pageNum = 1;
		}
		//获取第1页，10条内容，默认查询总数count
		List<MyFile> files = null;
			/*
			if (RedisTemplateUtil.hasKey("files")) {
				//files = RedisTemplateUtil.getList("files",pageNum-1,pageNum);
				files = RedisTemplateUtil.get("files");
				logger.info("size"+files.size()+"pageNum"+pageNum+"file"+files);
			} else {
				files = fileService.getFileToShow();
				logger.info("size"+files.size());
				RedisTemplateUtil.setNX("files",files);
			}*/
		//使用缓存存储分页
		PageInfo pageUser = null;
		com.shop.entity.User user = (com.shop.entity.User) request.getSession().getAttribute("user");
		CheckFileChanges checkFileChanges;
		try {
			checkFileChanges = myFileService.getFileChangesStatus(user.getUserId());
		} catch (Throwable throwable) {
			logger.info("用户未登录！----------------------------------------------");
			throwable.printStackTrace();
			return "index";
		}

		boolean flash = false;
		if (fileTemplate.hasKey(filesIdKey)) {
			if (!(Objects.equals(checkFileChanges.getId(), (stringTemplate.opsForValue().get(filesIdKey)))
					&& Objects.equals(checkFileChanges.getIdCount(), (stringTemplate.opsForValue().get(filesIdCountKey)))
			)) {
				stringTemplate.opsForValue().set(filesIdKey, checkFileChanges.getId());
				stringTemplate.opsForValue().set(filesIdCountKey, checkFileChanges.getIdCount());
				logger.info("数据已更改，更新缓存--------------------------------------------");
			} else {
				flash = true;
			}
		} else {
			stringTemplate.opsForValue().set(filesIdKey, checkFileChanges.getId());
			stringTemplate.opsForValue().set(filesIdCountKey, checkFileChanges.getIdCount());
			logger.info("初始化数据，更新缓存--------------------------------------------");
		}

		if (fileTemplate.hasKey(filesStringKey) && flash) {
			List<MyFile> filesList = fileTemplate.opsForList().range(filesStringKey, 0, fileTemplate.opsForList().size(filesStringKey) - 1);
			pageUser = getCachePagesInfo(filesList, pageNum, 5);// 0 - 4 ,5 - 10
			System.out.println(JacksonUtil.toJson(filesList));
			//RedisUtil.lpush("testfiles", filesList);
			//List<File> filesList1 = RedisUtil.lrangeList("testfiles", 0, 1, File.class);
			//System.out.println("test" + filesList1);
			files = fileTemplate.opsForList().range(filesStringKey, (pageNum - 1) * 5, (pageNum - 1) * 5+4);
			logger.info("数据未更改，从缓存中获取-----------------------------------------------");
			logger.info("size" + files.size() + "pageNum" + pageNum + "file" + files);
			logger.info("pageUser" + pageUser);
		} else {
			try {
				RedisTemplateUtil.del(filesStringKey);
				files = myFileService.getMyFileByUserId(user.getUserId());
				pageUser = getCachePagesInfo(files, pageNum, 5);
				if (files != null && files.size() != 0) {
					fileTemplate.opsForList().leftPushAll(filesStringKey, files);
				}
				//RedisTemplateUtil.setList("list", files);
				files = myFileService.getMyFileByUserIdWithPage(pageNum, user.getUserId());
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}

		if (files != null && files.size() != 0) {
			request.getSession().setAttribute("pageUser", pageUser);
			request.getSession().setAttribute("fileDate", files);
			request.getSession().setAttribute("file", files.get(0));
		} else {
			request.getSession().removeAttribute("pageUser");
			request.getSession().removeAttribute("fileDate");
			request.getSession().removeAttribute("file");
		}

		return "/file/uploadFile";

	}

	private PageInfo getCachePagesInfo(List pageInfo, int pageNum, int pageSize) {
		PageInfo listInfo = null;
		PageInfo pageUser = null;
		listInfo = new PageInfo(pageInfo);
		logger.info("listInfo" + listInfo);
		listInfo.setPageSize(pageSize);
		pageUser = new PageInfo<>();
		pageUser.setPageNum(pageNum);
		pageUser.setPageSize(pageSize);
		int size = Integer.valueOf(listInfo.getTotal() + "");
		int pages = size / listInfo.getPageSize();
		if (size % listInfo.getPageSize() > 0) {
			pages = pages + 1;
		}
		pageUser.setPages(pages);
		pageUser.setPrePage(pageNum == 0 ? pageNum : pageNum - 1);
		pageUser.setNextPage(pageNum == listInfo.getSize() / pageSize + 1 ? pageNum : pageNum + 1);
		pageUser.setSize(pageSize);
		pageUser.setTotal(listInfo.getTotal());
		return pageUser;
	}

	@ResponseBody
	@RequestMapping(value = "/showMyFile", method = RequestMethod.POST)
	public String showMyFile(HttpServletRequest request, @RequestParam(value = "pageNum", required = true) int pageNum,
			@RequestParam(value = "userId", required = false) String userId) throws FileNotFoundException {
		//获取第1页，10条内容，默认查询总数count
		PageHelper.startPage(pageNum, 5);
		List<MyFile> files = null;
		try {
			files = myFileService.getMyFileByUserId(userId);
			PageInfo<MyFile> fileListPageInfo = new PageInfo<>(files);
			if (files.size() != 0) {
				request.getSession().setAttribute("pageUser", fileListPageInfo);
				request.getSession().setAttribute("fileDate", files);
				request.getSession().setAttribute("file", files.get(0));
			}
		} catch (Throwable throwable) {
			logger.error("显示我的文件" + throwable.getCause());
			throwable.printStackTrace();
		}
		return "/file/uploadFile";
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result uploadFile(MultipartHttpServletRequest multipartRequest, @RequestParam(value = "userId", required = false) String userId) throws Throwable {

		MultipartRsq multipartRsq = new MultipartRsq();
		Result rt;
		List<MultipartFile> multipartFiles = multipartRequest.getFiles("file");
		MultipartFileEntity multipartFileEntity;
		List<MultipartFileEntity> multipartFileEntityList = new ArrayList<>();

		for (MultipartFile multipartFile : multipartFiles) {
			if (multipartFile == null) {
				continue;
			}
			multipartFileEntity = new MultipartFileEntity();
			multipartFileEntity.setContentType(multipartFile.getContentType());
			multipartFileEntity.setEmpty(multipartFile.isEmpty());
			multipartFileEntity.setName(multipartFile.getName());
			multipartFileEntity.setOriginalFilename(multipartFile.getOriginalFilename());
			multipartFileEntity.setSize(multipartFile.getSize());
			multipartFileEntity.setFileBytes(multipartFile.getBytes());
			multipartFileEntityList.add(multipartFileEntity);
		}

		multipartRsq.setParameter(userId);
		multipartRsq.setMultipartFileEntityList(multipartFileEntityList);
		rt = UploadFileService.uploadFile(multipartRsq);

		return rt;
	}

	@ResponseBody
	@RequestMapping(value = "/drop", method = RequestMethod.POST)
	public Result dropFile(@RequestParam("userId") String userId) {
		Result rt = new Result(ResultCode.DEFAULT_CODE);
		try {
			File personResume = new File();
			personResume.setFileIdentity(userId);
			personResume.setFileStatue(FileStatueEnum.UNAVAILABLE.getName());
			fileService.updateByUserId(personResume);
			rt.setStatus(ResultCode.SUCCESS);
		} catch (Exception e) {
			logger.info("删除文件异常：{} ", e.toString());
			rt.setStatus(ResultCode.FAILED);
			rt.setErrorMessage("删除文件异常");
		} catch (Throwable throwable) {
			logger.error("更新异常" + throwable.getCause());
			throwable.printStackTrace();
		}
		return rt;
	}

	/**
	 * 根据确定url数组下载多个文件的压缩文件
	 * @param response
	 * @param url
	 * @author:HM
	 * @date: 2017/10/12 16:03:52
	 */
	@RequestMapping(value = "/downloadMulByUrls", method = RequestMethod.POST)
	public void downLoadSpecialMulFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("url") String ids) {
		String[] filePaths = ids.substring(ids.indexOf(";") + 1).split(";");
		if (filePaths == null || filePaths.length == 0) {
			return;
		}
		List<Integer> idlist = new ArrayList<>(filePaths.length);
		for (String id : filePaths) {
			idlist.add(Integer.valueOf(id.trim()));
		}
		Download download = null;
		ResponseRsp<Download> responseRsp;
		try {
			responseRsp = downloadFileService.downloadMulFile(idlist);
			download = responseRsp.getContent();
		} catch (Throwable throwable) {
			com.shop.entity.User user = (com.shop.entity.User) request.getSession().getAttribute("user");
			logger.info("用户" + user.getUserId() + "下载文件错误" + throwable.getCause());
			throwable.printStackTrace();
		}

		FileUtil.downloadFileForWeb(response, download);
	}

	/**
	 * 该方法支持多个文件下载
	 * 会将文件打包成压缩文件
	 * 下载
	 *
	 * @param response
	 * @param userId
	 * @author:HM
	 * @date: 2017/10/10 15:09:02
	 *//*
	@RequestMapping(value = "/downloadMul", method = RequestMethod.GET)
	public void downloadMulFile(HttpServletResponse response, @RequestParam("userId") String userId) {
		FileInputStream fin = null;
		Download download = new Download();
		*//*List<MyFile> resumes = fileService.getFileByUserId(userId);
			String[] filePaths = new String[resumes.size()];
			final String zipPath = FileUtil.MulFileToZip(filePaths);
			final java.io.File zipFile = new java.io.File(zipPath);
			Download download = new Download();
			download.setContentType("application/octet-stream");
			download.setFileName(zipFile.getName());
			download.setFilePath(zipFile.getPath());*//*
		try {
			//download = myFileService.getFileRealPathById();
			//FileUtil.downloadFileForWeb(response, download);
		} catch (IOException e) {
			logger.error("下载上传的附件异常：{}", e.getCause());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("根据id {} 查询候选人简历记录异常：{}", userId, e.getCause());
			e.printStackTrace();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}*/

	/**
	 * 单个文件下载
	 *
	 * @param response
	 * @param userId
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @RequestParam("userId") String userId) {
		FileInputStream fin = null;
		try {

			List<MyFile> resume = fileService.getFileByUserId(String.valueOf(userId));
			Download download = new Download();
			download.setContentType(resume.get(0).getFileType());
			download.setFileName(resume.get(0).getFileName());
			//download.setFilePath(resume.get(0).getFilePath());
			FileUtil.downloadFileForWeb(response, download);

		} catch (IOException e) {
			logger.error("下载上传的附件异常：{}", e.getCause());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("根据id {} 查询候选人简历记录异常：{}", userId, e.getCause());
			e.printStackTrace();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//@RequestMapping("/imageDownload")
	public ModelAndView download(@RequestParam("url")
			String fileName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String downLoadPath = fileName;
		System.out.println(downLoadPath);
		try {
			long fileLength = new java.io.File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	//文件下载
	@RequestMapping("/imageDownload")
	public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("url")
			String fileName) {
		//String path=request.getServletContext().getRealPath("/")+"/images/rawImages/";
		String[] url = fileName.split(";");
		if (url.length > 1) {
			FileUtil.MulFileToZip(url);

		}
		java.io.File file = new java.io.File(fileName);
		int i = fileName.lastIndexOf("/");
		String outName = fileName.substring(i + 1);
		if (file.exists()) {
			//设置MIME类型
			//	response.setContentType("application/octet-stream");
			// 或者为response.setContentType("application/x-msdownload");
			try {
				String contentType = "application/octet-stream";
				String fileRealName = new String(outName.getBytes(), "ISO-8859-1");
				Download download = new Download();
				download.setContentType(contentType);
				download.setFileName(fileRealName);
				download.setFilePath(fileName);
				FileUtil.downloadFileForWeb(response, download);
			} catch (Exception e) {
				logger.error("imageDownload Exception" + e.getCause());
				e.printStackTrace();
			} catch (Throwable throwable) {
				logger.error("下载错误 throwable Exception" + throwable.getCause());
				throwable.printStackTrace();
			}
		}
	}

	@Cacheable("user")
	public List<File> files(String province) {
		logger.debug("province=" + province);
		List<File> files = null;
		try {
			/*files = fileService.getFileToShow();*/
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return files;
	}

	@RequestMapping("/getQuarterInvest")
	public void getQuarterInvest(HttpServletResponse response) {
	/*	Rpc rpc = Rpc.getInstance();
		OpResponse<List<TotalInvest>> f = null;
		try {
			f = rpc.queryQuarterTotalInvest();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("test", f);
			writeToStream(response, jsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}

	@RequestMapping("/queryShopStore")
	public void getBaiduTest(HttpServletResponse response) {
		Rpc rpc = Rpc.getInstance();
		try {
			String f = rpc.queryShopStore();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("test", f);
			writeToStream(response, jsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/getCrazy")
	public void getCrazy(HttpServletResponse response) {
		Rpc rpc = Rpc.getInstance();
		try {
			OpResponse<List<TotalInvest>> f = rpc.queryQuarterTotalInvestNumbers();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("test", f);
			writeToStream(response, jsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/dubboTest")
	public void dubboTest() {
		try {
			logger.info("checkUserByPhone: " + userService.selectByUserPhone("123456"));
			logger.info("getUserInfo: " + userService.getUserInfo());
			String text = JSON.toJSONString(userService.getUserInfo());
			//logger.info("" + JSON.parseObject(text, new TypeReference<User>(){}));
		} catch (Throwable throwable) {
			logger.error("dubbo test error->" + throwable);
			throwable.printStackTrace();
		}
	}

	public void writeToStream(HttpServletResponse response, JSONObject jsonObject) {

		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.write(jsonObject.toString());
			out.close();
		} catch (IOException e) {
			logger.error("out error" + e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(FileController.class.getResource("/").getPath());
	}

}
