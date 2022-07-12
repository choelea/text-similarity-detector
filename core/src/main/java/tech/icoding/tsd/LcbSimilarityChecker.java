package tech.icoding.tsd;

import java.util.*;

/**
 * 采用LongestCommonSubstr 计算段落的相似度。
 * 计算段落之间的相似度，优先进行段落的拆分，将所有的段落拆分成一个一个的句子，然后进行N*M的比较。 N代表左边句子数量，M代表右边句子数量。
 * 比较得到N*M的矩阵结果，针对左边每一句，取出最大分值作为汇总计算总的相似度分值。 总的相似度:（maxScore(N)/N）
 * @author : Joe
 * @date : 2022/7/1
 */
public class LcbSimilarityChecker implements SimilarityScore<Float> {

    private LongestCommonSubstr longestCommonSubstr = new LongestCommonSubstr();
    private SentenceBreaker sentenceBreaker;

    public LcbSimilarityChecker(SentenceBreaker sentenceBreaker) {
        this.sentenceBreaker = sentenceBreaker;
    }

    @Override
    public Float apply(CharSequence left, CharSequence right) {
        if(left == null || right == null ){
            throw  new IllegalArgumentException("parameters can not be null");
        }
        final List<String> leftSentences = sentenceBreaker.toSentenceList(left);
        final List<String> rightSentences = sentenceBreaker.toSentenceList(right);

        float[] maxLeftScore = new float[leftSentences.size()];
        String leftSentence;
        String rightSentence;
        for (int i = 0; i < leftSentences.size(); i++) {
            leftSentence = leftSentences.get(i);
            float maxScore = 0f;
            for (int j = 0; j < rightSentences.size(); j++) {
                rightSentence = rightSentences.get(j);
                final Float score = longestCommonSubstr.apply(leftSentence, rightSentence);
                if(maxScore < score.floatValue()){
                    maxScore = score;
                }
            }
            maxLeftScore[i] = maxScore;
        }

        // 计算平均得分
        float resultScore = 0;
        for (int i = 0; i < maxLeftScore.length; i++) {
            resultScore = resultScore + maxLeftScore[i];
        }
        return resultScore/maxLeftScore.length;
    }


    /**
     * 返回两个段落句子N*M对比后的最长公共子串的列表。
     * @param left
     * @param right
     * @return
     */
    public Collection<CharSequence> longestCommonSubStr(CharSequence left, CharSequence right){
        if(left == null || right == null ){
            throw  new IllegalArgumentException("parameters can not be null");
        }
        HashMap<Integer, CharSequence> hashMap = new HashMap<>();
        final List<String> leftSentences = sentenceBreaker.toSentenceList(left);
        final List<String> rightSentences = sentenceBreaker.toSentenceList(right);
        leftSentences.forEach(leftSentence -> {
            rightSentences.forEach(rightSentence -> {
                final CharSequence charSequence = longestCommonSubstr.longestCommonSubstring(leftSentence, rightSentence);
                hashMap.put(charSequence.hashCode(), charSequence);
            });
        });
        return hashMap.values();
    }

    /**
     * 返回两个段落句子N*M对比后的最长公共子串的列表。并根据结果列表的字符串的长度倒序排列。
     * @param left
     * @param right
     * @return
     */
    public CharSequence[] sortedLongestCommonSubstr(CharSequence left, CharSequence right){
        final Collection<CharSequence> charSequences = longestCommonSubStr(left, right);
        final CharSequence[] css = new CharSequence[charSequences.size()];
        charSequences.toArray(css);
        Arrays.sort(css, new Comparator<CharSequence>() {
            @Override
            public int compare(CharSequence o1, CharSequence o2) {
                return o2.length() - o1.length();
            }
        });
        return css;
    }
}
