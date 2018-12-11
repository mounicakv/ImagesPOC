package com.capgemini.imagespoc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests for the Images service.
 */
@RestController
public class ImageController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	@Autowired
	private ImageService imageService;
//	List<byte[]> imageList=new ArrayList(); 

//	imageList=ImageConverter.imageProcessing();
//	
//	List<Image> images=new ArrayList<Image>();
	
	
	
	@RequestMapping(value = "/images/retrieveAll", method = RequestMethod.GET, produces="application/json")
	public List<Image> getAllImages() {
		logger.info("Start getAllImages");
		
		return imageService.getImages();
	}
	
	
	
	/* @RequestMapping(value = "/rest/img/imagebytitle", method = RequestMethod.GET)
	public @ResponseBody ImagesCollage getEmployee(@PathVariable("title") String img_title) {
		//logger.info("Start getImagesCollage. title="+empId);
		
		return imagesList.get(id);
	} */
}
	