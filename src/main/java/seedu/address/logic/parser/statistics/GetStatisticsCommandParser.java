package seedu.address.logic.parser.statistics;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.statistics.GetStatisticsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GetStatisticsCommand object.
 */
public class GetStatisticsCommandParser implements Parser<GetStatisticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetStatisticsCommand
     * and returns an GetStatisticsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GetStatisticsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        // To be implemented later
        /*ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        List subjects = argMultimap.getAllValues(PREFIX_SUBJECT);

        if (subjects.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GetStatisticsCommand.MESSAGE_USAGE));
        }

        return new GetStatisticsCommand(subjects);
        */
        return new GetStatisticsCommand();
    }
}
