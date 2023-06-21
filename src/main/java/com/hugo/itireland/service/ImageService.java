package com.hugo.itireland.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public String upload(Long userId, MultipartFile file, int type) throws IOException;
    public byte[] get(Long userId, String imageName);
}
