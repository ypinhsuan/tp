package tutorspet.logic.commands.student;

import static tutorspet.commons.core.Messages.MESSAGE_DUPLICATE_STUDENT;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorspet.logic.commands.student.AddStudentCommand.MESSAGE_SUCCESS;
import static tutorspet.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.UserPrefs;
import tutorspet.model.student.Student;
import tutorspet.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddStudentCommand}.
 */
public class AddStudentCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        String expectedMessage = String.format(MESSAGE_SUCCESS, validStudent);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.addStudent(validStudent);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(new AddStudentCommand(validStudent), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student studentInList = model.getTutorsPet().getStudentList().get(0);
        assertCommandFailure(new AddStudentCommand(studentInList), model, MESSAGE_DUPLICATE_STUDENT);
    }
}
