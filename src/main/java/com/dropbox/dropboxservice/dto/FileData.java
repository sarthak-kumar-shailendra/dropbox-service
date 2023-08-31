package com.dropbox.dropboxservice.dto;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Document(collection = "fileData")
@NoArgsConstructor
@AllArgsConstructor
public class FileData {
    @Id
    String id;
    //String fileId;
    @jakarta.validation.constraints.NotBlank
    String fileName;
    
    String type;
    Long size;

    // @Lob
    // byte[] data;
    
    @CreatedDate
    Date createdAt; // UTC

    @LastModifiedDate
    Date updatedAt;
}
