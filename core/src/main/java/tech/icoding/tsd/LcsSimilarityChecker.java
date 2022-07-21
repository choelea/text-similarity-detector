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
        float score = (float)totalLcsLength / totalLength;
        return (score > 1f)? 1f : score;
    }


    /**
     * 返回两个段落句子N*M对比后的最长公共子串的列表。
     * @param left
     * @param right
     * @return
     */
    public List<CharSequence> longestCommonSubStr(CharSequence left, CharSequence right){
        if(left == null || right == null ){
            throw  new IllegalArgumentException("parameters can not be null");
        }
        final List<String> leftSentences = sentenceBreaker.toSentenceList(left);
        final List<String> rightSentences = sentenceBreaker.toSentenceList(right);
        return longestCommonSubStr(leftSentences, rightSentences);
    }

    /**
     * 返回两个句子列表N*M对比后的最长公共子串的列表。 列表的大小 <= leftSentences.size()。
     * 可能存在重复的字符串，但是在用于计算分值更准确。
     * @param leftSentences
     * @param rightSentences
     * @return
     */
    private List<CharSequence> longestCommonSubStr(final List<String> leftSentences, final List<String> rightSentences){
        List<CharSequence> list = new ArrayList<>();

        String leftSentence;
        String rightSentence;
        for (int i = 0; i < leftSentences.size(); i++) {
            leftSentence = leftSentences.get(i);
            Integer maxLength = 0;
            CharSequence longestSubStr = null;
            for (int j = 0; j < rightSentences.size(); j++) {
                rightSentence = rightSentences.get(j);
                final CharSequence charSequence = longestCommonSubstr.longestCommonSubstring(leftSentence, rightSentence);
                final Integer length = charSequence.length();
                if(maxLength < length && length >= minLength){
                    maxLength = length;
                    longestSubStr = charSequence;
                }
            }
            if(longestSubStr != null){
                list.add(longestSubStr);
            }
        }

//        去掉重复字符串
//        Map<Integer, CharSequence> hashMap = new HashMap<>();
//        list.forEach(sequence -> {
//            hashMap.put(sequence.hashCode(), sequence);
//        });
        return list;
    }
    /**
     * 返回两个段落句子N*M对比后的最长公共子串的列表。并根据结果列表的字符串的长度倒序排列。
     * @param left
     * @param right
     * @return
     */
    public List<CharSequence> sortedLongestCommonSubstr(CharSequence left, CharSequence right){
        final List<CharSequence> charSequences = longestCommonSubStr(left, right);
        Collections.sort(charSequences, new Comparator<CharSequence>() {
            @Override
            public int compare(CharSequence o1, CharSequence o2) {
                return o2.length() - o1.length();
            }
        });
        return charSequences;
    }
}
