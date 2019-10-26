package seedu.address.ui.statistics;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.quiz.QuizResult;
import seedu.address.ui.UiPart;

/**
 * Temporary panel for returning correct/incorrect questions.
 */
public class StatsQns extends UiPart<Region> {
    private static final String FXML = "StatsQns.fxml";
    private final Logger logger = LogsCenter.getLogger(StatsQns.class);

    @FXML
    private ListView quizResultListView;
    @FXML
    private PieChart quizResultDifficultyChart;

    public StatsQns(ObservableList<QuizResult> quizResultList, ObservableList<PieChart.Data> data) {
        super(FXML);
        quizResultListView.setItems(quizResultList);
        quizResultListView.setCellFactory(listView -> new StatsQns.QuizResultListViewCell());
        quizResultDifficultyChart.setData(data);
        quizResultDifficultyChart.setTitle("Difficulty levels");
    }

    public void setMouseover() {
        quizResultDifficultyChart.getData().forEach(data -> {
            String value = "" + (int) data.getPieValue();
            Tooltip toolTip = new Tooltip(value);
            toolTip.setStyle("-fx-font-size: 20");
            toolTip.setShowDelay(Duration.seconds(0));
            Tooltip.install(data.getNode(), toolTip);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code QuizResult} using a {@code StatsQnsCard}.
     */
    class QuizResultListViewCell extends ListCell<QuizResult> {
        @Override
        protected void updateItem(QuizResult quizResult, boolean empty) {
            super.updateItem(quizResult, empty);

            if (empty || quizResult == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StatsQnsListCard(quizResult, getIndex() + 1).getRoot());
            }
        }
    }

}