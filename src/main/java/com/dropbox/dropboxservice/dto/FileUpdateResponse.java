package com.dropbox.dropboxservice.dto;

import com.dropbox.dropboxservice.model.Status;

import lombok.Data;

@Data
public class FileUpdateResponse {
    FileData fileData;
    Status status;
}
