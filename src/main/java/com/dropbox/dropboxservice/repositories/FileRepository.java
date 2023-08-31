package com.dropbox.dropboxservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dropbox.dropboxservice.dto.FileData;

public interface FileRepository extends MongoRepository<FileData, String> {
    // @Query(value = "{ 'id': ?0 }")
    // FileData findByFileId(String fileId);
}

