package heero.mc.mod.wakcraft.util;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil {
	public static List<String> warpString(String input, int maxLineLength) {
		StringTokenizer tokens = new StringTokenizer(input, " ");
		StringBuilder subString = new StringBuilder(maxLineLength);
		List<String> output = new LinkedList<>();

		int lineLength = 0;
		while (tokens.hasMoreTokens()) {
			String word = tokens.nextToken();

			if (lineLength + word.length() > maxLineLength) {
				output.add(subString.toString());
				subString.setLength(0);
				lineLength = 0;
			}

			subString.append(word);
			subString.append(" ");
			lineLength += word.length();
		}

		output.add(subString.toString());

		return output;
	}
}
