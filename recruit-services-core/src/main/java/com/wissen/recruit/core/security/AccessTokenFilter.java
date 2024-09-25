package com.wissen.recruit.core.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.jwt.JwtHelper;
import com.wissen.recruit.core.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AccessTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			Optional<String> accessToken = parseAccessToken(request);
			if(accessToken.isPresent() && jwtHelper.validateAccessToken(accessToken.get())) {
				String username = jwtHelper.extractUsernameFromAccessToken(accessToken.get());
				User user = userService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(user,null,Collections.emptyList());
				upa.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upa);
			}
		}catch(Exception e) {
			log.error("Cannot set authenticate...",e);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private Optional<String> parseAccessToken(HttpServletRequest request){
		String authHeader = request.getHeader("Authorization");
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return Optional.of(authHeader.substring(7)) ;
		}	
		return Optional.empty();
	}

}
