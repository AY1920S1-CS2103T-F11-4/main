package seedu.address.logic.parser.statistics;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.statistics.GetQnsCommand;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.testutil.QuizResultFilterBuilder;

class GetQnsCommandParserTest {

    private GetQnsCommandParser parser = new GetQnsCommandParser();

    @Test
    void parse_invalidCommandFormat_failure() {
        assertParseFailure(parser, " ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                GetQnsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -c -i", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                GetQnsCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_quizResultFilterWithoutSubject_success() {
        // get correct qns
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("true")
                .withSubjects(new ArrayList<>())
                .buildWithSubjectsAndResult();
        GetQnsCommand expectedCommand = new GetQnsCommand(quizResultFilter,
                "Here are the correct questions:");

        assertParseSuccess(parser, " -c", expectedCommand);

        // get incorrect qns
        quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("false")
                .withSubjects(new ArrayList<>())
                .buildWithSubjectsAndResult();
        expectedCommand = new GetQnsCommand(quizResultFilter, "Here are the incorrect questions:");

        assertParseSuccess(parser, " -i", expectedCommand);
    }

    @Test
    void parse_quizResultFilterWithSubject_success() {
        // one subject
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("true")
                .withSubjects(new ArrayList<>(Collections.singletonList("CS2103T")))
                .buildWithSubjectsAndResult();
        GetQnsCommand expectedCommand = new GetQnsCommand(quizResultFilter,
                "Here are the correct questions for [CS2103T]:");

        assertParseSuccess(parser, " -c s/CS2103T", expectedCommand);

        // multiple subjects
        quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("true")
                .withSubjects(new ArrayList<>(Arrays.asList("CS2103T", "CS2101")))
                .buildWithSubjectsAndResult();
        expectedCommand = new GetQnsCommand(quizResultFilter,
                "Here are the correct questions for [CS2102T, CS2101]:");

        assertParseSuccess(parser, " -c s/CS2103T s/CS2101", expectedCommand);

    }
}
