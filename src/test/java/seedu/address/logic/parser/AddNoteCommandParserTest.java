package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalAppData.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.AddNoteCommand;
import seedu.address.logic.parser.note.AddNoteCommandParser;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.testutil.NoteBuilder;

class AddNoteCommandParserTest {
    private AddNoteCommandParser parser = new AddNoteCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        Note expectedNote = new NoteBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_BOB + CONTENT_DESC_BOB,
                new AddNoteCommand(expectedNote));

        // multiple titles, last accepted
        assertParseSuccess(parser, TITLE_DESC_AMY + TITLE_DESC_BOB + CONTENT_DESC_BOB,
                new AddNoteCommand(expectedNote));

        // multiple contents, last accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + CONTENT_DESC_AMY + CONTENT_DESC_BOB,
                new AddNoteCommand(expectedNote));
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_BOB + CONTENT_DESC_BOB, expectedMessage);

        // missing content prefix
        assertParseFailure(parser, TITLE_DESC_BOB + VALID_CONTENT_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_BOB + VALID_CONTENT_BOB, expectedMessage);
    }

    @Test
    void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + CONTENT_DESC_BOB, Title.MESSAGE_CONSTRAINTS);

        // invalid content
        assertParseFailure(parser, TITLE_DESC_BOB + INVALID_CONTENT_DESC, Content.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + INVALID_CONTENT_DESC, Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_BOB + CONTENT_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
    }
}
