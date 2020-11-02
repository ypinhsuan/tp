package tutorspet.logic.commands.attendance;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_NO_LESSON_ATTENDED;
import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.util.ModuleClassUtil.getAbsentWeek;
import static tutorspet.logic.util.ModuleClassUtil.getParticipationScore;

import java.util.List;
import java.util.Map;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.Command;
import tutorspet.logic.commands.CommandResult;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.Model;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;
import tutorspet.model.student.Student;

/**
 * Gives a summary of a student's attendance for a specific class in the student manager.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gives a summary of a student's attendance "
            + "for a specific class\n"
            + "Note: All indexes and numbers must be positive integers.\n"
            + "Parameters: "
            + PREFIX_CLASS_INDEX + "CLASS_INDEX "
            + PREFIX_STUDENT_INDEX + "STUDENT_INDEX ";

    public static final String MESSAGE_SUCCESS = "%1$s attendance summary for %2$s:\n"
            + "Average participation: %3$.2f\n"
            + "Lesson(s) not attended:\n"
            + "%4$s";

    private final Index moduleClassIndex;
    private final Index studentIndex;

    /**
     * Creates a StatisticsCommand to display a summary of a specific student's attendance for a specific class in
     * the student manager.
     */
    public StatisticsCommand(Index moduleClassIndex, Index studentIndex) {
        requireAllNonNull(moduleClassIndex, studentIndex);

        this.moduleClassIndex = moduleClassIndex;
        this.studentIndex = studentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<ModuleClass> lastShownModuleClassList = model.getFilteredModuleClassList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        if (moduleClassIndex.getOneBased() > lastShownModuleClassList.size()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        if (studentIndex.getOneBased() > lastShownStudentList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        ModuleClass targetModuleClass = lastShownModuleClassList.get(moduleClassIndex.getZeroBased());
        Student targetStudent = lastShownStudentList.get(studentIndex.getZeroBased());

        Map<Lesson, List<Integer>> scores = getParticipationScore(targetModuleClass, targetStudent);
        double averageScore = getAverageParticipationScore(scores);
        Map<Lesson, List<Integer>> attendances = getAbsentWeek(targetModuleClass, targetStudent);
        String weeksNotPresent = printStats(attendances);

        String message = String.format(MESSAGE_SUCCESS, targetStudent.getName().fullName,
                targetModuleClass.getName().fullName, averageScore, weeksNotPresent);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatisticsCommand // instanceof handles nulls
                && moduleClassIndex.equals(((StatisticsCommand) other).moduleClassIndex)
                && studentIndex.equals(((StatisticsCommand) other).studentIndex));
    }

    private double getAverageParticipationScore(Map<Lesson, List<Integer>> scores) throws CommandException {
        int totalScore = 0;
        double numOfWeeksAttended = 0;

        for (Lesson lesson : scores.keySet()) {
            List<Integer> lessonScore = scores.get(lesson);

            for (Integer score : lessonScore) {
                totalScore += score;
                numOfWeeksAttended += 1;
            }
        }

        if (numOfWeeksAttended == 0) {
            throw new CommandException(MESSAGE_NO_LESSON_ATTENDED);
        }

        return totalScore / numOfWeeksAttended;
    }

    private String printStats(Map<Lesson, List<Integer>> attendances) {
        StringBuilder weeksNotPresent = new StringBuilder();

        for (Lesson lesson : attendances.keySet()) {
            weeksNotPresent.append(lesson.printLesson()).append(": Weeks");
            List<Integer> absentWeeks = attendances.get(lesson);

            for (Integer weekNo : absentWeeks) {
                weeksNotPresent.append(" ").append(weekNo);
            }

            weeksNotPresent.append("\n");
        }

        return weeksNotPresent.toString();
    }
}
