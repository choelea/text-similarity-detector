package tech.icoding.tsd;

/**
 * @author : Joe
 * @date : 2022/7/1
 */
public interface SimilarityScore<R> {
    /**
     * Compares two Paragraph.
     *
     * @param left the first Paragraph
     * @param right the second Paragraph
     * @return The similarity score between two Paragraphs
     */
    R apply(CharSequence left, CharSequence right);
}
