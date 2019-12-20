package net.canway.meeting_message.common;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter extends FormAuthenticationFilter {
    private Logger log = LoggerFactory.getLogger(MyFilter.class);

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String url = getPathWithinApplication(request);
        if(url.equals("/login/doLogin")){
            return true;
        }
        Subject subject = getSubject(request, response);
        return subject.getPrincipal() != null;
    }

}
