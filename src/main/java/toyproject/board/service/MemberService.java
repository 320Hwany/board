package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Member;
import toyproject.board.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(Member member) {

        validateDuplicateMember(member);

        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByUsername(member.getUsername())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    public Member findByUsername(String username) {
        Member member = memberRepository.findByUsername(username).get();
        return member;
    }

    public Member findById(Long id) {
        Member member = memberRepository.findById(id).get();
        return member;
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
