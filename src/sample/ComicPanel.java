package sample;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

import javax.swing.plaf.PanelUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ComicPanel extends Pane {


    ImageView leftCharacterView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));
    ImageView rightCharacterView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));
    BorderPane leftCharacterWrapper = new BorderPane(leftCharacterView);
    BorderPane rightCharacterWrapper = new BorderPane(leftCharacterView);

    public ComicPanel() throws FileNotFoundException {
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px");
        this.setPrefHeight(280);
        this.setPrefWidth(280);
    }

    public ImageView getLeftCharacterView() {
        return leftCharacterView;
    }

    public void setLeftCharacter(String imagePath) throws FileNotFoundException {
        this.getChildren().remove(leftCharacterWrapper);
        Image image = new Image(new FileInputStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setY(this.getTranslateY() + 100);
        imageView.setX(this.getTranslateX() + 10);
        leftCharacterWrapper = new BorderPane(imageView);
        leftCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
        leftCharacterWrapper.setTranslateX(this.getTranslateX() + 10);
        this.getChildren().add(leftCharacterWrapper);
        leftCharacterWrapper.setStyle("-fx-border-color: cyan");
        rightCharacterWrapper.setStyle("-fx-border-color: white");
    }

    public ImageView getRightCharacter() {
        return rightCharacterView;
    }

    public void setRightCharacter(String imagePath) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setY(this.getTranslateY() + 100);
        imageView.setX(this.getTranslateX() + 170);
        imageView.setRotationAxis(Rotate.Y_AXIS);
        imageView.setRotate(180);
        rightCharacterWrapper = new BorderPane(imageView);
        rightCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
        rightCharacterWrapper.setTranslateX(this.getTranslateX() + 170);
        this.getChildren().add(rightCharacterWrapper);
        leftCharacterWrapper.setStyle("-fx-border-color: white");
        rightCharacterWrapper.setStyle("-fx-border-color: cyan");
    }

    public void flipOrientation(String character){

        ImageView flipCharacter;

        if(character.matches("left"))
            flipCharacter = (ImageView) leftCharacterWrapper.getChildren().get(0);
        else
            flipCharacter = (ImageView) rightCharacterWrapper.getChildren().get(0);

        flipCharacter.setRotationAxis(Rotate.Y_AXIS);
        if(flipCharacter.getRotate() == 180)
            flipCharacter.setRotate(0);
        else
            flipCharacter.setRotate(180);

        if(character.matches("left")) {
            leftCharacterWrapper = new BorderPane(flipCharacter);
            leftCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            leftCharacterWrapper.setTranslateX(this.getTranslateX() + 10);
            leftCharacterWrapper.setStyle("-fx-border-color: cyan");
            this.getChildren().add(leftCharacterWrapper);
        }
        else {
            rightCharacterWrapper = new BorderPane(flipCharacter);
            rightCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            rightCharacterWrapper.setTranslateX(this.getTranslateX() + 170);
            rightCharacterWrapper.setStyle("-fx-border-color: cyan");
            this.getChildren().add(rightCharacterWrapper);
        }

    }

    public void selectCharacter(String character){

        System.out.println("Character: " + character);

        if(character.matches("left")) {
            System.out.println("LEFTS");
            leftCharacterWrapper.setStyle("-fx-border-color: cyan");
            rightCharacterWrapper.setStyle("-fx-border-color: white");
        }
        else if(character.matches("right")) {
            rightCharacterWrapper.setStyle("-fx-border-color: cyan");
            leftCharacterWrapper.setStyle("-fx-border-color: white");
        }
        else {
            leftCharacterWrapper.setStyle("-fx-border-color: white");
            rightCharacterWrapper.setStyle("-fx-border-color: white");
        }
    }

}
