package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.board.domain.Member;
import toyproject.board.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(Member member) {

        Member saveMember = memberRepository.save(member);
        return saveMember;
    }


    public Member findById(Long id) {
        Member member = memberRepository.findById(id).get();
        return member;
    }
}
