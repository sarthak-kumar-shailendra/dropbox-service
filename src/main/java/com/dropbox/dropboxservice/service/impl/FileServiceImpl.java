package com.dropbox.dropboxservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.dropboxservice.dto.FileData;
import com.dropbox.dropboxservice.dto.FileFetchResponse;
import com.dropbox.dropboxservice.dto.FileUpdateResponse;
import com.dropbox.dropboxservice.dto.FileUploadReqBody;
import com.dropbox.dropboxservice.manager.FileCreator;
import com.dropbox.dropboxservice.model.Status;
import com.dropbox.dropboxservice.repositories.FileRepository;
import com.dropbox.dropboxservice.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileCreator pdfCreator;

    public FileData saveFile(MultipartFile file, String payload) {
        try{
            FileData fileData = new FileData();
            fileData.setFileName(file.getOriginalFilename());
            fileData.setType(file.getContentType());
            fileData.setSize(file.getSize());
            fileData.setFilePath(payload);

            FileData fileDataDB = fileRepository.save(fileData);

            pdfCreator.storeFile(file, fileDataDB.getId(), payload);

            return fileDataDB;
        }
        catch(Exception ex){
            logger.error("Error in saving file", ex.getMessage());
            return null;
        }
    }

    public FileFetchResponse getFile(String id) {
        try {
            
            Optional<FileData> fileData =  fileRepository.findById(id);
            FileFetchResponse fileFetchResponse = new FileFetchResponse();
            if(fileData.isPresent()){
                FileData fileDataDB = fileData.get();
                String filePathByUser = fileData.get().getFilePath();
                byte[] fileContent=FileCreator.fetchFile(id, filePathByUser);
                if(fileContent == null){
                    return null;
                }
                fileFetchResponse.setStatus(Status.SUCCESS);
                fileFetchResponse.setFileName(fileDataDB.getFileName());
                fileFetchResponse.setContent(fileContent);
                fileFetchResponse.setFileType(fileDataDB.getType());
            }
            else{
                fileFetchResponse.setStatus(Status.NOT_FOUND);
            }
            return fileFetchResponse;

        } catch (Exception ex) {
            logger.error("Error in fetching file", ex.getMessage());
            return null;
        }
    }

    public List<FileData> getAllFiles() {
        return fileRepository.findAll();
    }

    public List<FileData> getAllChilds(String folder){
        Integer len=folder.length();
        if(folder.charAt(len-1)=='/'){
            String newFolder =folder.substring(0,folder.length()-1);
            return fileRepository.findByFilePathStartingWith(newFolder);
        }
        return fileRepository.findByFilePathStartingWith(folder);
    }

    public Status deleteFile(String fileId) {
         try {
            Optional<FileData> fileData =  fileRepository.findById(fileId);

            if(fileData.isPresent()){
                fileRepository.delete(fileData.get());
                boolean isDeleted = FileCreator.deleteFile(fileId);
                if(isDeleted) return Status.SUCCESS;
                return Status.FAILURE;
            }
            else{
                return Status.NOT_FOUND;
            }

        } catch (Exception ex) {
            logger.error("Error in deleting file", ex.getMessage());
            return Status.FAILURE;
        }
    }

    public FileUpdateResponse updateFile(FileData fileData) {
        FileUpdateResponse fileUpdateResponse = new FileUpdateResponse();
        try{
            Optional<FileData> fileDataDB =  fileRepository.findById(fileData.getId());
            
            if(fileDataDB.isPresent()){

                if(fileData.getFileName() == null){
                    fileData.setFileName(fileDataDB.get().getFileName());
                }
                
                fileData.setSize(fileDataDB.get().getSize());
                fileData.setType(fileDataDB.get().getType());
                fileData.setCreatedAt(fileDataDB.get().getCreatedAt());

                fileRepository.save(fileData);
                
                fileUpdateResponse.setFileData(fileData);
                fileUpdateResponse.setStatus(Status.SUCCESS);
                return fileUpdateResponse;
            }
            else{
                fileUpdateResponse.setStatus(Status.NOT_FOUND);
                return fileUpdateResponse;
            }
        } catch(Exception ex){
            logger.error("Error in updating file", ex.getMessage());
            fileUpdateResponse.setStatus(Status.FAILURE);
            return fileUpdateResponse;
        }
    }

}
