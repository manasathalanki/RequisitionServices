package com.wissen.recruit.core.jwt;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.wissen.recruit.core.entity.User;
import com.wissen.recruit.core.repository.RefreshTokenRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtHelper {

	@Autowired
	RefreshTokenRepository refreshTokenRepository;
	
	static final String ISSUER = "RecurIT";

	private long accessTokenExpiresInMs;
	private long refreshTokenExpiresInMs;

	private Algorithm accessTokenAlgo;
	private Algorithm refreshTokenAlgo;
	private JWTVerifier accessTokenJwtVerifier;
	private JWTVerifier refreshTokenJwtVerifier;

	public JwtHelper(@Value("${accessTokenSecret}") String accessTokenSecret,
			@Value("${refreshTokenSecret}") String refreshTokenSecret, @Value("${accessTokenExpiresInMins}") long accessTokenExpiresInMins,
			@Value("${refreshTokenExpiresInDays}") long refreshTokenExpiresInDays) {

		accessTokenExpiresInMs = accessTokenExpiresInMins * 60 * 1000;
		refreshTokenExpiresInMs =  refreshTokenExpiresInDays * 24 * 60 * 60 * 1000;

		accessTokenAlgo = Algorithm.HMAC512(accessTokenSecret);
		refreshTokenAlgo = Algorithm.HMAC512(refreshTokenSecret);

		accessTokenJwtVerifier = JWT.require(accessTokenAlgo).withIssuer(ISSUER).build();
		refreshTokenJwtVerifier = JWT.require(refreshTokenAlgo).withIssuer(ISSUER).build();
	}

	public String generateAccessToken(User user,Integer refreshTokenId) {
		log.info("Generating Access Token using RefreshTokenID");
		return JWT.create().withIssuer(ISSUER)
				.withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.withClaim("refreshTokenId", String.valueOf(refreshTokenId))
				.withSubject(user.getUsername())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(new Date().getTime() + accessTokenExpiresInMs))
				.sign(accessTokenAlgo);
	}

	public String generateRefreshToken(User user) {
		log.info("Generating Refresh Token using UserId and Username");
		return JWT.create().withIssuer(ISSUER)
				.withClaim("tokenId",user.getId())
				.withSubject(user.getUsername())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(new Date().getTime() + refreshTokenExpiresInMs))
				.sign(refreshTokenAlgo);
	}

	public Optional<DecodedJWT> decodeAccessToken(String token) {
		try {
			return Optional.of(accessTokenJwtVerifier.verify(token));
		} catch (JWTVerificationException e) {
			log.error("Access Token Expried or not valid...");
		}
		
		return Optional.empty();

	}
	
	public Optional<DecodedJWT> decodeRefreshToken(String token) {
		try {
			return Optional.of(refreshTokenJwtVerifier.verify(token));
		} catch (JWTVerificationException e) {
			log.error("Refresh Token Expired or not valid...");
		}
		
		return Optional.empty();

	}
	
	public boolean validateAccessToken(String token) {
		log.info("Checking AccessToken with RefreshTokenId stored in DB");
		String refreshTokenId = extractTokenIdFromAccessToken(token);
		if(decodeAccessToken(token).isPresent() && 
				refreshTokenRepository.findById(Integer.valueOf(refreshTokenId)).isPresent()) {
			log.info("Valid Access Token.RefreshTokenId Matches with DB.");
			return true;
		} else {
			log.error("Invalid Access Token.RefreshTokenId Not Matches with DB.");
			return false;
		}
	}
	
	public boolean validateRefreshToken(String token) {
		return decodeRefreshToken(token).isPresent();
	}
	
	public String extractUsernameFromAccessToken(String token) {
		
		Optional<DecodedJWT>  decodedJwt= decodeAccessToken(token);
		if(decodedJwt.isPresent()) {
			return decodedJwt.get().getSubject();
		}
		
		return null;
		
	}
	
	public String extractUsernameFromRefreshToken(String token) {
		Optional<DecodedJWT>  decodedJwt= decodeRefreshToken(token);
		if(decodedJwt.isPresent()) {
			return decodedJwt.get().getSubject();
		}
		
		return null;
	}

	public String extractTokenIdFromAccessToken(String token) {
		Optional<DecodedJWT>  decodedJwt= decodeAccessToken(token);
		if(decodedJwt.isPresent()) {
			return decodedJwt.get().getClaim("refreshTokenId").asString();
		}
		
		return null;
	}
	
	public String extractTokenIdFromRefreshToken(String token) {
		Optional<DecodedJWT>  decodedJwt= decodeRefreshToken(token);
		if(decodedJwt.isPresent()) {
			return decodedJwt.get().getClaim("tokenId").asString();
		}
		return null;
	}
}
