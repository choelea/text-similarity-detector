package tech.icoding.tsd;

import java.util.*;

/**
 * 返回长度大于minLength的公共子字符串列表。
 * @author : Joe
 * @date : 2022/7/1
 */
public class CommonSubstrings implements SimilarityScore<Integer> {

    /**
     * 最小长度，只有满足最小长度的公共子字符串才会被选中并返回
     */
    private int minLength;

    public CommonSubstrings(int minLength) {
        this.minLength = minLength;
    }

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
        final List<CharSequence> charSequences = commonSubstrings(left, right);
        int totalLength = 0;
        for (int i = 0; i < charSequences.size(); i++) {
            totalLength += charSequences.get(i).length();
        }
        return totalLength;
    }


    /**
     * 通过矩阵
     * @param left
     * @param right
     * @return
     */
    public List<CharSequence> commonSubstrings(final CharSequence left, final CharSequence right) {
        Map<String, Position> positions = new HashMap<>();

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
                    int value = lcsLengthArray[i][j] + 1;
                    lcsLengthArray[i + 1][j + 1] = value;
                    if(value ==1 ){
                        positions.put(i+"-"+j, new Position(i, j, 0));
                    }else{
                        positions.get((i+1-value)+"-"+(j+1-value)).length = value;
                    }
                } else {
                    lcsLengthArray[i + 1][j + 1] = 0;
                }
            }
        }


        final Position[] positionArr = positions.values().toArray(new Position[positions.size()]);

        Arrays.sort(positionArr, new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return o2.length - o1.length;
            }
        });


        List<CharSequence> charSequences = new ArrayList<>();
        for (int i = 0; i < positionArr.length; i++) {
            Position position = positionArr[i];
            charSequences.add(left.subSequence(position.i, position.i + position.length));
        }

        return charSequences;
    }



    static class Position{
        int i;
        int j;
        int length = 0;

        public Position(int i, int j, int length) {
            this.i = i;
            this.j = j;
            this.length = length;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return i == position.i &&
                    j == position.j &&
                    length == position.length;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j, length);
        }
    }


}
