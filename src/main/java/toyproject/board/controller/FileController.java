package toyproject.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import toyproject.board.domain.file.DownLoadFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class FileController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String uploadFileForm() {
        return "file/upload-form";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam String fileName, @RequestParam MultipartFile multipartFiles[])
            throws IOException {
        // 파일 여러 개를 업로드 하려면 HTML form 의 input name 이 배열 형식으로 들어가야 한다.
        // multiple 로 설정을 해도 그냥 file 하나를 받는 것으로 해서 적용되지 않았다.
        for (MultipartFile file : multipartFiles) {
            if (!file.isEmpty()) {
                String fullPath = fileDir + file.getOriginalFilename();
                file.transferTo(new File(fullPath));
            }
        }
        return "file/upload-form";
    }

    @GetMapping("/download")
    public String downloadFileForm() {
        return "file/download-form";
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> selectDownloadFile(@ModelAttribute DownLoadFile downLoadFile)
            throws MalformedURLException {

        MultipartFile file = downLoadFile.getMultipartFile();
        // 지정한 url 안에 있는 파일만 다운로드 받을 수 있다.
        UrlResource resource = new UrlResource("file:" + fileDir + file.getOriginalFilename());

        String encodedUploadFileName = UriUtils.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
