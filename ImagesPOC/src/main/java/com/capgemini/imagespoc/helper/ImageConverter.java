package com.capgemini.imagespoc.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
	
  /* public byte[] getImageInBytes(File file) throws IOException {
	 	return FileUtils.readFileToByteArray(file);
   }
   
   /**
    * Zip the files which are available as part of source files
    */
   public byte[] zipFiles(String[] files) throws IOException {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       ZipOutputStream zos = new ZipOutputStream(baos);
       byte bytes[] = new byte[2048];
       for (String fileName : files) {
       	File f = new ClassPathResource(fileName).getFile();
           FileInputStream fis = new FileInputStream(f);
           BufferedInputStream bis = new BufferedInputStream(fis);
           zos.putNextEntry(new ZipEntry(fileName));
           int bytesRead;
           while ((bytesRead = bis.read(bytes)) != -1) {
               zos.write(bytes, 0, bytesRead);
           }
           zos.closeEntry();
           bis.close();
           fis.close();
       }
       zos.flush();
       baos.flush();
       zos.close();
       baos.close();
       return baos.toByteArray();
   }
   
   public byte[] zipFiles() throws IOException {
		String[] extensions = { "jpg" };
		List<String> fileNames = new ArrayList<String>();
		File directory = new File("D://artifacts");
		Iterator itr = FileUtils.iterateFiles(directory, extensions, true);
		while (itr.hasNext()) {
			File f = (File) itr.next();
			fileNames.add(f.getName());
		}
		byte[] outputFromDirectory = zipFiles(directory, fileNames);
		FileUtils.cleanDirectory(directory);
		return outputFromDirectory;
	}
   
   /**
    * Zip all the files present in particular directory
    * @param directory
    * @param files
    * @return
    * @throws IOException
    */
   public byte[] zipFiles(File directory, List<String> files) throws IOException {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       ZipOutputStream zos = new ZipOutputStream(baos);
       byte bytes[] = new byte[2048];
       for (String fileName : files) {
           FileInputStream fis = new FileInputStream(directory.getPath() + 
        		   File.separator + fileName);
           BufferedInputStream bis = new BufferedInputStream(fis);
           zos.putNextEntry(new ZipEntry(fileName));
           int bytesRead;
           while ((bytesRead = bis.read(bytes)) != -1) {
               zos.write(bytes, 0, bytesRead);
           }
           zos.closeEntry();
           bis.close();
           fis.close();
       }
       zos.flush();
       baos.flush();
       zos.close();
       baos.close();
       return baos.toByteArray();
   }
}
