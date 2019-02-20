package com.mallall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by qcc on 2/20/2019.
 */
public interface IFileService {

    String upload(MultipartFile file,String path);
}
