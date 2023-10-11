package cnu.healthcare.global.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    private String memberName;

    public CustomUser(String username, String password
            , Collection<? extends GrantedAuthority> authorities, String memberName) {
        super(username, password, authorities);
        this.memberName = memberName;
    }

    public String getMemberName() {
        return memberName;
    }
}
