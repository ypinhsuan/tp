package seedu.address.model.lesson.exceptions;

public class DuplicateLessonException extends RuntimeException {

    public DuplicateLessonException() {
        super("Operation would result in duplicate lessons");
    }
}
