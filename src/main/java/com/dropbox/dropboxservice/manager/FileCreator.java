package com.dropbox.dropboxservice.manager;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
@PropertySource("classpath:application.properties")
public class FileCreator {

    private final static Logger logger = LoggerFactory.getLogger(FileCreator.class);

    private final static String filePath = "/Users/sarthakkumar/Downloads/dropbox-service/src/storage/";

    public void storeFile(MultipartFile file, String fileId) {

        try{
            File path = new File(filePath + 
                    fileId + ".txt");
            path.createNewFile();
            
            FileOutputStream output = new FileOutputStream(path);
           
            output.write(file.getBytes());
            output.close();

            logger.info("File created with name " + fileId + ".txt");

        }
        catch (Exception ex){
            logger.error("Error in storing file" + ex.getMessage());
        }
    }
 public static byte[] fetchFile(String fileId) {

        try{
            File path = ResourceUtils.getFile(filePath + 
                    fileId + ".txt");
            
            byte[] fileContent = Files.readAllBytes(path.toPath());

            return fileContent;
            
        }
        catch (Exception ex){
            logger.error("Error in fetching file" + ex.getMessage());
            return null;
        }
    }


    public static boolean deleteFile(String fileId) throws Exception {
        try{
            return Files.deleteIfExists(
                Paths.get(filePath + fileId + ".txt"));
        }
        catch (Exception ex){
            logger.error("Error in deleting file" + ex.getMessage());
            return false;
        }
    }

}
