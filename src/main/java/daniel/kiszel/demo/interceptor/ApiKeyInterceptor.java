package daniel.kiszel.demo.interceptor;

import daniel.kiszel.demo.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;


@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Autowired
    ClientService clientService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("api-key");

        //null check
        if(apiKey == null){
            return false;
        }

        if (!isValidApiKey(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Invalid API Key\"}");
            return false;
        }
        return true;
    }
    private Boolean isValidApiKey(String apiKey) {
        //null check
        if(apiKey == null){
            return false;
        }

        UUID uuidAPiKey = UUID.fromString(apiKey);
        if (!clientService.isApiKeyValid(uuidAPiKey)) {
            return false;
        }
        return true; // API key is valid
    }
}