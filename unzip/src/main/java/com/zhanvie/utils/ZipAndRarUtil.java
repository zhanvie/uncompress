package com.zhanvie.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

public class ZipAndRarUtil {
	/**
	 * 解压缩zip文件 
	 * @param sourceZip	zip文件路径
	 * @param destDir	目标路径
	 * @throws Exception
	 */
	   private static void unzip(String sourceZip,String destDir) throws Exception{    
	       try{    
	           Project p = new Project();    
	           Expand e = new Expand();    
	           e.setProject(p);    
	           e.setSrc(new File(sourceZip));    
	           e.setOverwrite(false);    
	           File fileout = new File(destDir);
	           if(!fileout.exists()) fileout.mkdirs();
	           e.setDest(fileout);    
	           /*  
		           ant下的zip工具默认压缩编码为UTF-8编码，  
			           而winRAR软件压缩是用的windows默认的GBK或者GB2312编码  
			           所以解压缩时要制定编码格式  
	           */   
	           e.setEncoding("GBK");    
	           e.execute();    
	       }catch(Exception e){    
	           throw e;    
	       }    
	   }    
	   
	   /**
	    * 解压缩zip文件
	    * @param srcFile	zip文件
	    * @param destDir	目标路径
	    * @throws Exception
	    */
	   private static void unzip(File srcFile,String destDir) throws Exception{    
	       try{    
	           Project p = new Project();    
	           Expand e = new Expand();    
	           e.setProject(p);    
	           e.setSrc(srcFile);    
	           e.setOverwrite(false);    
	           File fileout = new File(destDir);
	           if(!fileout.exists()) fileout.mkdirs();
	           e.setDest(fileout);    
	           e.setEncoding("GBK");    
	           e.execute();    
	       }catch(Exception e){    
	           throw e;    
	       }    
	   }  
	   
	   /**
	    * 解压缩rar文件
	    * @param sourceRar	rar文件路径
	    * @param destDir	目标路径
	    * @throws Exception
	    */
	   private static void unrar(String sourceRar, String destDir) throws Exception {
	        File outFileDir = new File(destDir);
	        if (!outFileDir.exists()) {
	            outFileDir.mkdirs();
	        }
	        File rarFile = new File(sourceRar);
	        Archive archive = new Archive(new FileInputStream(rarFile));
	        FileHeader fileHeader = archive.nextFileHeader();
	        while (fileHeader != null) {
	            if (fileHeader.isDirectory()) {
	                fileHeader = archive.nextFileHeader();
	                continue;
	            }
	            File out = new File(destDir + fileHeader.getFileNameString());
	            if (!out.exists()) {
	                if (!out.getParentFile().exists()) {
	                    out.getParentFile().mkdirs();
	                }
	                out.createNewFile();
	            }
	            FileOutputStream os = new FileOutputStream(out);
	            archive.extractFile(fileHeader, os);

	            os.close();

	            fileHeader = archive.nextFileHeader();
	        }
	        archive.close();
	    }
	   
	   /**
	    * 解压缩rar文件
	    * @param srcFile	rar文件
	    * @param destDir	目标路径
	    * @throws Exception
	    */
	   private static void unrar(File srcFile, String destDir) throws Exception {
	        File outFileDir = new File(destDir);
	        if (!outFileDir.exists()) {
	            outFileDir.mkdirs();
	        }
	        Archive archive = new Archive(new FileInputStream(srcFile));
	        FileHeader fileHeader = archive.nextFileHeader();
	        while (fileHeader != null) {
	            if (fileHeader.isDirectory()) {
	                fileHeader = archive.nextFileHeader();
	                continue;
	            }
	            File out = new File(destDir + fileHeader.getFileNameString());
	            if (!out.exists()) {
	                if (!out.getParentFile().exists()) {
	                    out.getParentFile().mkdirs();
	                }
	                out.createNewFile();
	            }
	            FileOutputStream os = new FileOutputStream(out);
	            archive.extractFile(fileHeader, os);

	            os.close();

	            fileHeader = archive.nextFileHeader();
	        }
	        archive.close();
	    } 
	   
	   /**
	    * 解压缩
	    * @param sourceFile	文件路径
	    * @param destDir	目标路径
	    * @throws Exception
	    */
	   public static void deCompress(String sourceFile,String destDir) throws Exception{    
	       //保证文件夹路径最后是"/"或者"\"    
	       char lastChar = destDir.charAt(destDir.length()-1);    
	       if(lastChar!='/'&&lastChar!='\\'){    
	           destDir += File.separator;    
	       }    
	       //根据类型，进行相应的解压缩    
	       String type = sourceFile.substring(sourceFile.lastIndexOf(".")+1);    
	       if(type.equals("zip")){    
	    	   ZipAndRarUtil.unzip(sourceFile, destDir);    
	        }else if(type.equals("rar")){    
	        	ZipAndRarUtil.unrar(sourceFile, destDir);    
	        }else{    
	            throw new Exception("只支持zip和rar格式的压缩包！");    
	        }    
	    }    
	   
	   /**
	    * 解压缩
	    * @param fileType	文件类型
	    * @param srcFile	文件
	    * @param destDir	目标路径
	    * @throws Exception
	    */
	   public static void deCompress(String fileType,File srcFile,String destDir) throws Exception{    
	       //保证文件夹路径最后是"/"或者"\"    
	       char lastChar = destDir.charAt(destDir.length()-1);    
	       if(lastChar!='/'&&lastChar!='\\'){    
	           destDir += File.separator;    
	       }    
	       //根据类型，进行相应的解压缩    
	       if("zip".equals(fileType)){    
	    	   ZipAndRarUtil.unzip(srcFile, destDir);    
	        }else if("rar".equals(fileType)){    
	        	ZipAndRarUtil.unrar(srcFile, destDir);    
	        }else{    
	            throw new Exception("只支持zip和rar格式的压缩包！");    
	        }    
	    }  
	   
	   public static void main(String[] args) throws Exception {
		   ZipAndRarUtil.deCompress("D:\\Calculator.zip","D:\\Calculator");
		   ZipAndRarUtil.deCompress("D:\\hello.rar","D:\\hello");
	   }
}
