package cnu.healthcare.global.security.jwt;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import cnu.healthcare.domain.member.Member;
import cnu.healthcare.domain.member.repo.MemberRepository;
import cnu.healthcare.global.security.auth.PrincipalDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
    private final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final MemberRepository memberRepository;

    private final JwtConfig jwtConfig;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  MemberRepository adminRepository,
                                  JwtConfig jwtConfig) {
        super(authenticationManager);
        this.memberRepository = adminRepository;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(jwtConfig.getHeaderString());
        System.out.println("header is ... ->" + header);
        if(header == null || !header.startsWith(jwtConfig.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader(jwtConfig.getHeaderString())
                .replace(jwtConfig.getTokenPrefix(), "");

        System.out.println("token is ... ->" + token);
        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
        String username = JWT.require(Algorithm.HMAC512(jwtConfig.getSecret())).build().verify(token)
                .getClaim("id").asString();
        System.out.println("username is ... ->" + username);

        if(username != null) {
            try{
                Member user = memberRepository.findByMemberId(username);

                // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
                // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                                null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                                principalDetails.getAuthorities());
                System.out.println("권한 확인" + principalDetails.getAuthorities());
                System.out.println("권한 확인" + authentication);

                // 강제로 시큐리티의 세션에 접근하여 값 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception exception) {
                logger.error("An error occurred during token verification: {} ", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        }

        chain.doFilter(request, response);
    }

}