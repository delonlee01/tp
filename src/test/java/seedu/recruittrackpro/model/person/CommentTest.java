package seedu.recruittrackpro.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recruittrackpro.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Comment(null));
    }

    @Test
    public void constructor_invalidComment_throwsIllegalArgumentException() {
        String invalidComment = "A".repeat(501);
        assertThrows(IllegalArgumentException.class, () -> new Comment(invalidComment));
    }

    @Test
    public void isValidComment() {
        // null comment
        assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));

        // invalid comment
        assertFalse(Comment.isValidComment("A".repeat(501))); // too long

        // valid comments
        assertTrue(Comment.isValidComment("do not disturb"));
        assertTrue(Comment.isValidComment("")); // blank
    }

    @Test
    public void equals() {
        Comment comment = new Comment("Valid Comment");

        // same values -> returns true
        assertTrue(comment.equals(new Comment("Valid Comment")));

        // same object -> returns true
        assertTrue(comment.equals(comment));

        // null -> returns false
        assertFalse(comment.equals(null));

        // different types -> returns false
        assertFalse(comment.equals(5.0f));

        // different values -> returns false
        assertFalse(comment.equals(new Comment("Other Valid Comment")));
    }
}
