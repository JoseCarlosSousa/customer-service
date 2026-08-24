package pt.kkosmico.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    // 🌟 Must match exactly the secret key phrase used in your user-service TokenRefresher
    private final String secretPhrase = "myUltraSecretKeyForJwtTokenGeneration2026!!!";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretPhrase.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = this.recoverToken(request);
        
        if (token != null) {
            try {
                // 1. Decode and parse token payload claims securely using recent JJWT fluent API
                var payload = Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String login = payload.getSubject(); 
                Object roleClaim = payload.get("role");
                String role = (roleClaim != null) ? roleClaim.toString() : "USER";
                
                if (login != null) {
                	if (role.trim().isEmpty()) {
                        role = "USER"; // Default fallback metadata safety check
                    }
                    
                    // 2. Create authority context using matching standard Spring security syntax
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);
                    
                    // 3. Inject verified identity session mappings directly inside core security application context
                    var authentication = new UsernamePasswordAuthenticationToken(login, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Fail silently to continue filter loop validation without breaking core engine pipelines
                logger.error("Failed to process security authorization filter chain", e);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
