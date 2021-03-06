package com.samadhi.project_wso2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SaltedHashFunction {
	public static String[] getHashedPassword(String password) throws Exception {
		String[] saltedHashArray = new String[2];
		String data = password;
		String algorithm = "SHA-256";
		byte[] salt = createSalt();
		saltedHashArray[0] = generateHash(data, algorithm, salt);
		saltedHashArray[1] = bytesToStringHex(salt);
		return saltedHashArray;
	}

	public static String getHashedGivenSalt(String password, String salt) throws Exception {
		String data = password;
		String algorithm = "SHA-256";
		String saltedHash = generateHash(data, algorithm, hexStringToByteArray(salt));
		return saltedHash;
	}

	private static String generateHash(String data, String alogrithm, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(alogrithm);
		digest.reset();
		digest.update(salt);
		byte[] hash = digest.digest(data.getBytes());
		return bytesToStringHex(hash);
	}

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToStringHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static byte[] createSalt() {
		byte[] bytes = new byte[10];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}
}
