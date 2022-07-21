package tech.icoding.tsd.web.controller.rest;

import org.springframework.web.bind.annotation.*;
import tech.icoding.tsd.CssSimilarityChecker;
import tech.icoding.tsd.DefaultSentenceBreaker;
import tech.icoding.tsd.LcsSimilarityChecker;
import tech.icoding.tsd.web.form.ContentForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author : Joe
 * @date : 2022/7/6
 */
@RestController
@CrossOrigin(origins = "http://127.0.0.1:8887/", maxAge = 3600)
@RequestMapping("/api/similarity")
public class SimilarityTestController {

    LcsSimilarityChecker paragraphSimilarityChecker = new LcsSimilarityChecker(new DefaultSentenceBreaker(3, true), 3);
//    CssSimilarityChecker cssSimilarityChecker = new CssSimilarityChecker(new DefaultSentenceBreaker());
    @PostMapping("/_check")
    public Map check(@RequestBody ContentForm contentForm){
        final Float score = paragraphSimilarityChecker.apply(contentForm.getLeft(), contentForm.getRight());
        final List<CharSequence> charSequences = paragraphSimilarityChecker.sortedLongestCommonSubstr(contentForm.getLeft(), contentForm.getRight());

        //        去掉重复字符串
        Map<Integer, CharSequence> hashMap = new HashMap<>();
        charSequences.forEach(sequence -> {
            hashMap.put(sequence.hashCode(), sequence);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("score", score);
        map.put("subStrings", hashMap.values());
        return map;
    }


//    @PostMapping("/_checks")
//    public CharSequence[] checks(@RequestBody ContentForm contentForm){
//        final CharSequence[] charSequences = cssSimilarityChecker.sortedLongestCommonSubstr(contentForm.getLeft(), contentForm.getRight());
//        return charSequences;
//    }
}
