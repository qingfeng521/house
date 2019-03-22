package com.imooc.house.service;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.imooc.house.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.multi.MultiToolTipUI;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class FileService {

    public List<String> getImagPath(List<MultipartFile> files){
        List<String> paths = Lists.newArrayList();
        files.forEach(file->{
            File localFile = null;
            if(!file.isEmpty()){
                try{
                    String  filePath = Constants.FILE_URL;
                    localFile = saveToLocal(file,filePath);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),filePath);
                    paths.add(path);
                }catch(Exception e){
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return paths;
    }

    private File saveToLocal(MultipartFile file, String filePath) throws IOException {
        File newFile = new File(Constants.FILE_URL + "/" + file.getOriginalFilename());
        if(!newFile.exists()){
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(),newFile);
        return newFile;
    }

}
