package seedu.address.logic.parser.quiz;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.quiz.QuizCheckAnswer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.Answer;

/**
 * Parses user input for quiz.
 */
public class QuizAnswerParser implements Parser<QuizCheckAnswer> {
    /**
     * Used for initial separation of question index and answer.
     */
    private static final Pattern BASIC_INPUT_FORMAT = Pattern.compile("(?<index>\\S+)(?<answer>.*)");

    /**
     * Parses the given {@code String} of user input in the context of the quiz answer
     * and returns an QuizCheckAnswer object for execution.
     */
    public QuizCheckAnswer parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_INPUT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String index = matcher.group("index");
        final String stringAnswer = matcher.group("answer");
        final int intIndex = Integer.parseInt(index);
        final Answer answer = new Answer(stringAnswer);

        return new QuizCheckAnswer(intIndex, answer);
    }
}
