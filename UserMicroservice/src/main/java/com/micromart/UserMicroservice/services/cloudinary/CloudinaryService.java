package com.micromart.UserMicroservice.services.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public Map<?,?> upload(MultipartFile file)throws Exception {
        Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder","user-avatars"
        ));
        System.out.println(uploadResult.toString());
        return uploadResult;

    }

    public void deleteImage(String publicId) throws IOException {
        System.out.println("DELETE IMAGE with publicId: " + publicId);
        Map<?,?> data = cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
        System.out.println(data.toString());
    }


}
