package cnu.healthcare.global.security.auth;

import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // System.out.println("PrincipalDetailsService : 진입");
        Member member = memberRepository.findByMemberId(username);

        // session.setAttribute("loginUser", user);
        return new PrincipalDetails(member);
    }
}