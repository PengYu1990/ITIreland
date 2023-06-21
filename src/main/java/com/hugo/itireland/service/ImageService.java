package com.hugo.itireland.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public String upload(String username, MultipartFile file, int type) throws IOException;
    public byte[] get(String username, String imageName);
}
