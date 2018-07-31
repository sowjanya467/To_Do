package com.todo.note.securityservice.jwt;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.todo.note.userservice.model.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component("tokens")
public class JwtTokens  
{
	final static String KEY = "sowjanya";

	/**
	 * purpose: to create a token
	 * 
	 * @param user
	 * @return token
	 */
	public String createToken(User user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		String subject = user.get_id();
		String issuer = user.getEmailId();
		Date date = new Date();
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(date).setIssuer(issuer)
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	/**
	 * purpose: Decoding the token(to get details of user)
	 * 
	 * @param Jwt
	 * @return 
	 */
	public Claims parseJwt(String jwt) {
		return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();

	}

}
