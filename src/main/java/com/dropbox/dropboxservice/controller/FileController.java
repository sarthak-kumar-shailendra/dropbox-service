package com.dropbox.dropboxservice.controller;

import com.dropbox.dropboxservice.dto.FileData;
import com.dropbox.dropboxservice.dto.FileFetchResponse;
import com.dropbox.dropboxservice.dto.FileUpdateResponse;
import com.dropbox.dropboxservice.dto.FileUploadResponse;
import com.dropbox.dropboxservice.model.Status;
import com.dropbox.dropboxservice.service.FileService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/files")
public class FileController {
    
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")

    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
            
        FileData fileDataDB = fileService.saveFile(file);
        if(fileDataDB == null){
            return new ResponseEntity<Object>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setMessage("File Uploaded Successfully");
        fileUploadResponse.setFileId(fileDataDB.getId());
        return new ResponseEntity<Object>(fileUploadResponse, HttpStatus.CREATED);
    }


    @GetMapping("/{fileId}")
    public ResponseEntity<Object> getFile(@PathVariable String fileId){
        FileFetchResponse file = fileService.getFile(fileId);
        if(file == null){
            return new ResponseEntity<Object>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            if(file.getStatus()==Status.NOT_FOUND){
                return new ResponseEntity<Object>("File Not Found", HttpStatus.NOT_FOUND);
            }
            else if(file.getStatus()==Status.SUCCESS){
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
                responseHeaders.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*,Authorization,File-Type");
                return ResponseEntity.ok()
                        .headers(responseHeaders)
                        .body(file.getContent());
            }
            else 
                return new ResponseEntity<Object>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<Object> putFile(@PathVariable String fileId, @RequestBody FileData fileData) {
        fileData.setId(fileId);
        FileUpdateResponse fileUpdateResponse = fileService.updateFile(fileData);
        if(fileUpdateResponse.getStatus() == Status.NOT_FOUND)
            return new ResponseEntity<Object>("File Not Found", HttpStatus.NOT_FOUND);
        
        else if(fileUpdateResponse.getStatus() == Status.SUCCESS){
            return new ResponseEntity<Object>(fileUpdateResponse.getFileData(), HttpStatus.OK);
        }
        return new ResponseEntity<Object>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Object> deleteFile(@PathVariable String fileId){
        Status status = fileService.deleteFile(fileId);
        if(status == Status.SUCCESS){
            return new ResponseEntity<Object>("File Deleted Successfully", HttpStatus.OK);
        }
        else if(status == Status.NOT_FOUND){
            return new ResponseEntity<Object>("File Not Found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllFiles() {
        List<FileData> allFiles = fileService.getAllFiles();
        return new ResponseEntity<Object>(allFiles, HttpStatus.OK);
    }

}
