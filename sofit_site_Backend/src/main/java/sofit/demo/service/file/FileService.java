package sofit.demo.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class FileService {
    
    @Value("${spring.file.dir}")
    private String fileDir;

    public String save(MultipartFile multipartFile) {

        int extIdx = multipartFile.getOriginalFilename().lastIndexOf(".");
        String extension = multipartFile.getOriginalFilename().substring(extIdx + 1);

        String filePath = fileDir + UUID.randomUUID() + "." + extension;

        try { 
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 저장 실패!");
        }

        return filePath;
    }

    public void delete(String filePath) {
        File file = new File(filePath);

        if(!file.exists()) return;

        if(!file.delete()) throw new IllegalArgumentException("파일 삭제 실패!");
    }
}
