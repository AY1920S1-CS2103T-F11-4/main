package seedu.address.ui.panels;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.note.Note;

/**
 * An UI component that displays information of a {@code Note}.
 */
public class NoteCard extends PanelComponent<Region> {
    private static final String FXML = "NoteListCard.fxml";

    public final Note note;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label content;
    @FXML
    private ImageView image;

    public NoteCard(Note note, int displayedIndex) {
        super(FXML);
        this.note = note;
        id.setText(displayedIndex + ". ");
        title.setText(note.getTitle().title);
        content.setText(note.getContent().content);
        image.setImage(note.getImage());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCard)) {
            return false;
        }

        // state check
        NoteCard card = (NoteCard) other;
        return id.getText().equals(card.id.getText())
                && note.equals(card.note);
    }
}