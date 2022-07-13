package tech.icoding.tsd;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author : Joe
 * @date : 2022/7/1
 */
public class DefaultSentenceBreaker implements SentenceBreaker{

    /**
     * 最小长度，小于这个长度的句子会被忽略
     */
    private int minLength = 6;

    /**
     * 是否切割为最细的单位（将逗号也视作分隔符）
     */
    private boolean shortest = false;

    public DefaultSentenceBreaker(){

    }
    public DefaultSentenceBreaker(int minLength, boolean shortest) {
        this.minLength = minLength;
        this.shortest = shortest;
    }

    @Override
    public List<String> toSentenceList(CharSequence content) {
        return toSentenceList(content,shortest);
    }

    /**
     * 文本分句
     *
     * @param chars  文本
     * @param shortest 是否切割为最细的单位（将逗号也视作分隔符）
     * @return
     */
    public List<String> toSentenceList(CharSequence chars, boolean shortest)
    {

        StringBuilder sb = new StringBuilder();

        List<String> sentences = new LinkedList<String>();

        for (int i = 0; i < chars.length(); ++i)
        {
            if (sb.length() == 0 && (Character.isWhitespace(chars.charAt(i)) || chars.charAt(i) == ' '))
            {
                continue;
            }

            sb.append(chars.charAt(i));
            switch (chars.charAt(i))
            {
                case '.':
                    if (i < chars.length() - 1 && chars.charAt(i + 1) > 128)
                    {
                        insertIntoList(sb, sentences);
                        sb = new StringBuilder();
                    }
                    break;
                case '…':
                {
                    if (i < chars.length() - 1 && chars.charAt(i+1) == '…')
                    {
                        sb.append('…');
                        ++i;
                        insertIntoList(sb, sentences);
                        sb = new StringBuilder();
                    }
                }
                break;
                case '，':
                case ',':
                case '、':
                case ';':
                case '；':
                    if (!shortest)
                    {
                        continue;
                    }
                case '	':
                case ' ':
                case '。':
                case '!':
                case '！':
                case '?':
                case '？':
                case '\n':
                case '\r':
                    insertIntoList(sb, sentences);
                    sb = new StringBuilder();
                    break;
            }
        }

        if (sb.length() > 0)
        {
            insertIntoList(sb, sentences);
        }

        return sentences;
    }

    private void insertIntoList(StringBuilder sb, List<String> sentences)
    {
        String content = sb.toString().trim();
        if (content.length() > minLength)
        {
            sentences.add(content);
        }
    }
}
