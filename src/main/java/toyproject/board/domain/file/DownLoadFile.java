package toyproject.board.domain.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DownLoadFile {

    private String fileName;
    private MultipartFile multipartFile;
}
