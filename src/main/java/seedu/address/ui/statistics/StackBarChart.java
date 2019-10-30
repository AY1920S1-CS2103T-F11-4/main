package seedu.address.ui.statistics;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.question.Subject;
import seedu.address.model.statistics.StackBarChartModel;
import seedu.address.ui.UiPart;

/**
 * A UI component that displays a stack bar chart.
 */
public class StackBarChart extends UiPart<Region> {
    private static final String FXML = "StackBarChart.fxml";

    @FXML
    private StackedBarChart<String, Number> bc;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private Label overview;

    public StackBarChart(ObservableList<StackBarChartModel> data, ObservableList<Subject> uniqueSubjectList) {
        super(FXML);
        int totalQuestions = 0;
        List<String> subjects = uniqueSubjectList
                .stream()
                .map(Subject::toString)
                .collect(Collectors.toList());
        categoryAxis.setCategories(FXCollections.observableArrayList(subjects));

        bc.setLegendSide(Side.LEFT);

        ObservableList<XYChart.Series<String, Number>> stackedBarChart = FXCollections.observableArrayList();

        for (StackBarChartModel m : data) {
            StackedBarChart.Series<String, Number> series = new StackedBarChart.Series<>();
            series.setName(m.getDifficulty().toString());

            for (XYChart.Data<String, Number> pair : m.getData()) {
                series.getData().add(pair);
                totalQuestions += pair.getYValue().intValue();
            }
            stackedBarChart.add(series);
        }

        bc.setData(stackedBarChart);
        overview.setText("Total number of questions done: " + totalQuestions + "\n"
                + "Total number of subjects: " + subjects.size() + "\n");
    }

    public StackedBarChart getChart() {
        return bc;
    }
}
