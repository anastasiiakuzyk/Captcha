package ua.kpi.security.console;

public class Vigenere {
    public static String crypt(String text, final String key) {
        String res = "";
        text = text.toLowerCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'a' || c > 'z')
                continue;
            res += (char) ((c + key.charAt(j) - 2 * 'a') % 26 + 'a');
            j = ++j % key.length();
        }
        return res;
    }
}
