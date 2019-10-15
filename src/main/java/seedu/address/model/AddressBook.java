package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.note.Note;
import seedu.address.model.note.UniqueNoteList;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.question.UniqueQuestionList;
import seedu.address.model.quiz.QuizQuestionList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameNote comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueNoteList notes;

    private final UniqueQuestionList questions;

    private final QuizQuestionList quiz;
    private final TaskList tasks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */

    {
        notes = new UniqueNoteList();

        questions = new UniqueQuestionList();

        quiz = new QuizQuestionList();
        tasks = new TaskList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Notes in {@code toBeCopied}.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the note list with {@code notes}.
     * {@code notes} must not contain duplicate titles.
     */
    public void setNotes(List<Note> notes) {
        this.notes.setNotes(notes);
    }

    /**
     * Replaces the contents of the question list with {@code questions}.
     * {@code questions} must not contain duplicate questions.
     */
    public void setQuestions(List<Question> questions) {
        this.questions.setQuestions(questions);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setNotes(newData.getNoteList());
        setQuestions(newData.getQuestionList());
        setTasks(newData.getTaskList());
    }

    // note-level operations

    /**
     * Returns true if a lecture note with the same title as {@code note} exists.
     */
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Adds a lecture note; its title must not already exist.
     */
    public void addNote(Note p) {
        notes.add(p);
    }

    /**
     * Replaces the given lecture note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist beforehand and titles must remain unique.
     */
    public void setNote(Note target, Note editedNote) {
        requireNonNull(editedNote);

        notes.setNote(target, editedNote);
    }

    /**
     * Retrieves {@code title} from the note list. The note must exists.
     * @param title The note with the same tile to be retrieved.
     * @return The note with the same title as specified in input.
     */
    public Note getNote(Note title) {
        return notes.get(title);
    }

    /**
     * Removes {@code title} from the lecture note list. This title must exist.
     */
    public void removeNote(Note title) {
        notes.remove(title);
    }

    //// question operations
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasQuestion(Question question) {
        requireNonNull(question);
        return questions.contains(question);
    }

    /**
     * Adds a question to NUStudy.
     * The question must not already exist in NUStudy.
     */
    public void addQuestion(Question q) {
        questions.add(q);
    }

    /**
     * Replaces the given question {@code target} in the list with {@code editedQuestion}.
     * {@code target} must exist in NUStudy.
     * The question body of {@code editedQuestion} must not be the same as another existing question in NUStudy.
     */
    public void setQuestion(Question target, Question editedQuestion) {
        requireNonNull(editedQuestion);

        questions.setQuestion(target, editedQuestion);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeQuestion(Question key) {
        questions.remove(key);
    }

    // quiz operations

    /**
     * Sets the question list in quiz with specific {@code subject} and {@code difficulty}.
     */
    public void setQuizQuestionList(int numOfQuestions, Subject subject, Difficulty difficulty) {
        quiz.setQuizQuestionList(numOfQuestions, subject, difficulty);
    }

    /**
     * Checks the answer input by user and return a boolean value as the result.
     */
    public boolean checkQuizAnswer(int index, Answer answer) {
        return quiz.checkQuizAnswer(index, answer);
    }

    /**
     * Clears the quiz question list.
     */
    public void clearQuizQuestionList() {
        quiz.clearQuizQuestionList();
    }

    // util methods

    @Override
    public String toString() {
        return notes.asUnmodifiableObservableList().size() + " lecture notes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Note> getNoteList() {
        return notes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Question> getQuestionList() {
        return questions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && notes.equals(((AddressBook) other).notes)
                && questions.equals(((AddressBook) other).questions))
                && tasks.equals(((AddressBook) other).tasks));
    }

    @Override
    public int hashCode() {
        return Objects.hash(notes, quiz, questions, tasks);
    }

    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    // note-level operations

    /**
     * Returns true if a revision task with the same note / question, same date and time, and same status
     * as {@code task} exists.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a revision task.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist beforehand.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code toRemove} from the revision task list. This task must exist.
     */
    public void removeTask(Task toRemove) {
        tasks.remove(toRemove);
    }
}
