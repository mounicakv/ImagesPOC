package com.capgemini.imagespoc.controller;



import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.imagespoc.service.ImageServiceImpl ;


/**
 * Handles requests for the Images service.
 */
@RestController
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
		logger.info("getAllImagesFromInternet");
		byte[] images = null;
		logger.info("Keyword passed in input" + keyword);
		images =  imageService.getImagesFromInternet(keyword);
		ServletOutputStream sos = response.getOutputStream();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=DATA.ZIP");
        sos.write(images);
        sos.flush();
	}
}
	