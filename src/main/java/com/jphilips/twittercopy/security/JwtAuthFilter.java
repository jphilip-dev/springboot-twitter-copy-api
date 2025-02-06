package com.jphilips.twittercopy.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.jphilips.twittercopy.service.JwtService;
import com.jphilips.twittercopy.service.MyUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private JwtService jwtService;
	
	private MyUserService myUserService;
	
	
	private HandlerExceptionResolver resolver;
	
	public JwtAuthFilter(JwtService jwtService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,MyUserService myUserService) {
		this.jwtService = jwtService;
		this.resolver = resolver;
		this.myUserService = myUserService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = extractToken(request);
		
		// Skip token check for public end points (login, register)
	    if (request.getRequestURI().startsWith("/api/auth")) {
	        filterChain.doFilter(request, response);
	        return; 
	    }

		if (token == null) {
			// throw error
			handleException(request, response, new JwtException("Missing Token"));
			return;
		}
		
		try {
			Claims claims = jwtService.getClaims(token);
			
			String username = claims.getSubject();
			
			UserDetails userDetails = myUserService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					username, userDetails.getPassword(), userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		} catch (Exception e) {
			
			// Exception will filtered in the GlobalExceptionHandler
			handleException(request, response,  e);
			
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	// helper methods

	private String extractToken(HttpServletRequest request) {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7); // Remove the "Bearer " prefix
		}

		return null;
	}
	
	private void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
	    resolver.resolveException(request, response, null, ex);
	}

}
