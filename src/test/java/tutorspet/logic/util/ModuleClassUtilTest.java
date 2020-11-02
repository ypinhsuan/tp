package tutorspet.logic.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorspet.commons.core.Messages.MESSAGE_DUPLICATE_LESSON;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_LINK;
import static tutorspet.commons.core.Messages.MESSAGE_NO_LESSONS_IN_MODULE_CLASS;
import static tutorspet.logic.commands.CommandTestUtil.VALID_ATTENDANCE;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_80;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static tutorspet.logic.util.LessonUtil.addAttendanceToLesson;
import static tutorspet.logic.util.LessonUtil.deleteAllStudentsFromLesson;
import static tutorspet.logic.util.LessonUtil.deleteAttendanceFromLesson;
import static tutorspet.logic.util.LessonUtil.deleteStudentFromLesson;
import static tutorspet.logic.util.LessonUtil.editAttendanceInLesson;
import static tutorspet.logic.util.ModuleClassUtil.addAttendanceToModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.addLessonToModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.deleteAllStudentsFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.deleteAttendanceFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.deleteLessonFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.deleteStudentFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.editAttendanceInModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.editLessonInModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.getAbsentWeek;
import static tutorspet.logic.util.ModuleClassUtil.getAttendanceFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.getLessonFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.getParticipationScore;
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalModuleClass.CS2100_TUTORIAL;
import static tutorspet.testutil.TypicalModuleClass.CS2103T_TUTORIAL;
import static tutorspet.testutil.TypicalStudent.ALICE;
import static tutorspet.testutil.TypicalStudent.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;
import tutorspet.testutil.LessonBuilder;
import tutorspet.testutil.ModuleClassBuilder;

public class ModuleClassUtilTest {

    private static final Lesson DEFAULT_LESSON = new LessonBuilder().build();

    @Test
    public void deleteStudentFromModuleClass_validParameters_success() {
        Set<UUID> modifiedUuids = new HashSet<>(CS2103T_TUTORIAL.getStudentUuids());
        modifiedUuids.remove(ALICE.getUuid());
        List<Lesson> lessons = new ArrayList<>(CS2103T_TUTORIAL.getLessons());
        List<Lesson> modifiedLessons = lessons.stream().map(lesson ->
                deleteStudentFromLesson(lesson, ALICE)).collect(Collectors.toUnmodifiableList());
        ModuleClass expectedModuleClass = new ModuleClass(CS2103T_TUTORIAL.getName(), modifiedUuids,
                modifiedLessons);

        assertEquals(expectedModuleClass, deleteStudentFromModuleClass(CS2103T_TUTORIAL, ALICE));
    }

