package utils;

public class StringUtils {

    private StringUtils() {
    }

    public static int getEditDistanceBetweenWords(String s1, String s2, int m, int n) {
        if (m == 0) {
            return n;
        }
        if (n == 0) {
            return m;
        }
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return getEditDistanceBetweenWords(s1, s2, m - 1, n - 1);
        }
        int distanceMovingFirstEnd = getEditDistanceBetweenWords(s1, s2, m - 1, n);
        int distanceRemovingSecondEnd = getEditDistanceBetweenWords(s1, s2, m, n - 1);
        int distanceMovingBothEnds = getEditDistanceBetweenWords(s1, s2, m - 1, n - 1);
        int tempMin = Math.min(distanceMovingFirstEnd, distanceRemovingSecondEnd);
        return 1 + Math.min(distanceMovingBothEnds, tempMin);
    }
}
