package com.drl.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.drl.config.EncryptionDescriptionService;
import com.drl.config.JwtUtil;
import com.drl.entities.FramesRights;
import com.drl.entities.Token;
import com.drl.entities.User;
import com.drl.repositry.FramesRightsRepository;
import com.drl.repositry.TokenRepository;
import com.drl.repositry.UserRepository;
import com.drl.utils.AuthenticationResponse;
import com.drl.utils.Utils;

import ch.qos.logback.classic.pattern.Util;
import jakarta.transaction.Transactional;

@Service
public class AuthenticateService {
	@Autowired
	TokenRepository tokenRepository;
	@Autowired
	private JwtUtil jwtService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FramesRightsRepository frameRepository;

	public ResponseEntity<Object> authenticate(User user) throws Exception {
		String pass = "qwerty";
		System.out.println(EncryptionDescriptionService.encrypt(pass));
		String password = EncryptionDescriptionService.decrypt(user.getPassword());
		user.setTxtPassword(password);

		Optional<User> existingOptional = userRepository.findByTxtUserName(user.getUsername());
		if (!existingOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("User with username '" + user.getUsername() + "' does not exist");
		}
		var existingUser = existingOptional.get();
		if (existingUser.getBlnStatus() == null || !existingUser.getBlnStatus()) {
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("User '" + user.getUsername() + "' is not active");
		}
		String encryptedInputPassword = getEncryptedString(user.getPassword());
		// Compare with stored password
		if (!encryptedInputPassword.equals(existingUser.getPassword())) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid password for user '" + user.getUsername() + "'");
		}
		Map<String, Object> claims = new HashMap<>();
		List<FramesRights> frameRights = frameRepository.getFrameRight(existingUser.getSerUserId());
		/*
		 * if(frameRights == null || frameRights.size()==0 ) { return ResponseEntity
		 * .status(HttpStatus.UNAUTHORIZED)
		 * .body("You are not allowed to use the form please contact to Administrator");
		 * }
		 */

		for (FramesRights rights : frameRights) {
			rights.setCreatedDate(null);
			rights.setModifiedDate(null);
			claims.put(frameRepository.getFormName(rights.getFormId()), rights);

		}
		var jwtToken = jwtService.generateToken(existingUser, claims);
		var refreshToken = jwtService.generateRefreshToken(user, claims);
		var authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setAccessToken(jwtToken);
		authenticationResponse.setRefreshToken(refreshToken);
		revokeAllUserTokens(existingUser);
		saveUserToken(existingUser, jwtToken);
		authenticationResponse.setAccessToken(jwtToken);
		authenticationResponse.setRefreshToken(refreshToken);
		authenticationResponse.setClaims(claims);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(authenticationResponse);

	}

	/**
	 * Generates an MD5 hash of the input string and returns it as a hexadecimal
	 * string.
	 *
	 * <p>
	 * <b>Note:</b> MD5 is considered cryptographically insecure for password
	 * hashing or
	 * sensitive data protection. Use stronger algorithms like SHA-256 or bcrypt for
	 * such cases.
	 * </p>
	 *
	 * @param toEncrypt the input string to be hashed
	 * @return the MD5 hash of the input string as a hexadecimal representation
	 * @throws RuntimeException if the MD5 algorithm is not available
	 */

	public final String getEncryptedString(String toEncrypt) {
		String tmp = toEncrypt;
		try {
			byte[] defaultBytes = tmp.getBytes();
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			String foo = messageDigest.toString();

			// com.drl.DRLConsoleHRServer.print("\n USER's PASSWORD IS :: " +
			// hexString.toString());

			return hexString.toString();

			// sessionid=hexString+"";
		} catch (NoSuchAlgorithmException nsae) {
			return null;
		}
	}

	private void revokeAllUserTokens(User user) {
		try {
			tokenRepository.deleteByUserId(user.getSerUserId());
		} catch (Exception e) {
			System.out.println("empty tokens" + e);
		}
	}

	public void saveUserToken(User user, String jwtToken) {
		var token = new Token();
		token.setUserId(user.getSerUserId());
		token.setToken(jwtToken);
		token.setExpired(false);
		token.setRevoked(false);
		tokenRepository.saveAndFlush(token);
	}

	public ResponseEntity<Object> createUser(User user) throws Exception{
			Optional<User> existingOptional  = userRepository.findByTxtUserName(user.getUsername());		
			if (existingOptional.isPresent()) {
		        return ResponseEntity
		                .status(HttpStatus.NOT_FOUND)
		                .body("User name already exist");
		    }
			try {
			LocalDateTime now = LocalDateTime.now();		
		    String encryptedInputPassword = getEncryptedString(user.getPassword());
		    user.setTxtPassword(encryptedInputPassword);
		    user.setBlnStatus(false);
		    user.setSerCreatedUserId(Utils.getCurrentUser().getSerUserId());
		    user.setDteCreatedDate(now);
		    user.setSerGroupId(39);
		    user.setBlnUserStatus(false);
		    User savedUSer = userRepository.save(user);
		    return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(savedUSer);
			}
			catch(Exception e) {
				return ResponseEntity
		                .status(HttpStatus.NOT_FOUND)
		                .body("Internal Server Error");
			}
			
		}

	public List<User> getAllUsers() {
		// Fetch all users from the repository
		return userRepository.findAll();
	}

}