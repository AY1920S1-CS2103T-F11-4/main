package seedu.address.ui.statistics;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.ui.UiPart;

/**
 * A UI component that displays statistics.
 */
public class StatsPieChart extends UiPart<Region> {
    private static final String FXML = "StatsPieChart.fxml";

    @FXML
    private PieChart pc;
    @FXML
    private Label overview;

    public StatsPieChart(ObservableList<PieChart.Data> data, int totalQns) {
        super(FXML);
        pc.setLegendSide(Side.LEFT);
        pc.setData(data);
        overview.setText("The total number of questions answered so far: " + totalQns + "\n"
                + "Number of questions answered correctly: " + (int) data.get(0).getPieValue() + "\n"
                + "Number of questions answered incorrectly: " + (int) data.get(1).getPieValue() + "\n");
    }

    public void setMouseover() {
        pc.getData().forEach(data -> {
            String value = "" + (int) data.getPieValue();
            Tooltip toolTip = new Tooltip(value);
            toolTip.setStyle("-fx-font-size: 20");
            toolTip.setShowDelay(Duration.seconds(0));
            Tooltip.install(data.getNode(), toolTip);
        });
    }
}
