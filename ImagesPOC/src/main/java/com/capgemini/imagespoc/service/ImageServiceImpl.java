package com.capgemini.imagespoc.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.capgemini.imagespoc.controller.ImageController;
import com.capgemini.imagespoc.dto.Image;
import com.capgemini.imagespoc.dto.ImageList;
import com.capgemini.imagespoc.dto.ImageProcessingTask;
import com.capgemini.imagespoc.helper.ImageConverter;

@Service
public class ImageServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	// private static final Integer threadCount = 5;

	@Autowired
	ImageConverter imageConverter;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public byte[] getImagesFromResource() throws IOException {
		String[] files = { "img1.jpeg", "img2.jpeg" };
		byte[] output = imageConverter.zipFiles(files);
		return output;
	}

	
	public byte[] getImagesFromInternet(String keyword) throws IOException, MalformedURLException {
		ImageList images = retrieveImageFromSplash(keyword);
		if (null != images && !CollectionUtils.isEmpty(images.getImages())) {
			logger.info("Image count based on keyword is" + images.getImages().size());
			// Iterate these objects using java8 streams
			// Call the url present in url attribute
			images.getImages().stream().forEach(img -> {
				invokeImage(img.getUrl(), img.getId());
			});
			byte[] outputFromDirectory = imageConverter.zipFiles();
			return outputFromDirectory;
		} else {
			logger.info("No images available");
		}
		return null;
	}

	// Optimized Images from Internet

	public byte[] getImagesFromInternetOptimized(String keyword) throws IOException, MalformedURLException, Exception {
		ImageList images = retrieveImageFromSplash(keyword);
		if (null != images && !CollectionUtils.isEmpty(images.getImages())) {
			int count = images.getImages().size();
			logger.info("Image count based on keyword is" + count);
			// int threadCount = Runtime.getRuntime().availableProcessors() + 1;
			logger.info("Number of Processors : " + Runtime.getRuntime().availableProcessors());
			int threadCount = Runtime.getRuntime().availableProcessors();
			ExecutorService es = Executors.newFixedThreadPool(threadCount);
			List<Future<Map<Integer, Integer>>> futures = new ArrayList<Future<Map<Integer, Integer>>>();
			int minItemsPerThread = count / threadCount;
			int maxItemsPerThread = minItemsPerThread + 1;
			int threadsWithMaxItems = count - threadCount * minItemsPerThread;
			int start = 0;
			for (int i = 0; i < threadCount; i++) {
				int itemsCount = (i < threadsWithMaxItems ? maxItemsPerThread : minItemsPerThread);
				int end = start + itemsCount;
				ImageProcessingTask imgTask = new ImageProcessingTask(this, images.getImages().subList(start, end));
				futures.add(es.submit(imgTask));
				start = end;
			}
			for (Future<Map<Integer, Integer>> f : futures) {
				f.get();
			}
			logger.info("all items processed");
			byte[] outputFromDirectory = imageConverter.zipFiles();
			return outputFromDirectory;
		} else {
			logger.info("No images available");
		}
		return null;
	}

	public byte[] getRequiredImagesFromInternet(String keyword, int count) throws IOException, MalformedURLException {
		ImageList images = retrieveImageFromSplash(keyword);
		if (null != images && !CollectionUtils.isEmpty(images.getImages())) {

			logger.info("Total images available :" + images.getImages().size());
			logger.info("Required images by user  :" + count);

			if (images.getImages().size() <= count) {
				logger.info("Images are available but lesser than required");
				images.getImages().stream().forEach(img -> {
					invokeImage(img.getUrl(), img.getId());
				});

			} else {
				logger.info("More than required images are loaded...needs processing");
				images.getImages().stream().limit(count).forEach(img -> {
					invokeImage(img.getUrl(), img.getId());
				});
			}
			byte[] outputFromDirectory = imageConverter.zipFiles();
			return outputFromDirectory;
		} else {
			logger.info("No images available");
		}
		return null;
	}
	
	public ImageList retrieveImageFromSplash(String keyword) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceURL = "http://www.splashbase.co/api/v1/images/search?query={queryparam}";
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("queryparam", keyword);
		return restTemplate.getForObject(resourceURL, ImageList.class, urlVariables);
	}

	public Integer invokeImage(String imageUrl, int id) {
		logger.info("Inside Invoke Image method...");
		logger.info("url of image :" + imageUrl);
		logger.info("Id of image :" + id);
		try {
			URL url = new URL(imageUrl);
			BufferedImage img = ImageIO.read(url);
			File file = new File("D://artifacts/" + "File_" + id + ".jpg");
			ImageIO.write(img, "jpg", file);
			logger.info("Image file written to stream...");
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
}
