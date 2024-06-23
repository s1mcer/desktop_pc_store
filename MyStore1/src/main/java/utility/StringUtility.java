package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.SimpleDateFormat;

public class StringUtility {
	public static String capitalizeWords(String str) {
		Pattern pattern = Pattern.compile("\\b[a-zA-Z]");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group().toUpperCase());
		}
		matcher.appendTail(sb);

		return sb.toString();
	}

	public static String getFirstWord(String input) {
		return input.split(",")[0];
	}

	public static String formatDate(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}

}
