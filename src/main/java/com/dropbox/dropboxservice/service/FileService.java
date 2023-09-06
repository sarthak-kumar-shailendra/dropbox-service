package com.dropbox.dropboxservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.dropboxservice.dto.FileData;
import com.dropbox.dropboxservice.dto.FileFetchResponse;
import com.dropbox.dropboxservice.dto.FileUpdateResponse;
import com.dropbox.dropboxservice.dto.FileUploadReqBody;
import com.dropbox.dropboxservice.model.Status;

@Service
public interface FileService {
    public FileData saveFile(MultipartFile file, String payload);
    public FileFetchResponse getFile(String id);
    public List<FileData> getAllFiles();
    public Status deleteFile(String fileId);
    public FileUpdateResponse updateFile(FileData fileData);
    public List<FileData> getAllChilds(String folder);
}
