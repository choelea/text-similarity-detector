package tech.icoding.tsd;

import java.util.*;

/**
 * 公共子字符串比较，列出并比较所有的公共子字符串，而不仅仅是最长的那个。
 * TODO 还未开发完成
 * @author : Joe
 * @date : 2022/7/1
 */
public class CssSimilarityChecker implements SimilarityScore<Float> {

    private CommonSubstrings commonSubstrings = new CommonSubstrings(3);
    private SentenceBreaker sentenceBreaker;

    public CssSimilarityChecker(SentenceBreaker sentenceBreaker) {
        this.sentenceBreaker = sentenceBreaker;
    }

    @Override
    public Float apply(CharSequence left, CharSequence right) {
        return 0f;
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
                final List<CharSequence> charSequences = commonSubstrings.commonSubstrings(leftSentence, rightSentence);
                for (CharSequence charSequence:charSequences) {
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
