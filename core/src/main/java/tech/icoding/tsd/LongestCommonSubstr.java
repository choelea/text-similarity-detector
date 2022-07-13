package tech.icoding.tsd;

/**
 * A similarity algorithm indicating the length of the longest common substring between two strings.
 *
 * For LongestCommonSubSequence, please refer to apache commons-text. This class is copy from there with a little bit change.
 * @author : Joe
 * @date : 2022/7/1
 */
public class LongestCommonSubstr implements SimilarityScore<Integer> {


    /**
     * Calculates longest common substring similarity score of two {@code CharSequence}'s passed as
     * input.
     *
     * @param left first character sequence
     * @param right second character sequence
     * @return The longest common substring length
     * @throws IllegalArgumentException
     *             if either String input {@code null}
     */
    @Override
    public Integer apply(final CharSequence left, final CharSequence right) {
        // Quick return for invalid inputs
        if (left == null || right == null) {
            throw new IllegalArgumentException("Inputs must not be null");
        }
//        return Float.intBitsToFloat(1);
        return longestCommonSubstring(left, right).length();
    }


    /**
     * @param left
     * @param right
     * @return
     */
    public CharSequence longestCommonSubstring(final CharSequence left, final CharSequence right) {
        int max = 0;
        int p = 0; // 最长匹配对应left中的最后一位
        final int[][] lcsLengthArray = new int[left.length() + 1][right.length() + 1];
        for (int i = 0; i < left.length(); i++) {
            for (int j = 0; j < right.length(); j++) {
                if (i == 0) {
                    lcsLengthArray[i][j] = 0;
                }
                if (j == 0) {
                    lcsLengthArray[i][j] = 0;
                }
                if (left.charAt(i) == right.charAt(j)) {
                    lcsLengthArray[i + 1][j + 1] = lcsLengthArray[i][j] + 1;
                    if(lcsLengthArray[i + 1][j + 1] > max){
                        max = lcsLengthArray[i + 1][j + 1];
                        p = i+1;
                    }
                } else {
                    lcsLengthArray[i + 1][j + 1] = 0;
                }
            }
        }
        return left.subSequence(p-max, p);
    }

}
