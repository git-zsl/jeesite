/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.ReturnEntity;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.test.entity.TestData;
import com.thinkgem.jeesite.test.service.TestDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2015-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testData")
public class TestDataController extends BaseController {

	@Autowired
	private TestDataService testDataService;
	@Autowired
	private ArticleService articleService;

	@ModelAttribute
	public TestData get(@RequestParam(required=false) String id) {
		TestData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testDataService.get(id);
		}
		if (entity == null){
			entity = new TestData();
		}
		return entity;
	}
	
	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = {"list", ""})
	public String list(TestData testData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestData> page = testDataService.findPage(new Page<TestData>(request, response), testData); 
		model.addAttribute("page", page);
		return "jeesite/test/testDataList";
	}

	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "form")
	public String form(TestData testData, Model model) {
		model.addAttribute("testData", testData);
		return "jeesite/test/testDataForm";
	}

	@RequiresPermissions("test:testData:edit")
	@RequestMapping(value = "save")
	public String save(TestData testData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, testData)){
			return form(testData, model);
		}
		testDataService.save(testData);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/test/testData/?repage";
	}

	@RequiresPermissions("test:testData:edit")
	@RequestMapping(value = "delete")
	public String delete(TestData testData, RedirectAttributes redirectAttributes) {
		testDataService.delete(testData);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/test/testData/?repage";
	}

	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "demo")
	public String demo(Article article, RedirectAttributes redirectAttributes,Model model) {
		//查询一年的文章数量
		Map<String,Object> byYearCount = articleService.findByYearCount();
		List<Article> list = articleService.findArticles();
		model.addAttribute("list",list);
		return "/modules/test/echarts";
	}

		@ResponseBody
		@RequestMapping("/upload/editor/img")
		//RequestParam中的属性名称要和前端定义的一致，上面说明了．所以写"img"
		//使用List<MultipartFile>进行接收
		//返回的是一个Ｄto类，后面会说明，使用@ResponseBody会将其转换为Ｊson格式数据
		public ReturnEntity uploadEditorImg(@RequestParam("img") List<MultipartFile> list,HttpServletRequest request) {
			//这里是我在web中定义的两个初始化属性，保存目录的绝对路径和相对路径，你们可以自定义
			String imgUploadAbsolutePath = request.getSession().getServletContext().getInitParameter("imgUploadAbsolutePath");
			String imgUploadRelativePath = request.getSession().getServletContext().getInitParameter("imgUploadRelativePath");
        //服务曾处理数据，返回Dto
			/*ImgResultDto imgResult
					= addCommodityService.upLoadEditorImg(list, imgUploadAbsolutePath,
					imgUploadRelativePath,1);*/
			return ReturnEntity.success("成功");
		}
	/*@RequestMapping("findEditor")
	//RequestParam中的属性名称要和前端定义的一致，上面说明了．所以写"img"
	//使用List<MultipartFile>进行接收
	//返回的是一个Ｄto类，后面会说明，使用@ResponseBody会将其转换为Ｊson格式数据
	public String findEditor() {
		return "modules/test/wangEditor";
	}

	*//**
	 *
	 * @Title: fileUp
	 * @Description:wangEditor上传图片
	 * @param
	 * @return
	 *//*
	@RequestMapping("/fileUp")
	@ResponseBody
	public ReturnEntity fileUp(HttpServletRequest request) throws Exception{
		*//*AjaxJson j = new AjaxJson();*//*
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			multipartRequest.setCharacterEncoding("UTF-8");
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存的路径
			String path = null;
			// 文件保存在硬盘的相对路径
			String realPath = null;

			*//*realPath = ResourceUtil.getConfigByName("webUploadpath") + "/";
			path = ResourceUtil.getConfigByName("http_url") + "/";
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			realPath += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
			path += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
			file = new File(realPath);
			if (!file.exists()) {
				file.mkdir();// 创建文件时间子目录
			}
			String fileName = "";
			// String swfName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {

				MultipartFile mf = entity.getValue();// 获取上传文件对象
				fileName = mf.getOriginalFilename();// 获取文件名
				// swfName =
				// PinyinUtil.getPinYinHeadChar(oConvertUtils.replaceBlank(FileUtils.getFilePrefix(fileName)));//
				// 取文件名首字母作为SWF文件名
				String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
				String myfilename = "";
				String noextfilename = "";// 不带扩展名

				noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + StringUtil.random(8);// 自定义文件名称
				myfilename = noextfilename + "." + extend;// 自定义文件名称

				String savePath = realPath + myfilename;// 文件保存全路径

				File savefile = new File(savePath);
				if (entity.getKey() != null) {
					// 设置文件数据库的物理路径
					String filePath = path + myfilename;
					j.setObj(filePath);
				}
				// 文件拷贝到指定硬盘目录
				if ("txt".equals(extend)) {
					// 利用utf-8字符集的固定首行隐藏编码原理
					// Unicode:FF FE UTF-8:EF BB
					byte[] allbytes = mf.getBytes();
					try {
						String head1 = toHexString(allbytes[0]);
						// System.out.println(head1);
						String head2 = toHexString(allbytes[1]);
						// System.out.println(head2);
						if ("ef".equals(head1) && "bb".equals(head2)) {
							// UTF-8
							String contents = new String(mf.getBytes(), "UTF-8");
							if (StringUtils.isNotBlank(contents)) {
								OutputStream out = new FileOutputStream(savePath);
								out.write(contents.getBytes());
								out.close();
							}
						} else {

							// GBK
							String contents = new String(mf.getBytes(), "GBK");
							OutputStream out = new FileOutputStream(savePath);
							out.write(contents.getBytes());
							out.close();
						}
					} catch (Exception e) {
						String contents = new String(mf.getBytes(), "UTF-8");
						if (StringUtils.isNotBlank(contents)) {
							OutputStream out = new FileOutputStream(savePath);
							out.write(contents.getBytes());
							out.close();
						}
					}
				} else {
					FileCopyUtils.copy(mf.getBytes(), savefile);
				}
			}
		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
		}*//*
		return ReturnEntity.success("成功");
	}

	private String toHexString(int index) {
		String hexString = Integer.toHexString(index);
		// 1个byte变成16进制的，只需要2位就可以表示了，取后面两位，去掉前面的符号填充
		hexString = hexString.substring(hexString.length() - 2);
		return hexString;
	}*/
}