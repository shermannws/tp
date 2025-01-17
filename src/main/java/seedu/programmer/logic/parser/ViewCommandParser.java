package seedu.programmer.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.programmer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.programmer.logic.parser.CliSyntax.PREFIX_CLASS_ID;
import static seedu.programmer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.programmer.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.programmer.logic.commands.ViewCommand;
import seedu.programmer.logic.parser.exceptions.ParseException;
import seedu.programmer.model.student.QueryStudentDescriptor;
import seedu.programmer.model.student.StudentDetailContainsQueryPredicate;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STUDENT_ID, PREFIX_CLASS_ID);

        // Initializing all the arguments as null at the beginning.
        String trimmedNameArg = null;
        String trimmedSidArg = null;
        String trimmedCidArg = null;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            trimmedNameArg = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (trimmedNameArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
            }
        }
        if (argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()) {
            trimmedSidArg = argMultimap.getValue(PREFIX_STUDENT_ID).get().trim();
            if (trimmedSidArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
            }
        }
        if (argMultimap.getValue(PREFIX_CLASS_ID).isPresent()) {
            trimmedCidArg = argMultimap.getValue(PREFIX_CLASS_ID).get().trim();
            if (trimmedCidArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
            }
        }

        QueryStudentDescriptor queryStudentDescriptor =
                new QueryStudentDescriptor(trimmedNameArg, trimmedSidArg, trimmedCidArg);

        if (!queryStudentDescriptor.isAnyFieldToBeQueried()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        return new ViewCommand(new StudentDetailContainsQueryPredicate(queryStudentDescriptor));
    }
}
