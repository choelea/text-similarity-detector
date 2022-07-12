package tech.icoding.tsd;

import java.util.List;

/**
 * 讲字符串拆分成一个个的句子
 * @author : Joe
 * @date : 2022/7/1
 */
public interface SentenceBreaker {

    /**
     * 拆分成一个个句子
     * @param content
     * @return
     */
    List<String> toSentenceList(CharSequence content);
}
