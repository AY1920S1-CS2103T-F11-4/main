package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.note.Note;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.model.statistics.TempStatsQnsModel;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Note> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to false
     */
    Predicate<Note> PREDICATE_SHOW_NO_NOTES = unused -> false;

    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Question> PREDICATE_SHOW_ALL_QUESTIONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' application data file path.
     */
    Path getAppDataFilePath();

    /**
     * Sets the user prefs' application data file path.
     */
    void setAppDataFilePath(Path appDataFilePath);

    /**
     * Replaces current application data with the data in {@code appData}.
     */
    void setAppData(ReadOnlyAppData appData);

    /**
     * Returns the AppData
     */
    ReadOnlyAppData getAppData();

    /**
     * Returns true if a note with the same identity as {@code note} exists in the current application data.
     */
    boolean hasNote(Note note);

    /**
     * Deletes the given existing lecture note.
     */
    void deleteNote(Note target);

    /**
     * Clears all lecture notes.
     */
    void clearNotes();

    /**
     * Adds the given (not yet existing) lecture note.
     */
    void addNote(Note note);

    boolean hasTask(Task task);

    void deleteTask(Task target);

    void addTask(Task task);

    /**
     * Replaces the given lecture note {@code target} with {@code editedNote}.
     * {@code target} must exist, while the title of {@code editedNote} must not be the same as another
     * existing lecture note.
     */
    void setNote(Note target, Note editedNote);

    /**
     * Returns an unmodifiable view of the filtered note list
     */
    ObservableList<Note> getFilteredNoteList();

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered note list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredNoteList(Predicate<Note> predicate);

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    Note getNote(Note note);

    /**
     * Returns true if a question with the same identity as {@code question} exists in NUStudy.
     */
    boolean hasQuestion(Question question);

    /**
     * Adds the given question.
     * {@code question} must not already exist in NUStudy.
     */
    void addQuestion(Question question);

    /**
     * Deletes the given question.
     * The question must exist in NUStudy.
     */
    void deleteQuestion(Question target);

    /**
     * Replaces the given question {@code target} with {@code editedQuestion}.
     * {@code target} must exist in NUStudy.
     * The question body of {@code editedQuestion} must not be the same as another existing question in NUStudy.
     */
    void setQuestion(Question target, Question editedQuestion);

    /**
     * Returns an unmodifiable view of the filtered question list
     */
    ObservableList<Question> getFilteredQuestionList();

    /**
     * Updates the filter of the filtered question list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredQuestionList(Predicate<Question> predicate);

    /**
     * Returns a question list with specific {@code numOfQuestions}, {@code subject} and {@code difficulty}.
     */
    ObservableList<Question> getQuizQuestions(int numOfQuestions, Subject subject, Difficulty difficulty);

    /**
     * Sets the question list in quiz as {@code quizQuestionList}.
     */
    void setQuizQuestionList(ObservableList<Question> quizQuestionList);

    void setTask(Task target, Task editedTask);

    /**
     * Returns an unmodifiable view of the filtered quiz question list.
     */
    ObservableList<Question> getFilteredQuizQuestionList();

    void filterQuizResult(QuizResultFilter quizResultFilter);

    /**
     * Returns an unmodifiable view of the filtered quiz result list.
     */
    ObservableList<QuizResult> getFilteredQuizResultList();

    /**
     * Checks the an answer input by user and return the boolean value as the result.
     */
    boolean checkQuizAnswer(int index, Answer answer);

    void addQuizResult(QuizResult quizResult);

    /**
     * Clears the quiz question list.
     */
    void clearQuizQuestionList();

    void markTaskAsDone(Task taskDone);

    void clearTaskList();

    /**
     * Returns an answer for question in quiz with specific {@code index}.
     */
    Answer showQuizAnswer(int index);

    /**
     * Returns the total number of questions answered.
     */
    int getTotalQuestionsDone();

    /**
     * Returns an unmodifiable view of a list correct questions.
     */
    void setCorrectQnsList();

    /**
     * Returns an unmodifiable view of a list incorrect questions.
     */
    void setIncorrectQnsList();

    /**
     * Returns an unmodifiable view of the pie chart data.
     */
    ObservableList<PieChart.Data> getStatsPieChartData();

    ObservableList<TempStatsQnsModel> getStatsQnsList();

    ObservableList<XYChart.Data> getStackBarChartData();
}