    @Test
    public void deleteStudentFromModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> deleteStudentFromModuleClass(null, ALICE));
        assertThrows(NullPointerException.class, () -> deleteStudentFromModuleClass(CS2103T_TUTORIAL, null));
    }

    @Test
    public void deleteAllStudentsFromModuleClass_validParameters_success() {
        List<Lesson> lessons = new ArrayList<>(CS2103T_TUTORIAL.getLessons());
        List<Lesson> modifiedLessons = lessons.stream().map(lesson ->
                deleteAllStudentsFromLesson(lesson)).collect(Collectors.toUnmodifiableList());
        ModuleClass expectedModuleClass = new ModuleClass(CS2103T_TUTORIAL.getName(), Collections.emptySet(),
                modifiedLessons);

        assertEquals(expectedModuleClass, deleteAllStudentsFromModuleClass(CS2103T_TUTORIAL));
    }

    @Test
    public void deleteAllStudentsFromModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> deleteAllStudentsFromModuleClass(null));
    }

    @Test
    public void addLessonToModuleClass_validParameters_success() throws CommandException {
        List<Lesson> updatedLessons = new ArrayList<>(CS2103T_TUTORIAL.getLessons());
        updatedLessons.add(DEFAULT_LESSON);

        ModuleClass expectedModuleClass = new ModuleClassBuilder(CS2103T_TUTORIAL)
                .withLessons(updatedLessons.toArray(new Lesson[updatedLessons.size()])).build();

        assertEquals(expectedModuleClass, addLessonToModuleClass(CS2103T_TUTORIAL, DEFAULT_LESSON));
    }

    @Test
    public void addLessonToModuleClass_duplicateLesson_throwsCommandException() {
        Lesson lessonToAdd = CS2103T_TUTORIAL.getLessons().get(0);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_LESSON, () ->
                addLessonToModuleClass(CS2103T_TUTORIAL, lessonToAdd));
    }

    @Test
    public void addLessonToModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addLessonToModuleClass(null, DEFAULT_LESSON));
        assertThrows(NullPointerException.class, () -> addLessonToModuleClass(CS2103T_TUTORIAL, null));
    }

    @Test
    public void editLessonInModuleClass_validParameters_success() throws CommandException {
        List<Lesson> updatedLessons = new ArrayList<>(CS2103T_TUTORIAL.getLessons());
        Lesson lessonToEdit = updatedLessons.get(0);
        updatedLessons.set(0, DEFAULT_LESSON);

        ModuleClass expectedModuleClass = new ModuleClassBuilder(CS2103T_TUTORIAL)
                .withLessons(updatedLessons.toArray(new Lesson[updatedLessons.size()])).build();

        assertEquals(expectedModuleClass, editLessonInModuleClass(CS2103T_TUTORIAL, lessonToEdit, DEFAULT_LESSON));
    }

    @Test
    public void editLessonInModuleClass_duplicateLesson_throwsCommandException() {
        List<Lesson> updatedLessons = new ArrayList<>(CS2103T_TUTORIAL.getLessons());
        updatedLessons.add(DEFAULT_LESSON);
        Lesson lessonToEdit = updatedLessons.get(0);

        ModuleClass updatedModuleClass = new ModuleClassBuilder(CS2103T_TUTORIAL)
                .withLessons(updatedLessons.toArray(new Lesson[updatedLessons.size()])).build();

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_LESSON, () ->
                editLessonInModuleClass(updatedModuleClass, lessonToEdit, DEFAULT_LESSON));
    }

    @Test
    public void editLessonInModuleClass_nullParameters_throwsNullPointerException() {
        Lesson lessonToEdit = CS2103T_TUTORIAL.getLessons().get(0);

        assertThrows(NullPointerException.class, () -> editLessonInModuleClass(
                null, lessonToEdit, DEFAULT_LESSON));
        assertThrows(NullPointerException.class, () -> editLessonInModuleClass(
                CS2103T_TUTORIAL, null, DEFAULT_LESSON));
        assertThrows(NullPointerException.class, () -> editLessonInModuleClass(
                CS2103T_TUTORIAL, lessonToEdit, null));
    }

    @Test
    public void deleteLessonFromModuleClass_validParameters_success() throws CommandException {
        List<Lesson> updatedLessons = new ArrayList<>(CS2103T_TUTORIAL.getLessons());
        Lesson lessonToDelete = updatedLessons.get(0);
        updatedLessons.remove(lessonToDelete);

        ModuleClass expectedModuleClass = new ModuleClassBuilder(CS2103T_TUTORIAL)
                .withLessons(updatedLessons.toArray(new Lesson[updatedLessons.size()])).build();

        assertEquals(expectedModuleClass, deleteLessonFromModuleClass(CS2103T_TUTORIAL, lessonToDelete));
    }

    @Test
    public void deleteLessonFromModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> deleteLessonFromModuleClass(null, DEFAULT_LESSON));
        assertThrows(NullPointerException.class, () -> deleteLessonFromModuleClass(CS2103T_TUTORIAL, null));
    }

    @Test
    public void getLessonFromModuleClass_validParameters_success() throws CommandException {
        Lesson expectedLesson = CS2103T_TUTORIAL.getLessons().get(INDEX_FIRST_ITEM.getZeroBased());
        assertEquals(expectedLesson, getLessonFromModuleClass(CS2103T_TUTORIAL, INDEX_FIRST_ITEM));
    }

    @Test
    public void getLessonFromModuleClass_invalidWeekIndex_throwsCommandException() {
        Index invalidIndex = Index.fromZeroBased(CS2103T_TUTORIAL.getLessons().size());
        assertThrows(CommandException.class, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX, () ->
                getLessonFromModuleClass(CS2103T_TUTORIAL, invalidIndex));
    }

    @Test
    public void getLessonFromModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> getLessonFromModuleClass(null, INDEX_FIRST_ITEM));
        assertThrows(NullPointerException.class, () -> getLessonFromModuleClass(CS2103T_TUTORIAL, null));
    }

    @Test
    public void addAttendanceToModuleClass_validParameters_success() throws CommandException {
        Attendance attendanceToAdd = new Attendance(VALID_PARTICIPATION_SCORE_80);
        Index lessonIndex = Index.fromZeroBased(0);

        Lesson lesson = CS2103T_TUTORIAL.getLessons().get(lessonIndex.getZeroBased());
        Lesson editedLesson = addAttendanceToLesson(lesson, ALICE, VALID_WEEK_5, attendanceToAdd);

        ModuleClass expectedModuleClass = new ModuleClassBuilder(CS2103T_TUTORIAL).withLessons(editedLesson).build();

        assertEquals(expectedModuleClass,
                addAttendanceToModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_5, ALICE, attendanceToAdd));
    }

    @Test
    public void addAttendanceToModuleClass_invalidStudent_throwsCommandException() {
        Attendance attendanceToAdd = new Attendance(VALID_PARTICIPATION_SCORE_80);
        Index lessonIndex = Index.fromZeroBased(0);

        assertThrows(CommandException.class, MESSAGE_MISSING_LINK, () ->
                addAttendanceToModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_5, CARL, attendanceToAdd));
    }

    @Test
    public void addAttendanceToModuleClass_invalidLessonIndex_throwsCommandException() {
        Attendance attendanceToAdd = new Attendance(VALID_PARTICIPATION_SCORE_80);
        Index lessonIndex = Index.fromZeroBased(5);

        assertThrows(CommandException.class, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX, () ->
                addAttendanceToModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_5, ALICE, attendanceToAdd));
    }

    @Test
    public void addAttendanceToModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addAttendanceToModuleClass(
                null, INDEX_FIRST_ITEM, VALID_WEEK_1, ALICE, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () -> addAttendanceToModuleClass(
                CS2103T_TUTORIAL, null, VALID_WEEK_1, ALICE, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () -> addAttendanceToModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, null, ALICE, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () -> addAttendanceToModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, VALID_WEEK_1, null, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () -> addAttendanceToModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, VALID_WEEK_1, ALICE, null));
    }

    @Test
    public void editAttendanceInModuleClass_validParameters_success() throws CommandException {
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_80);
        Index lessonIndex = Index.fromZeroBased(0);

        Lesson lesson = CS2103T_TUTORIAL.getLessons().get(lessonIndex.getZeroBased());
        Lesson editedLesson = editAttendanceInLesson(lesson, ALICE, VALID_WEEK_1, attendanceToSet);

        ModuleClass expectedModuleClass = new ModuleClassBuilder(CS2103T_TUTORIAL).withLessons(editedLesson).build();

        assertEquals(expectedModuleClass,
                editAttendanceInModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, ALICE, attendanceToSet));
    }

    @Test
    public void editAttendanceInModuleClass_invalidStudent_throwsCommandException() {
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_80);
        Index lessonIndex = Index.fromZeroBased(0);

        assertThrows(CommandException.class, MESSAGE_MISSING_LINK, () ->
                editAttendanceInModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, CARL, attendanceToSet));
    }

    @Test
    public void editAttendanceInModuleClass_invalidLessonIndex_throwsCommandException() {
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_80);
        Index lessonIndex = Index.fromZeroBased(5);

        assertThrows(CommandException.class, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX, () ->
                editAttendanceInModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, ALICE, attendanceToSet));
    }

    @Test
    public void editAttendanceInModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> editAttendanceInModuleClass(
                null, INDEX_FIRST_ITEM, VALID_WEEK_1, ALICE, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () -> editAttendanceInModuleClass(
                CS2103T_TUTORIAL, null, VALID_WEEK_1, ALICE, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () -> editAttendanceInModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, null, ALICE, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () -> editAttendanceInModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, VALID_WEEK_1, null, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () -> editAttendanceInModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, VALID_WEEK_1, ALICE, null));
    }

    @Test
    public void deleteAttendanceFromModuleClass_validParameters_success() throws CommandException {
        Index lessonIndex = Index.fromZeroBased(0);

        Lesson lesson = CS2103T_TUTORIAL.getLessons().get(lessonIndex.getZeroBased());
        Lesson editedLesson = deleteAttendanceFromLesson(lesson, ALICE, VALID_WEEK_1);

        ModuleClass expectedModuleClass = new ModuleClassBuilder(CS2103T_TUTORIAL).withLessons(editedLesson).build();

        assertEquals(expectedModuleClass,
                deleteAttendanceFromModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, ALICE));
    }

    @Test
    public void deleteAttendanceFromModuleClass_invalidStudent_throwsCommandException() {
        Index lessonIndex = Index.fromZeroBased(0);

        assertThrows(CommandException.class, MESSAGE_MISSING_LINK, () ->
                deleteAttendanceFromModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, CARL));
    }

    @Test
    public void deleteAttendanceFromModuleClass_invalidLessonIndex_throwsCommandException() {
        Index lessonIndex = Index.fromZeroBased(5);

        assertThrows(CommandException.class, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX, () ->
                deleteAttendanceFromModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, ALICE));
    }

    @Test
    public void deleteAttendanceFromModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> deleteAttendanceFromModuleClass(
                null, INDEX_FIRST_ITEM, VALID_WEEK_1, ALICE));
        assertThrows(NullPointerException.class, () -> deleteAttendanceFromModuleClass(
                CS2103T_TUTORIAL, null, VALID_WEEK_1, ALICE));
        assertThrows(NullPointerException.class, () -> deleteAttendanceFromModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, null, ALICE));
        assertThrows(NullPointerException.class, () -> deleteAttendanceFromModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, VALID_WEEK_1, null));
    }

    @Test
    public void getAttendanceFromModuleClass_validParameters_success() throws CommandException {
        Index lessonIndex = Index.fromZeroBased(0);

        assertEquals(new Attendance(VALID_PARTICIPATION_SCORE_80),
                getAttendanceFromModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, ALICE));
    }

    @Test
    public void getAttendanceFromModuleClass_invalidStudent_throwsCommandException() {
        Index lessonIndex = Index.fromZeroBased(0);

        assertThrows(CommandException.class, MESSAGE_MISSING_LINK, () ->
                getAttendanceFromModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, CARL));
    }

    @Test
    public void getAttendanceFromModuleClass_invalidLessonIndex_throwsCommandException() {
        Index lessonIndex = Index.fromZeroBased(5);

        assertThrows(CommandException.class, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX, () ->
                getAttendanceFromModuleClass(CS2103T_TUTORIAL, lessonIndex, VALID_WEEK_1, ALICE));
    }

    @Test
    public void getAttendanceFromModuleClass_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> getAttendanceFromModuleClass(
                null, INDEX_FIRST_ITEM, VALID_WEEK_1, ALICE));
        assertThrows(NullPointerException.class, () -> getAttendanceFromModuleClass(
                CS2103T_TUTORIAL, null, VALID_WEEK_1, ALICE));
        assertThrows(NullPointerException.class, () -> getAttendanceFromModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, null, ALICE));
        assertThrows(NullPointerException.class, () -> getAttendanceFromModuleClass(
                CS2103T_TUTORIAL, INDEX_FIRST_ITEM, VALID_WEEK_1, null));
    }

    @Test
    public void getParticipationScore_validParameter_success() throws CommandException {
        Map<Lesson, List<Integer>> result = new HashMap<>();
        result.put(CS2103T_TUTORIAL.getLessons().get(0),
                new ArrayList<>(Arrays.asList(80, 51)));
        assertEquals(result,
                getParticipationScore(CS2103T_TUTORIAL, ALICE));
    }

    @Test
    public void getParticipationScore_invalidStudent_throwsCommandException() {
        assertThrows(CommandException.class, MESSAGE_MISSING_LINK, () ->
                getParticipationScore(CS2103T_TUTORIAL, CARL));
    }

    @Test
    public void getParticipationScore_noLessonInClass_throwsCommandException() {
        assertThrows(CommandException.class, MESSAGE_NO_LESSONS_IN_MODULE_CLASS, () ->
                getParticipationScore(CS2100_TUTORIAL, ALICE));
    }

    @Test
    public void getParticipationScore_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> getParticipationScore(
                null, ALICE));
        assertThrows(NullPointerException.class, () -> getParticipationScore(
                CS2103T_TUTORIAL, null));
    }

    @Test
    public void getAbsentWeek_validParameter_success() throws CommandException {
        Map<Lesson, List<Integer>> result = new HashMap<>();
        result.put(CS2103T_TUTORIAL.getLessons().get(0),
                new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10)));
        assertEquals(result, getAbsentWeek(CS2103T_TUTORIAL, ALICE));
    }

    @Test
    public void getAbsentWeek_invalidStudent_throwsCommandException() {
        assertThrows(CommandException.class, MESSAGE_MISSING_LINK, () ->
                getAbsentWeek(CS2103T_TUTORIAL, CARL));
    }

    @Test
    public void getAbsentWeek_noLessonInClass_throwsCommandException() {
        assertThrows(CommandException.class, MESSAGE_NO_LESSONS_IN_MODULE_CLASS , () ->
                getAbsentWeek(CS2100_TUTORIAL, CARL));
    }

    @Test
    public void getAbsentWeek_nullParameter_throwsCommandException() {
        assertThrows(NullPointerException.class, () -> getParticipationScore(
                null, ALICE));
        assertThrows(NullPointerException.class, () -> getParticipationScore(
                CS2103T_TUTORIAL, null));
    }
}
