package com.red.app.provider;

import com.red.model.User;
import com.red.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    @Qualifier("userDetailsService")
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication auth = super.authenticate(authentication);

            //if reach here, means login success, else exception will be thrown
            //reset the user_attempts
            userService.resetFailAttempts(authentication.getName());

            return auth;

        } catch (BadCredentialsException e) {
            //invalid login, update to user_attempts
            try {
                userService.updateFailAttempts(authentication.getName());
            }catch (LockedException lockedException){
                session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, lockedException);
                throw lockedException;
            }
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, e);
            throw e;

        } catch (LockedException e){
            //this user is locked!
            String error = "";
            Optional<User> user = userService.findByEmail(authentication.getName());
            if(user.isPresent()){
                Locale locale = RequestContextUtils.getLocale(request);
                System.out.println(locale);
                LocalDateTime lastAttempts = user.get().getUpdatedAt();
                String last = lastAttempts.toString();
                String name = authentication.getName();
                error = messageSource.getMessage("auth.lockByUser", new Object[]{name, last}, locale);// The account or password is incorrect.
            }else{
                error = e.getMessage();
            }

            LockedException lockedException = new LockedException(error);
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, lockedException);
            throw lockedException;
        }catch (AuthenticationException authenticationException){
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, authenticationException);
            throw authenticationException;
        }
    }
}
