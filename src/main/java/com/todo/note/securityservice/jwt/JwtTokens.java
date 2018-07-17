package com.todo.note.securityservice.jwt;
import java.util.Date;

import com.todo.note.userservice.model.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

		// Long id=user.getUserId();
		String subject = user.getEmailId();
		String issuer = user.getUserName();
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
	public Claims parseJwt(String Jwt) {
		Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(Jwt).getBody();
		System.out.println("subject-" + claims.getSubject());
		System.out.println("issuer-" + claims.getIssuer());
		System.out.println("date-" + claims.getIssuedAt());
		return claims;

	}

}
