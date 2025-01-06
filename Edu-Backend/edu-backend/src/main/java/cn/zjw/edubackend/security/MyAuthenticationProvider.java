package cn.zjw.edubackend.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    //自定义密码验证
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if (password == null)
            throw new BadCredentialsException("密码不能为空");

        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null)
            throw new BadCredentialsException("用户名不存在");
        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("密码错误");

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
