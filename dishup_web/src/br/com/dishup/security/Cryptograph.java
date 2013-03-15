package br.com.dishup.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import br.com.dishup.exception.EncryptException;

/**************************
 * @since 24/02/2013 
 * @author Lucas Biccio Ribeiro
 * @version 1.0 Class responsible for encapsulate the autentication and cryptographic logic
 **************************/
public class Cryptograph {
	
	/*********************
	 * A type of algorithm used to cipher.
	 *********************/
	private static final String cipherAlgorithm = "SHA-512";
	
	/*********************
	 * A type of algorithm used to cipher.
	 *********************/
	private static final String encondingType = "UTF-8";
	
	/*********************
	 * Method responsible for encode an string into base64 type enconding.
	 * @param var - String to be encoded.
	 * @return String encoded into base64 type.
	 *********************/
	private static String encodeBase64 (String var){
		byte[] encoded = Base64.encodeBase64(var.getBytes());
		return new String(encoded);
	}
	
	/*********************
	 * Method responsible for decode an string from base64 type enconding to the original format.
	 * @param var - String encoded at base64 type enconding.
	 * @return Original String format.
	 *********************/
	/*
	public static String decodeBase64 (String var){
		byte[] decoded = Base64.decodeBase64(var.getBytes());
		return new String(decoded);
	}
	*/
	 
	/********************
	 * Method responsible for cipher and var string using an especific algorithm into an array of bytes.
	 * @param var - string to be cipher.
	 * @return string ciphered.
	 * @throws NoSuchAlgorithmException - when the algorithm is not implemented.
	 * @throws UnsupportedEncodingException - when the type of encoding is not supported.
	 ********************/
	private static byte[] cipher(String var) throws NoSuchAlgorithmException, UnsupportedEncodingException{
			MessageDigest md = MessageDigest.getInstance(cipherAlgorithm);
			byte digested[] = md.digest(var.getBytes(encondingType));
			return digested;
	}
	
	/********************
	 * Method responsible to convert an array of bytes to an hexadecimal format.
	 * @param bytes - array of bytes.
	 * @return bytes converted to hexadecimal.
	 ********************/
	private static String convertByteToHexa(byte[] bytes){
		StringBuilder hexString = new StringBuilder();
		for(byte b : bytes)
			hexString.append(String.format("%02X", 0xFF & b));
		return hexString.toString();
	}
	
	/********************
	 * Method responsible for encrypt the user's password.
	 * @param password - user's password.
	 * @return password encrypted.
	 * @throws EncryptException.
	 ********************/
	public static String encrypt(String password) throws EncryptException{
		try{
			byte[] cipher = cipher(password);
			String hexa = convertByteToHexa(cipher);
			return encodeBase64(hexa);
		}catch (NoSuchAlgorithmException e){
			throw new EncryptException(e.getMessage());
		}catch (UnsupportedEncodingException e){
			throw new EncryptException(e.getMessage());
		}catch (Exception e) {
			throw new EncryptException(e.getMessage());
		}
	}
}
