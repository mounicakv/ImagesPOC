package com.capgemini.imagespoc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ImageService {

	public List<Image> getImages(){
		List<Image> images = new ArrayList<Image>();
		Image img1 = new Image();
		img1.setId(1);
		Image img2 = new Image();
		img2.setId(2);
		images.add(img1);
		images.add(img2);
		return images;
	}
}
