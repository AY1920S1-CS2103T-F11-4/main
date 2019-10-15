package seedu.address.model.quiz;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.Subject;
import seedu.address.model.question.UniqueQuestionList;

/**
 * A list of quiz questions.
 */
public class QuizQuestionList implements Iterable<Question> {
    private final ObservableList<Question> internalList = FXCollections.observableArrayList();
    private final UniqueQuestionList questions = new UniqueQuestionList();

    /**
     * Sets the question list in quiz as {@code quizQuestionList}.
     */
    public void setQuizQuestionList(ObservableList<Question> quizQuestionList) {
        for (int i = 0; i < quizQuestionList.size(); i++) {
            internalList.add(quizQuestionList.get(i));
        }
    }

    /**
     * Checks if the answer input by user is correct and return a boolean value to show the result.
     */
    public boolean checkQuizAnswer(int index, Answer answer) {
        requireAllNonNull(answer);

        return internalList.get(index).getAnswer().equals(answer);
    }

    /**
     * Clears the quiz question list.
     */
    public void clearQuizQuestionList() {
        internalList.clear();
    }

    @Override
    public Iterator<Question> iterator() {
        return internalList.iterator();
    }
}
