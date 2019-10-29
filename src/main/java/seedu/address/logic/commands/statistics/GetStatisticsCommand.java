package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.statistics.Type.STATS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.model.quiz.exceptions.EmptyQuizResultListException;

/**
 * Gets statistics of how well the user has attempted the questions.
 */
public class GetStatisticsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the statistics of how well "
            + "a question has been answered for a subject. "
            + "A pie chart will be returned.\n"
            + "Parameters: "
            + PREFIX_SUBJECT + "SUBJECT "
            + "[" + PREFIX_SUBJECT + "SUBJECT]... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECT + "CS2103T";

    public static final String MESSAGE_SUCCESS = "Here are the statistics: ";
    public static final String MESSAGE_NO_STATISTICS = "There is no available data for computation of "
            + "statistics, try doing some questions.";

    private QuizResultFilter quizResultFilter;

    public GetStatisticsCommand(QuizResultFilter quizResultFilter) {
        requireNonNull(quizResultFilter);
        this.quizResultFilter = quizResultFilter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.filterQuizResult(quizResultFilter);
        } catch (EmptyQuizResultListException e) {
            throw new CommandException(MESSAGE_NO_STATISTICS);
        }
        CommandResult c = new CommandResult(MESSAGE_SUCCESS, 8);
        c.setType(STATS);
        return c;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetStatisticsCommand // instanceof handles nulls
                && quizResultFilter.equals(((GetStatisticsCommand) other).quizResultFilter)); // state check
    }
}
