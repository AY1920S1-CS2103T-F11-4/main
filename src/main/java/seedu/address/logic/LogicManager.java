package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AppDataParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.quiz.QuizParser;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.note.Note;
import seedu.address.model.question.Question;
import seedu.address.model.statistics.TempStatsQnsModel;
import seedu.address.model.task.Task;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private static boolean isQuiz = false;
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AppDataParser appDataParser;
    private final QuizParser quizParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        appDataParser = new AppDataParser();
        quizParser = new QuizParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult commandResult;
        if (!isQuiz) {
            logger.info("----------------[USER COMMAND][" + commandText + "]");

            Command command = appDataParser.parseCommand(commandText);
            commandResult = command.execute(model);

            if (commandResult.isShowStats()) {
                return commandResult;
            }
        } else {
            logger.info("----------------[USER INPUT][" + commandText + "]");

            Command command = quizParser.parseCommand(commandText);
            commandResult = command.execute(model);
        }

        try {
            storage.saveAppData(model.getAppData());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
        return commandResult;
    }


    @Override
    public ReadOnlyAppData getAppData() {
        return model.getAppData();
    }

    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return model.getFilteredNoteList();
    }

    @Override
    public ObservableList<PieChart.Data> getStatsChartData() {
        return model.getStatsChartData();
    }

    @Override
    public ObservableList<TempStatsQnsModel> getStatsQnsList() {
        return model.getStatsQnsList();
    }

    @Override
    public int getTotalQuestionsDone() {
        return model.getTotalQuestionsDone();
    }

    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        return model.getFilteredQuestionList();
    }

    @Override
    public Path getAppDataFilePath() {
        return model.getAppDataFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    public static void setIsQuiz(boolean isQuiz) {
        LogicManager.isQuiz = isQuiz;
    }

    public boolean getIsQuiz() {
        return isQuiz;
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Question> getFilteredQuizQuestionList() {
        return model.getFilteredQuizQuestionList();
    }

    @Override
    public ObservableList<Question> getFirstQuizQuestionAsList() {
        return model.getFirstQuizQuestionAsList();
    }
}
