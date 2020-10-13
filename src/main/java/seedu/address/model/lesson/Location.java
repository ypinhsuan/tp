package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Location {

    public static final String MESSAGE_CONSTRAINTS =
            "Locations should only contain alphanumeric characters, spaces, underscores and hyphens, "
                    + "and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\w-][\\w- ]*";

    public final String location;

    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);

        this.location = location;
    }

    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return location;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && location.equals(((Location) other).location)); // state check
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
