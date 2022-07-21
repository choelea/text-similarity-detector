package tech.icoding.tsd;

import java.util.*;

/**
 * 采用LongestCommonSubstr 计算段落的相似度。
 * 计算段落之间的相似度，优先进行段落的拆分，将所有的段落拆分成一个一个的句子，然后进行N*M的比较。 N代表左边句子数量，M代表右边句子数量。
 * 比较得到N*M的矩阵结果，针对左边每一句，取出最大分值作为汇总计算总的相似度分值。 总的相似度:（maxScore(N)/N）
 * @author : Joe
 * @date : 2022/7/1
 */
public class LcsSimilarityChecker implements SimilarityScore<Float> {

    private LongestCommonSubstr longestCommonSubstr = new LongestCommonSubstr();
    private SentenceBreaker sentenceBreaker;
    private int minLength;
    public LcsSimilarityChecker(SentenceBreaker sentenceBreaker, int minLength) {
        this.sentenceBreaker = sentenceBreaker;
        this.minLength = minLength;
    }

    /**
     *
     * @param left the first Paragraph
     * @param right the second Paragraph
     * @return
     */
    @Override
    public Float apply(CharSequence left, CharSequence right) {
        if(left == null || right == null ){
            throw  new IllegalArgumentException("parameters can not be null");
        }
        final List<String> leftSentences = sentenceBreaker.toSentenceList(left);
        final List<String> rightSentences = sentenceBreaker.toSentenceList(right);


        final Collection<CharSequence> charSequences = longestCommonSubStr(leftSentences, rightSentences);
        int totalLcsLength = charSequences.stream().mapToInt(CharSequence::length).sum();

        int totalLength = 0;
        for (int i = 0; i < leftSentences.size(); i++) {
            totalLength += leftSentences.get(i).length();
        }
        return (float)totalLcsLength / totalLength;


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
        final List<String> leftSentences = sentenceBreaker.toSentenceList(left);
        final List<String> rightSentences = sentenceBreaker.toSentenceList(right);
        return longestCommonSubStr(leftSentences, rightSentences);
    }

    /**
     * 返回两个句子列表N*M对比后的最长公共子串的列表。
     * @param leftSentences
     * @param rightSentences
     * @return
     */
    private Collection<CharSequence> longestCommonSubStr(final List<String> leftSentences, final List<String> rightSentences){
        HashMap<Integer, CharSequence> hashMap = new HashMap<>();
        leftSentences.forEach(leftSentence -> {
            rightSentences.forEach(rightSentence -> {
                final CharSequence charSequence = longestCommonSubstr.longestCommonSubstring(leftSentence, rightSentence);
                if(charSequence.length() >= minLength) {
                    hashMap.put(charSequence.hashCode(), charSequence);
                }
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
