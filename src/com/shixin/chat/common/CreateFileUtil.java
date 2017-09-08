package com.shixin.chat.common;

import java.io.File;
import java.io.IOException;

public class CreateFileUtil {
	 public static boolean createFile(String destFileName) {  
	        File file = new File(destFileName);  
	        if(file.exists()) {  
	            return false;  
	        }  
	        if (destFileName.endsWith(File.separator)) {  
	            return false;  
	        }  

	        if(!file.getParentFile().exists()) {  


	            if(!file.getParentFile().mkdirs()) {  
	                return false;  
	            }  
	        }  
	        try {  
	            if (file.createNewFile()) {  
	                return true;  
	            } else {  
	                return false;  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            return false;  
	        }  
	    }  
	     
	     
	    public static boolean createDir(String destDirName) {  
	        File dir = new File(destDirName);  
	        if (dir.exists()) {  
	            return false;  
	        }  
	        if (!destDirName.endsWith(File.separator)) {  
	            destDirName = destDirName + File.separator;  
	        }  
	        if (dir.mkdirs()) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	    }  
	     
	     
	    public static String createTempFile(String prefix, String suffix, String dirName) {  
	        File tempFile = null;  
	        if (dirName == null) {  
	            try{  
	                tempFile = File.createTempFile(prefix, suffix);  
	                return tempFile.getCanonicalPath();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	                return null;  
	            }  
	        } else {  
	            File dir = new File(dirName);  
	            if (!dir.exists()) {  
	                if (!CreateFileUtil.createDir(dirName)) {  
	                    return null;  
	                }  
	            }  
	            try {  
	                tempFile = File.createTempFile(prefix, suffix, dir);  
	                return tempFile.getCanonicalPath();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	                return null;  
	            }  
	        }  
	    }  
	     
	
}
