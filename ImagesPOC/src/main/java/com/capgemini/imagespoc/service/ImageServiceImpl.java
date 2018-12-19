package com.capgemini.imagespoc.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.capgemini.imagespoc.controller.ImageController;
import com.capgemini.imagespoc.dto.Image;
import com.capgemini.imagespoc.dto.ImageList;
import com.capgemini.imagespoc.helper.ImageConverter;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	ImageConverter imageConverter;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public byte[] getImagesFromResource() throws IOException{
		String[] files = {"img1.jpeg", "img2.jpeg"};
		byte[] output =imageConverter.zipFiles(files);
		return output;
	}
	
	public byte[] getImagesFromInternet(String keyword) throws IOException,MalformedURLException{
		RestTemplate restTemplate = new RestTemplate();
		String resourceURL
		  = "http://www.splashbase.co/api/v1/images/search?query={queryparam}";
		Map<String,String> urlVariables = new HashMap<String, String>();
		urlVariables.put("queryparam", keyword);
		ImageList images
		  = restTemplate.getForObject(resourceURL, ImageList.class, urlVariables);
		//String responseString = response.getBody();
		if(null != images && !CollectionUtils.isEmpty(images.getImages())) {
	logger.info("Image count based on keyword is" + images.getImages().size());	
			//Iterate these objects using java8 streams
			//Call the url present in url attribute 
			images.getImages().stream().forEach(img -> {
				try {
					invokeImage(img.getUrl(),img.getId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			String[] extensions = {"jpg"};
			List<String> fileNames = new ArrayList<String>();
			File directory = new File("D://artifacts");
			Iterator itr = FileUtils.iterateFiles(directory, extensions, true);
			while(itr.hasNext()) {
				File f = (File)itr.next();
				fileNames.add(f.getName());
			}
			byte[] outputFromDirectory = imageConverter.zipFiles(directory,fileNames);
			FileUtils.cleanDirectory(directory);
			return outputFromDirectory;
		}
		
		else {
			logger.info("No images available");
		}
		
		String[] files = {"img1.jpeg", "img2.jpeg"};
		byte[] output =imageConverter.zipFiles(files);
		return output;
	}
	
	public void invokeImage(String imageUrl,int id) throws IOException{
		logger.info("Inside Invoke Image method...");
		logger.info("url of image :" +imageUrl);
		logger.info("Id of image :"+id);
		URL url = new URL(imageUrl);
		BufferedImage img = ImageIO.read(url);
		File file = new File("D://artifacts/" + "File_"+id+".jpg");
		//file.getParentFile().mkdirs();
		ImageIO.write(img, "jpg", file);
		
		/*String destinationFile=null;
		OutputStream os = new FileOutputStream(destinationFile);
		
		
		 byte[] b = new byte[2048];
		    int length;

		    while ((length = is.read(b)) != -1) {
		        os.write(b, 0, length);
		    }
		      is.close();
		    os.close();
		*/
		logger.info("Image file written to stream...");
		}
	
	
	
}
