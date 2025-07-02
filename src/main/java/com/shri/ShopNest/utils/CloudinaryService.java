package com.shri.ShopNest.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shri.ShopNest.config.properties.CloudinaryProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;
    private final CloudinaryProperties cloudinaryProperties;

    public CloudinaryService(Cloudinary cloudinary, CloudinaryProperties cloudinaryProperties) {
        this.cloudinary = cloudinary;
        this.cloudinaryProperties = cloudinaryProperties;
    }

    public String upload(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", cloudinaryProperties.getBaseFolder()
        ));
        return uploadResult.get("secure_url").toString();
    }
}
