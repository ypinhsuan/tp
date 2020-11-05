package tutorspet.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format.\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists.";
    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student index provided is invalid.";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "%1$d students listed.";

    public static final String MESSAGE_DUPLICATE_MODULE_CLASS = "This class already exists.";
    public static final String MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX = "The class index provided is invalid.";
    public static final String MESSAGE_MODULE_CLASS_LISTED_OVERVIEW = "%1$d classes listed.";

    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists.";
    public static final String MESSAGE_OVERLAP_LESSON = "This lesson overlaps with another lesson in the same class.";
    public static final String MESSAGE_INVALID_LESSON_DISPLAYED_INDEX = "The lesson index provided is invalid.";
    public static final String MESSAGE_NO_LESSONS_IN_MODULE_CLASS = "There are no lessons in this class.";

    public static final String MESSAGE_EXISTING_LINK = "This student already exists in this class.";
    public static final String MESSAGE_MISSING_LINK = "This student does not exist in this class.";

    public static final String MESSAGE_INVALID_WEEK = "The week provided is invalid.";

    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "This student's attendance already exists.";
    public static final String MESSAGE_MISSING_STUDENT_ATTENDANCE = "This student's attendance does not exist.";
    public static final String MESSAGE_NO_LESSON_ATTENDED = "This student did not attend any lesson.";
}
