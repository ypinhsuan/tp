package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Week;
import seedu.address.model.components.name.Name;
import seedu.address.model.components.tag.Tag;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.NumberOfOccurrences;
import seedu.address.model.lesson.Venue;
import seedu.address.model.student.Email;
import seedu.address.model.student.Telegram;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DAY = "Day format provided is invalid.";
    public static final String MESSAGE_INVALID_TIME = "Time format provided is invalid.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);

        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String telegram} into a {@code Telegram}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegram} is invalid.
     */
    public static Telegram parseTelegram(String telegram) throws ParseException {
        requireNonNull(telegram);

        String trimmedTelegram = telegram.trim();
        if (!Telegram.isValidTelegram(trimmedTelegram)) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        return new Telegram(trimmedTelegram);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);

        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);

        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String day} into a {@code Day}.
     * Leading and trailing whitespaces will be trimmed.
     * Characters will be capitalized.
     *
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static Day parseDay(String day) throws ParseException {
        requireNonNull(day);

        String trimmedDay = day.trim().toUpperCase();
        Day parsedDay;
        try {
            parsedDay = Day.valueOf(trimmedDay);
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_DAY);
        }
        return parsedDay;
    }

    /**
     * Parses a {@code String time} into a {@code LocalTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);

        String trimmedTime = time.trim();
        LocalTime parsedTime;
        try {
            parsedTime = LocalTime.parse(trimmedTime);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_TIME);
        }
        return parsedTime;
    }

    /**
     * Parses a {@code String venue} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);

        String trimmedVenue = venue.trim();
        if (!Venue.isValidVenue(trimmedVenue)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedVenue);
    }

    /**
     * Parses a {@code String numberOfOccurrences} into a {@code NumberOfOccurrences}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code numberOfOccurrences} is invalid.
     */
    public static NumberOfOccurrences parseNumberOfOccurrences(String numberOfOccurrences) throws ParseException {
        requireNonNull(numberOfOccurrences);

        String trimmedNumberOfOccurrences = numberOfOccurrences.trim();
        int occurrences;

        try {
            occurrences = Integer.parseInt(trimmedNumberOfOccurrences);
        } catch (NumberFormatException e) {
            throw new ParseException(NumberOfOccurrences.MESSAGE_CONSTRAINTS);
        }

        if (!NumberOfOccurrences.isValidNumberOfOccurrences(occurrences)) {
            throw new ParseException(NumberOfOccurrences.MESSAGE_CONSTRAINTS);
        }
        return new NumberOfOccurrences(occurrences);
    }

    /**
     * Parses a {@code String week} into a {@code Week}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code week} is invalid.
     */
    public static Week parseWeek(String week) throws ParseException {
        requireNonNull(week);

        String trimmedWeek = week.trim();
        Index weekIndex;

        try {
            weekIndex = ParserUtil.parseIndex(trimmedWeek);
        } catch (ParseException e) {
            throw new ParseException(Week.MESSAGE_CONSTRAINTS);
        }

        if (!Week.isValidWeek(weekIndex)) {
            throw new ParseException(Week.MESSAGE_CONSTRAINTS);
        }
        return new Week(weekIndex);
    }

    /**
     * Parses a {@code String participationScore} into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code participationScore} is invalid.
     */
    public static int parseParticipationScore(String participationScore) throws ParseException {
        requireNonNull(participationScore);

        String trimmedScore = participationScore.trim();
        int checkedScore;

        try {
            checkedScore = Integer.parseInt(trimmedScore);
        } catch (NumberFormatException e) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }

        if (!Attendance.isValidParticipationScore(checkedScore)) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }
        return checkedScore;
    }
}
