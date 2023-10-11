package cnu.healthcare.global.security;

import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    private final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

;
    @Override
    public CustomUser loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 id의 회원은 DB에 존재하지 않음"));
        logger.info("멤버가 존재함: {} ", member.getMemberId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        member.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        });

        return new CustomUser(member.getMemberId(), member.getPassword(), authorities
                , member.getMemberName());
    }
}
