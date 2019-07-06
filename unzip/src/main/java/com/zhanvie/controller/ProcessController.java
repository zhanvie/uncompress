package com.zhanvie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhanvie.utils.ZipAndRarUtil;

@Controller
public class ProcessController {
	
	@RequestMapping("uploadfiles")
	@ResponseBody
	public Map<String, Object> uploadfiles(@RequestParam List<MultipartFile> files) {
		Map<String,Object> rv = new HashMap<String,Object> ();
		MultipartFile file = files.get(0);
		String originalFilename = file.getOriginalFilename();
		int index = originalFilename.lastIndexOf(".");
		String fileType = null;
		boolean flag = false;
		if(index != -1) {
			fileType = originalFilename.substring(index + 1);
		}else {
			rv.put("issuccess", false);
			rv.put("msg", "文件无法解析");
			flag = true;
		}
		if(!"zip".equals(fileType) && !"rar".equals(fileType)) {
			rv.put("issuccess", false);
			rv.put("msg", "文件无法解析");
			flag = true;
		}
		if(flag) return rv;
		
		try {
			File outpath = new File("E:\\temp");
			if(!outpath.exists()) outpath.mkdirs();
			File outfile = new File("E:\\temp\\"+originalFilename);
			file.transferTo(outfile);
			ZipAndRarUtil.deCompress(fileType,outfile,"E:\\yasuobao");
			outfile.delete();
			outpath.delete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rv;
	}
}
