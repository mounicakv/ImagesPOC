package com.capgemini.imagespoc.controller;


import java.util.Calendar;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.imagespoc.service.ImageServiceImpl ;


/**
 * Handles requests for the Images service.
 */
@RestController /*(value="/images")*/
public class ImageController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	private ImageServiceImpl imageService;
	 
	@RequestMapping(value = "/images/retrieveImagesFromResource", method = RequestMethod.GET, produces="application/zip")
	public void getAllImages(HttpServletResponse response) throws Exception{
		logger.info("Start getAllImages");
		byte[] images = null;
		images =  imageService.getImagesFromResource();
		ServletOutputStream sos = response.getOutputStream();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=DATA.ZIP");
        sos.write(images);
        sos.flush();
	}
	
	@RequestMapping(value = "/images/retrieveImagesFromInternet", method = RequestMethod.GET, produces="application/zip")
	public void getAllImagesFromInternet(@RequestParam String keyword, HttpServletResponse response) throws Exception{
		long entryTime = Calendar.getInstance().getTimeInMillis();
		logger.info("getAllImagesFromInternet");
		byte[] images = null;
		logger.info("Keyword passed in input" + keyword);
		images =  imageService.getImagesFromInternet(keyword);
		ServletOutputStream sos = response.getOutputStream();
		if(null != images) {
	    response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=DATA.ZIP");
        sos.write(images);
        sos.flush();
		}else {
			response.setContentType("application/octet-stream");
			byte[] b = {'n', 'o', 'i','m', 'a', 'g','e', 's'};
		    sos.write(b);
	        sos.flush();
		}
        long totalTime = Calendar.getInstance().getTimeInMillis() - entryTime;
        logger.info("Total time taken to retrieve images from internet, process and send it back to user is " + totalTime + " ms");
	}
	
	
	@RequestMapping(value = "/images/retrieveImagesfromInternetOptimized", method = RequestMethod.GET, produces="application/zip")
	public void getAllImagesFromInternetOptimized(@RequestParam String keyword, HttpServletResponse response) throws Exception{
		long entryTime = Calendar.getInstance().getTimeInMillis();
		logger.info("getAllImagesFromInternetOptimized");
		byte[] images = null;
		logger.info("Keyword passed in input" + keyword);
		images =  imageService.getImagesFromInternetOptimized(keyword);
		ServletOutputStream sos = response.getOutputStream();
		if(null != images) {
	    response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=DATA.ZIP");
        sos.write(images);
        sos.flush();
		}else {
			response.setContentType("application/octet-stream");
			byte[] b = {'n', 'o', 'i','m', 'a', 'g','e', 's'};
		    sos.write(b);
	        sos.flush();
		}
        long totalTime = Calendar.getInstance().getTimeInMillis() - entryTime;
        logger.info("Total time taken to retrieve images from internet in an optimized way, process and send it back to user is" + totalTime + " ms");
	}
	
	@RequestMapping(value = "/images/retrieveCountableImagesFromInternet/count/{countID}", method = RequestMethod.GET, produces="application/zip")
	public void getRequiredImagesFromInternet(@RequestParam String keyword,@PathVariable int countID, HttpServletResponse response) throws Exception{
		logger.info("getAllImagesFromInternet");
		byte[] images = null;
		logger.info("Keyword passed in input " + keyword);
		logger.info("Count of images to be retrieved :"+ countID);
		
		images =  imageService.getRequiredImagesFromInternet(keyword,countID);
		ServletOutputStream sos = response.getOutputStream();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=DATA.ZIP");
        sos.write(images);
        sos.flush();
	}

	
	
}
	