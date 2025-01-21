package crm_app.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app.config.Constant;

@WebFilter(filterName = "authenFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    private static final Set<String> EXCLUDED_URLS = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        EXCLUDED_URLS.add("/login");
        EXCLUDED_URLS.add("/login.jsp");
        EXCLUDED_URLS.add("/404.html");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        if (EXCLUDED_URLS.stream().anyMatch(requestURI::contains)) {
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = httpRequest.getCookies();
        String userRole = null;
        String userEmail = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("role".equals(cookie.getName())) {
                    userRole = cookie.getValue();
                }
                if ("email".equals(cookie.getName())) {
                    userEmail = cookie.getValue();
                }
            }
        }

        if (userRole == null || userEmail == null) {
            httpResponse.sendRedirect(contextPath + "/login");
            return;
        }

        boolean hasAccess = false;

        if (Constant.ROLE_ADMIN.equals(userRole)) {
            hasAccess = true;
        } else if ("ROLE_MANAGER".equals(userRole)) {
            Set<String> managerLinks = Set.of("/user-table","/user-add","user-details", "/groupwork", "/profile","/task","task-add");
            hasAccess = managerLinks.stream().anyMatch(requestURI::contains);
        } else if ("ROLE_USER".equals(userRole)) {
            Set<String> userLinks = Set.of("/profile", "/profile-edit");
            hasAccess = userLinks.stream().anyMatch(requestURI::contains);
        }

        if (hasAccess) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(contextPath + "/404.html");
        }
    }

    @Override
    public void destroy() {
        // No additional clean-up required
    }
}
