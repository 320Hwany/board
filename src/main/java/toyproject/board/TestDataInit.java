package toyproject.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toyproject.board.domain.Member;
import toyproject.board.domain.StorageItem;
import toyproject.board.repository.ItemStorageRepository;
import toyproject.board.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final ItemStorageRepository itemStorageRepository;

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("yhwjd@naver.com", "spring", "4321", null,
                LocalDateTime.now()));
        itemStorageRepository.save(new StorageItem("itemA", 10000, 10));
        itemStorageRepository.save(new StorageItem("itemB", 2000, 50));
        itemStorageRepository.save(new StorageItem("itemC", 5000, 30));
    }
}
