package com.dropbox.dropboxservice.manager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.io.BufferedWriter;
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

    private final static String filePath = "/Users/sarthakkumar/Documents/dropbox-service/src/storage/";

    public void storeFile(MultipartFile file, String fileId, String filePathByUser) {

        try{
            File rootDir = File.listRoots()[0];
            
            File dir = new File(new File(new File(new File(new File(new File(new File(rootDir, "Users"), "sarthakkumar"),  "Documents"),  "dropbox-service"),  "src"), "storage"), filePathByUser);
            if (!dir.exists()){
                dir.mkdirs();
            }
            //Path path1 = Paths.get(filePathByUser);
            //Files.createDirectories(path1.getParent());
            //File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
            File path = new File(dir.getAbsolutePath() + "/" + 
                    fileId + ".txt");
            //path.getParentFile().mkdirs(); 
            path.createNewFile();

            System.out.println(dir.getAbsolutePath());

            FileOutputStream output = new FileOutputStream(path);
           
            output.write(file.getBytes());
            output.close();

            logger.info("File created with name " + fileId + ".txt");

        }
        catch (Exception ex){
            logger.error("Error in storing file" + ex.getMessage());
        }
    }
 public static byte[] fetchFile(String fileId, String filePathByUser) {

        try{
            File path = ResourceUtils.getFile(filePath + filePathByUser + "/" +  
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
