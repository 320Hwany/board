package toyproject.board.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    public void fileUpload(MultipartFile[] multipartFiles) throws IOException {
        for (MultipartFile file : multipartFiles) {
            if (!file.isEmpty()) {
                String fullPath = fileDir + file.getOriginalFilename();
                file.transferTo(new File(fullPath));
            }
        }
    }
}
