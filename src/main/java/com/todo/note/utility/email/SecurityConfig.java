package com.todo.note.utility.email;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.todo.note.userservice.model.RegistrationModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityConfig {

	static final String KEY = "sowjanya";

	public String createToken(String id) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
		Date date = new Date();

		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(date).signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	public Claims parseJwt(String jwt) {
		return  Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();

	}
	public String createTokens(RegistrationModel user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// Long id=user.getUserId();
		String subject = user.getEmailId();
		String issuer = user.getUserName();
		Date date = new Date();
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(date).setIssuer(issuer)
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}
}
