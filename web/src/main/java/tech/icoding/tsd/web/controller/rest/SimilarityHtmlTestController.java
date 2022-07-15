package tech.icoding.tsd.web.controller.rest;

import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.*;
import tech.icoding.tsd.DefaultSentenceBreaker;
import tech.icoding.tsd.LcsSimilarityChecker;
import tech.icoding.tsd.web.form.ContentForm;

/**
 * @author : Joe
 * @date : 2022/7/6
 */
@RestController
@CrossOrigin(origins = "http://127.0.0.1:8887/", maxAge = 3600)
@RequestMapping("/api/html")
public class SimilarityHtmlTestController {
    LcsSimilarityChecker paragraphSimilarityChecker = new LcsSimilarityChecker(new DefaultSentenceBreaker(3, true), 3);

    @PostMapping("/_parse")
    public CharSequence[] check(@RequestBody ContentForm contentForm){
        final String left = Jsoup.parse(contentForm.getLeft()).text();
        final String right = Jsoup.parse(contentForm.getRight()).text();
        final CharSequence[] pageContents = paragraphSimilarityChecker.sortedLongestCommonSubstr(left, right);
        return pageContents;
    }

}
