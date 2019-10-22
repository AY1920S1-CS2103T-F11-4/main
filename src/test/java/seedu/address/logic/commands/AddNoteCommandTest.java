package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.note.AddNoteCommand;
import seedu.address.model.AppData;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.note.Note;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.statistics.TempStatsQnsModel;
import seedu.address.model.task.Task;
import seedu.address.testutil.NoteBuilder;

public class AddNoteCommandTest {

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNoteCommand(null));
    }

    @Test
    public void execute_noteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Note validNote = new NoteBuilder().build();

        CommandResult commandResult = new AddNoteCommand(validNote).execute(modelStub);

        assertEquals(String.format(AddNoteCommand.MESSAGE_SUCCESS, validNote), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.notesAdded);
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note validNote = new NoteBuilder().build();
        AddNoteCommand addNoteCommand = new AddNoteCommand(validNote);
        ModelStub modelStub = new ModelStubWithNote(validNote);

        assertThrows(CommandException.class,
                AddNoteCommand.MESSAGE_DUPLICATE_TITLE, () -> addNoteCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Note alice = new NoteBuilder().withTitle("Alice").build();
        Note bob = new NoteBuilder().withTitle("Bob").build();
        AddNoteCommand addAliceCommand = new AddNoteCommand(alice);
        AddNoteCommand addBobCommand = new AddNoteCommand(bob);

        // same object -> returns true
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddNoteCommand addAliceCommandCopy = new AddNoteCommand(alice);
        assertEquals(addAliceCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceCommand);

        // null -> returns false
        assertNotNull(addAliceCommand);

        // different note -> returns false
        assertNotEquals(addAliceCommand, addBobCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAppDataFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppDataFilePath(Path appDataFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppData(ReadOnlyAppData newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAppData getAppData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteNote(Note target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNote(Note target, Note editedNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Note> getFilteredNoteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredNoteList(Predicate<Note> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<PieChart.Data> getStatsChartData() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasQuestion(Question question) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Note getNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addQuestion(Question question) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkQuizAnswer(int index, Answer answer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteQuestion(Question target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setQuestion(Question target, Question editedQuestion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Question> getFilteredQuestionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredQuestionList(Predicate<Question> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearQuizQuestionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Question> getQuizQuestions(int numOfQuestions, Subject subject, Difficulty difficulty) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markTaskAsDone(Task taskDone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setQuizQuestionList(ObservableList<Question> quizQuestionList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Question> getFilteredQuizQuestionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Answer showQuizAnswer(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addQuizResult(QuizResult quizResult) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList getFilteredQuizResultList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getTotalQuestionsDone() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getTotalQuestionsCorrect() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getTotalQuestionsIncorrect() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCorrectQnsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIncorrectQnsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TempStatsQnsModel> getStatsQnsList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single note.
     */
    private class ModelStubWithNote extends ModelStub {
        private final Note note;

        ModelStubWithNote(Note note) {
            requireNonNull(note);
            this.note = note;
        }

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return this.note.isSameNote(note);
        }
    }

    /**
     * A Model stub that always accept the note being added.
     */
    private class ModelStubAcceptingNoteAdded extends ModelStub {
        final ArrayList<Note> notesAdded = new ArrayList<>();

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return notesAdded.stream().anyMatch(note::isSameNote);
        }

        @Override
        public void addNote(Note note) {
            requireNonNull(note);
            notesAdded.add(note);
        }

        @Override
        public ReadOnlyAppData getAppData() {
            return new AppData();
        }
    }
}
