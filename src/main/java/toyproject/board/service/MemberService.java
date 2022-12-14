package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.embeddable.Address;
import toyproject.board.domain.member.Member;
import toyproject.board.dto.member.MemberDeleteDto;
import toyproject.board.dto.member.MemberLoginDto;
import toyproject.board.dto.member.MemberRechargeDto;
import toyproject.board.dto.member.MemberSignupDto;
import toyproject.board.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    public Optional<Member> findByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member;
    }

    public Member findById(Long id) {
        Member member = memberRepository.findById(id).get();
        return member;
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    public Member getMemberBySignupDto(MemberSignupDto memberSignupDto) {

        Address address = makeAddressFromMemberSignupDto(memberSignupDto);

        Member member = Member.builder()
                .email(memberSignupDto.getEmail())
                .username(memberSignupDto.getUsername())
                .password(memberSignupDto.getPassword())
                .localDateTime(LocalDateTime.now())
                .address(address)
                .build();

        return member;
    }

    public Address makeAddressFromMemberSignupDto(MemberSignupDto memberSignupDto) {
        String address = memberSignupDto.getAddress();
        String[] addressList = address.split(" ", 3);
        if (addressList.length < 3) {
            return null;
        }
        String country = addressList[0];
        String city = addressList[1];
        String apartment = addressList[2];

        return new Address(country, city, apartment);
    }
}
