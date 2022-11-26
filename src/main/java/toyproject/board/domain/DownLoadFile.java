package toyproject.board.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DownLoadFile {

    private String fileName;
    private MultipartFile multipartFile;
}
