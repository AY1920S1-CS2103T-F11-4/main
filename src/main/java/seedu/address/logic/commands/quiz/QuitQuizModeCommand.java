package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Quits the quiz mode.
 */
public class QuitQuizModeCommand extends Command {
    public static final String COMMAND_WORD = "quit";
    public static final String MESSAGE_SUCCESS = "You have exited from the quiz mode!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.clearQuizQuestionList();
        LogicManager.setIsQuiz(false);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
