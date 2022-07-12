package tech.icoding.tsd.web.controller.rest;

import org.springframework.web.bind.annotation.*;
import tech.icoding.tsd.DefaultSentenceBreaker;
import tech.icoding.tsd.LcbSimilarityChecker;
import tech.icoding.tsd.web.form.ContentForm;


/**
 * @author : Joe
 * @date : 2022/7/6
 */
@RestController
//@CrossOrigin(origins = "http://127.0.0.1:8887/", maxAge = 3600)
@RequestMapping("/api/similarity")
public class SimilarityTestController {

    LcbSimilarityChecker paragraphSimilarityChecker = new LcbSimilarityChecker(new DefaultSentenceBreaker());
    @PostMapping("/_check")
    public CharSequence[] check(@RequestBody ContentForm contentForm){
        final Float apply = paragraphSimilarityChecker.apply(contentForm.getLeft(), contentForm.getRight());
        final CharSequence[] charSequences = paragraphSimilarityChecker.sortedLongestCommonSubstr(contentForm.getLeft(), contentForm.getRight());
        return charSequences;
    }
}
