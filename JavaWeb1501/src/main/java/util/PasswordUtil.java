package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

	/**
	 * パスワードをSHA-256でハッシュ化する
	 *
	 * @param password 平文パスワード
	 * @return SHA-256でハッシュ化した文字列
	 * 
	 *  String inputPasswordHash = PasswordUtil.hashPassword(password);
	 *  このようにすれば、ハッシュ化された「password」が、inputPasswordHashに格納される。
	 * 
	 */
	public static String hashPassword(String password) {

		try {
			// SHA-256を利用するMessageDigestを取得
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			// パスワードをバイト配列に変換してハッシュ化
			byte[] hashBytes = messageDigest.digest(
					password.getBytes(StandardCharsets.UTF_8));

			// バイト配列を16進数の文字列に変換
			StringBuilder hashString = new StringBuilder();

			for (byte hashByte : hashBytes) {
				hashString.append(
						String.format("%02x", hashByte));
			}

			return hashString.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
					"SHA-256を使用できません", e);
		}
	}

	public static void main(String[] args) {

		String password = "pass";

		String passwordHash = PasswordUtil.hashPassword(password);

		System.out.println("平文パスワード:");
		System.out.println(password);

		System.out.println("ハッシュ化後:");
		System.out.println(passwordHash);
	}
}