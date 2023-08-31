package com.dropbox.dropboxservice.dto;

import lombok.Data;

@Data
public class FileUploadResponse {
    String message;
    String fileId;
}
