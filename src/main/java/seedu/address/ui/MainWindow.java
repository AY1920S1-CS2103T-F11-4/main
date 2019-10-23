package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.statistics.Type;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.statistics.StackBarChart;
import seedu.address.ui.statistics.StatsPieChart;
import seedu.address.ui.statistics.StatsQnsList;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private NoteListPanel noteListPanel;
    private TaskListPanel taskListPanel;
    private QuestionListPanel questionListPanel;
    private QuizQuestionListPanel quizQuestionListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatsPieChart statsPieChart;
    private StackBarChart stackBarChart;
    private StatsQnsList statsQnsList;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane noteListPanelPlaceholder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane statsPanelPlaceholder;

    @FXML
    private StackPane questionListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private HBox mainPanel;

    @FXML
    private VBox stats;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        stats.setVisible(false);

        noteListPanel = new NoteListPanel(logic.getFilteredNoteList());
        noteListPanelPlaceholder.getChildren().add(noteListPanel.getRoot());

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        questionListPanel = new QuestionListPanel(logic.getFilteredQuestionList());
        questionListPanelPlaceholder.getChildren().add(questionListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAppDataFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Shows a list of quiz questions, replace the panel of questions.
     */
    @FXML
    private void showQuiz() {
        questionListPanelPlaceholder.getChildren().clear();
        quizQuestionListPanel = new QuizQuestionListPanel(logic.getFilteredQuizQuestionList());
        questionListPanelPlaceholder.getChildren().add(quizQuestionListPanel.getRoot());
    }

    /**
     * Shows a list of questions when the quiz mode is not activated.
     */
    @FXML
    private void showQuestion() {
        questionListPanelPlaceholder.getChildren().clear();
        questionListPanel = new QuestionListPanel(logic.getFilteredQuestionList());
        questionListPanelPlaceholder.getChildren().add(questionListPanel.getRoot());
    }

    /**
     * Shows a pie chart and returns the value of the data
     * in each slice of the chart when the mouse hovers over it.
     */
    @FXML
    private void showStats(Type type) throws ParseException {
        mainPanel.setVisible(false);
        stats.setVisible(true);
        switch (type) {
        case STATS:
            statsPanelPlaceholder.getChildren().clear();
            statsPieChart = new StatsPieChart(logic.getStatsPieChartData(), logic.getTotalQuestionsDone());
            statsPanelPlaceholder.getChildren().add(statsPieChart.getRoot());
            statsPieChart.getChart().getData().forEach(data -> {
                String value = "" + (int) data.getPieValue();
                Tooltip toolTip = new Tooltip(value);
                toolTip.setStyle("-fx-font-size: 20");
                toolTip.setShowDelay(Duration.seconds(0));
                Tooltip.install(data.getNode(), toolTip);
            });
            break;
        case QUESTIONS:
            statsPanelPlaceholder.getChildren().clear();
            statsQnsList = new StatsQnsList(logic.getStatsQnsList());
            statsPanelPlaceholder.getChildren().add(statsQnsList.getLabel());
            break;
        case OVERVIEW:
            statsPanelPlaceholder.getChildren().clear();
            stackBarChart = new StackBarChart(logic.getStackBarChartData(), logic.getTotalQuestionsDone());
            statsPanelPlaceholder.getChildren().add(statsPieChart.getRoot());
            break;
        default:
            throw new ParseException("Invalid type: " + type);
        }

    }

    /**
     * Remove the pie chart from the noteListPanel.
     */
    @FXML
    private void removeStats() {
        mainPanel.setVisible(true);
        stats.setVisible(false);
    }

    public NoteListPanel getNoteListPanel() {
        return noteListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isQuiz()) {
                showQuiz();
            } else {
                showQuestion();
            }

            if (commandResult.isShowStats()) {
                showStats(commandResult.getType());
            } else {
                removeStats();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
