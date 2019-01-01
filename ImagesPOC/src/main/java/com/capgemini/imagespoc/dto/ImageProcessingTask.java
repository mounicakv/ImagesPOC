package com.capgemini.imagespoc.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.capgemini.imagespoc.service.ImageServiceImpl;

public class ImageProcessingTask implements Callable {

	ImageServiceImpl imgService;

	private List<Image> imagesBucket;

	public ImageProcessingTask(ImageServiceImpl imgService, List<Image> imagesBucket) {
		super();
		this.imagesBucket = imagesBucket;
		this.imgService = imgService;
	}

	@Override
	public Map<Integer, Integer> call() {
		Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
		System.out.println("####Images size for this execution is " + imagesBucket.size() );
		if (null != imagesBucket && !imagesBucket.isEmpty()) {
			//final int result;
			imagesBucket.stream().forEach(img -> {
				resultMap.put(img.getId(), imgService.invokeImage(img.getUrl(), img.getId()));
			});
		}
		return resultMap;
	}
}
