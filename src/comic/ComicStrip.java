package comic;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ComicStrip extends HBox{

    public ComicStrip(double width, double height) {

        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(height * 0.6 - 20);
        this.setPrefWidth(width - 20);
        this.getStylesheets().add("main/style.css");
        this.getStyleClass().add("comicStrip");

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.setDragDetect(true);
            }
        });
    }
}
