package com.dropbox.dropboxservice.dto;

import com.dropbox.dropboxservice.model.Status;

import lombok.Data;

@Data   
public class FileFetchResponse {
    Status status;
    String fileName;
    byte[] content;
    String fileType;
}
