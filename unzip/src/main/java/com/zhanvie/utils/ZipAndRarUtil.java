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
	 * ��ѹ��zip�ļ� 
	 * @param sourceZip	zip�ļ�·��
	 * @param destDir	Ŀ��·��
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
		           ant�µ�zip����Ĭ��ѹ������ΪUTF-8���룬  
			           ��winRAR���ѹ�����õ�windowsĬ�ϵ�GBK����GB2312����  
			           ���Խ�ѹ��ʱҪ�ƶ������ʽ  
	           */   
	           e.setEncoding("GBK");    
	           e.execute();    
	       }catch(Exception e){    
	           throw e;    
	       }    
	   }    
	   
	   /**
	    * ��ѹ��zip�ļ�
	    * @param srcFile	zip�ļ�
	    * @param destDir	Ŀ��·��
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
	    * ��ѹ��rar�ļ�
	    * @param sourceRar	rar�ļ�·��
	    * @param destDir	Ŀ��·��
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
	    * ��ѹ��rar�ļ�
	    * @param srcFile	rar�ļ�
	    * @param destDir	Ŀ��·��
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
	    * ��ѹ��
	    * @param sourceFile	�ļ�·��
	    * @param destDir	Ŀ��·��
	    * @throws Exception
	    */
	   public static void deCompress(String sourceFile,String destDir) throws Exception{    
	       //��֤�ļ���·�������"/"����"\"    
	       char lastChar = destDir.charAt(destDir.length()-1);    
	       if(lastChar!='/'&&lastChar!='\\'){    
	           destDir += File.separator;    
	       }    
	       //�������ͣ�������Ӧ�Ľ�ѹ��    
	       String type = sourceFile.substring(sourceFile.lastIndexOf(".")+1);    
	       if(type.equals("zip")){    
	    	   ZipAndRarUtil.unzip(sourceFile, destDir);    
	        }else if(type.equals("rar")){    
	        	ZipAndRarUtil.unrar(sourceFile, destDir);    
	        }else{    
	            throw new Exception("ֻ֧��zip��rar��ʽ��ѹ������");    
	        }    
	    }    
	   
	   /**
	    * ��ѹ��
	    * @param fileType	�ļ�����
	    * @param srcFile	�ļ�
	    * @param destDir	Ŀ��·��
	    * @throws Exception
	    */
	   public static void deCompress(String fileType,File srcFile,String destDir) throws Exception{    
	       //��֤�ļ���·�������"/"����"\"    
	       char lastChar = destDir.charAt(destDir.length()-1);    
	       if(lastChar!='/'&&lastChar!='\\'){    
	           destDir += File.separator;    
	       }    
	       //�������ͣ�������Ӧ�Ľ�ѹ��    
	       if("zip".equals(fileType)){    
	    	   ZipAndRarUtil.unzip(srcFile, destDir);    
	        }else if("rar".equals(fileType)){    
	        	ZipAndRarUtil.unrar(srcFile, destDir);    
	        }else{    
	            throw new Exception("ֻ֧��zip��rar��ʽ��ѹ������");    
	        }    
	    }  
	   
	   public static void main(String[] args) throws Exception {
		   ZipAndRarUtil.deCompress("D:\\Calculator.zip","D:\\Calculator");
		   ZipAndRarUtil.deCompress("D:\\hello.rar","D:\\hello");
	   }
}
