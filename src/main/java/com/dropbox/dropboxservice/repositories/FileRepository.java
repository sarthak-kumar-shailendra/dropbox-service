package com.dropbox.dropboxservice.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dropbox.dropboxservice.dto.FileData;

public interface FileRepository extends MongoRepository<FileData, String> {
    //@Query(value = "{ 'filePath': ?0 }")
    List<FileData> findByFilePathStartingWith(String filePath);
}

