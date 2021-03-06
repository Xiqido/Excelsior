package comic;

import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import undo.Undo;
import undo.UndoList;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

public class ComicPanel extends Pane {

    private ComicCharacter leftCharacter = new ComicCharacter();
    private ComicCharacter rightCharacter =  new ComicCharacter();

    private ComicCharacter selectedCharacter;

    private TextBubble leftTextBubble = null;
    private TextBubble rightTextBubble = null;

    private TextCaption topText = new TextCaption("", Font.font("Segoe UI", 20));
    private TextCaption bottomText = new TextCaption("", Font.font("Segoe UI", 20));

    private String background = "images/backgrounds/BlankBackground.jpg";

    private Boolean isLocked;
    private String topBottom = null;

    private int index;

    int width = (int) Screen.getPrimary().getBounds().getWidth();
    int height = (int) Screen.getPrimary().getBounds().getHeight();

    public ComicPanel() throws FileNotFoundException {



        this.setMinHeight(height/2.4);
        this.setMinWidth(height/2.4 + height/9.6);

        this.setMaxHeight(height/2.4);
        this.setMaxWidth(height/2.4 + height/9.6);

        this.leftCharacter.setTranslateX(20);
        this.leftCharacter.setTranslateY(130);

        double rightTranslate = (height/2.4 + height/9.6);
        this.rightCharacter.setTranslateX(rightTranslate - rightTranslate/3);
        this.rightCharacter.setTranslateY(130);

        this.getChildren().add(leftCharacter);
        this.getChildren().add(rightCharacter);

        this.getChildren().add(topText);
        this.getChildren().add(bottomText);

        isLocked = false;

        this.unselect();
    }

    public void select(){
        this.getStyleClass().clear();
        this.getStyleClass().add("backgroundImage");
        this.setStyle("-fx-background-image: url('" + background + "'); " +
                "-fx-background-size: " + this.getWidth() + " " + this.getHeight() + ";" +
                "-fx-background-color: WHITE");
    }

    public void unselect(){
        this.getStyleClass().add("backgroundImage");
        this.setStyle("-fx-background-image: url('" + background + "'); " +
                "-fx-border-color: BLACK; " +
                "-fx-background-size: " + this.getWidth() + " " + this.getHeight() + ";" +
                "-fx-background-color: WHITE");
    }

    public ComicCharacter getLeftCharacter() {
        return leftCharacter;
    }

    public void setLeftCharacter(String imagePath) throws FileNotFoundException {
        leftCharacter.setCharacterImageView(imagePath);

        if(leftCharacter.getImageName().matches("blank")){
            leftCharacter.setOnMouseEntered(mouseEvent -> {
            });

            leftCharacter.setOnMouseExited(mouseEvent -> {
            });

            leftCharacter.setOnMousePressed(pressEvent -> {
                leftCharacter.setOnMouseDragged(dragEvent -> {
                });
            });

            leftCharacter.setStyle("-fx-border-width: 0");
            return;
        }

        AtomicReference<Double> dragX = new AtomicReference<>((double) 0);
        AtomicReference<Double> dragY = new AtomicReference<>((double) 0);

        leftCharacter.setOnMouseEntered(mouseEvent -> {
            leftCharacter.getScene().setCursor(Cursor.MOVE);
        });

        leftCharacter.setOnMouseExited(mouseEvent -> {
            leftCharacter.getScene().setCursor(Cursor.DEFAULT);
        });

        leftCharacter.setOnMousePressed(pressEvent -> {

            Undo undo = new Undo("moveCharacter", this, "left", this.leftCharacter.getTranslateX() + "#" + this.leftCharacter.getTranslateY());

            setSelectedCharacter(leftCharacter);

            dragX.set(0.0);
            dragY.set(0.0);
            leftCharacter.setOnMouseDragged(dragEvent -> {

                if(!UndoList.contains(undo))
                    UndoList.addUndo(undo);

                if(!this.isLocked) {
                    double offsetX = leftCharacter.getTranslateX() + dragEvent.getScreenX() - pressEvent.getScreenX() - dragX.get();
                    double offsetY = leftCharacter.getTranslateY() + dragEvent.getScreenY() - pressEvent.getScreenY() - dragY.get();

                    if (offsetX < 3)
                        offsetX = 3;
                    else if (offsetX > (this.getWidth() - 3) / 2 - leftCharacter.getWidth())
                        offsetX = (this.getWidth() - 3) / 2 - leftCharacter.getWidth();

                    if (offsetY < 3)
                        offsetY = 3;
                    else if (offsetY > this.getHeight() - 3 - leftCharacter.getHeight())
                        offsetY = this.getHeight() - 3 - leftCharacter.getHeight();


                    leftCharacter.setTranslateX(offsetX);
                    leftCharacter.setTranslateY(offsetY);
                    dragX.set(dragEvent.getScreenX() - pressEvent.getScreenX());
                    dragY.set(dragEvent.getScreenY() - pressEvent.getScreenY());
                }
            });
        });

        leftCharacter.setTranslateX(leftCharacter.getTranslateX() + dragX.get());
        leftCharacter.setTranslateY(leftCharacter.getTranslateY() + dragY.get());
    }

    public ComicCharacter getRightCharacter() {
        return rightCharacter;
    }

    public void setRightCharacter(String imagePath) throws FileNotFoundException {
        rightCharacter.setCharacterImageView(imagePath);
        rightCharacter.flipOrientation();

        if(rightCharacter.getImageName().matches("blank")){
            rightCharacter.setOnMouseEntered(mouseEvent -> {
            });

            rightCharacter.setOnMouseExited(mouseEvent -> {
            });

            rightCharacter.setOnMousePressed(pressEvent -> {
                rightCharacter.setOnMouseDragged(dragEvent -> {
                });
            });

            rightCharacter.setStyle("-fx-border-width: 0");
            return;
        }

        AtomicReference<Double> dragX = new AtomicReference<>((double) 0);
        AtomicReference<Double> dragY = new AtomicReference<>((double) 0);

        rightCharacter.setOnMouseEntered(mouseEvent -> {
            rightCharacter.getScene().setCursor(Cursor.MOVE);
        });

        rightCharacter.setOnMouseExited(mouseEvent -> {
            rightCharacter.getScene().setCursor(Cursor.DEFAULT);
        });

        rightCharacter.setOnMousePressed(pressEvent -> {

            Undo undo = new Undo("moveCharacter", this, "right", this.rightCharacter.getTranslateX() + "#" + this.rightCharacter.getTranslateY());

            setSelectedCharacter(rightCharacter);

            dragX.set(0.0);
            dragY.set(0.0);
            rightCharacter.setOnMouseDragged(dragEvent -> {

                if(!UndoList.contains(undo))
                    UndoList.addUndo(undo);

                if(!this.isLocked) {
                    double offsetX = rightCharacter.getTranslateX() + dragEvent.getScreenX() - pressEvent.getScreenX() - dragX.get();
                    double offsetY = rightCharacter.getTranslateY() + dragEvent.getScreenY() - pressEvent.getScreenY() - dragY.get();

                    if (offsetX < (this.getWidth() - 3) / 2)
                        offsetX = (this.getWidth() - 3) / 2;
                    else if (offsetX > this.getWidth() - 3 - rightCharacter.getWidth())
                        offsetX = this.getWidth() - 3 - rightCharacter.getWidth();

                    if (offsetY < 3)
                        offsetY = 3;
                    else if (offsetY > this.getHeight() - 3 - rightCharacter.getHeight())
                        offsetY = this.getHeight() - 3 - rightCharacter.getHeight();


                    rightCharacter.setTranslateX(offsetX);
                    rightCharacter.setTranslateY(offsetY);
                    dragX.set(dragEvent.getScreenX() - pressEvent.getScreenX());
                    dragY.set(dragEvent.getScreenY() - pressEvent.getScreenY());
                }
            });
        });

        rightCharacter.setTranslateX(rightCharacter.getTranslateX() + dragX.get());
        rightCharacter.setTranslateY(rightCharacter.getTranslateY() + dragY.get());
    }

    public ComicCharacter getSelectedCharacter() {
        return selectedCharacter;
    }

    public ComicCharacter getCharacter(String side){
        if(side.matches("left"))
            return leftCharacter;
        else if (side.matches("right"))
            return rightCharacter;
        return null;
    }

    public String getLeftRight() {
        if(selectedCharacter != null){
            if(selectedCharacter.equals(leftCharacter))
                return "left";
            else
                return "right";
        }

        return "";
    }

    public void setSelectedCharacter(ComicCharacter selectedCharacter) {

        if(selectedCharacter != null) {
            if (selectedCharacter.getImageName().matches("blank"))
                return;

            this.selectedCharacter = selectedCharacter;

            this.selectedCharacter.setStyle("-fx-border-width: 1; -fx-border-color: cyan");
        }
        else {
            this.selectedCharacter = selectedCharacter;
        }

        if(leftCharacter != selectedCharacter)
            leftCharacter.setStyle("-fx-border-width: 0");

        if(rightCharacter != selectedCharacter)
            rightCharacter.setStyle("-fx-border-width: 0");
    }

    public void removeBubble(){
        if(selectedCharacter.equals(leftCharacter)){
            this.getChildren().remove(leftTextBubble);
            leftTextBubble = null;
        }
        else if(selectedCharacter.equals(rightCharacter)){
            this.getChildren().remove(rightTextBubble);
            rightTextBubble = null;
        }

    }

    public void setLeftBubble(Image image, String text, Font font, String status) {
        this.getChildren().remove(leftTextBubble);

        ImageView imageView = new ImageView(image);

        leftTextBubble = new TextBubble(imageView, text, font, status);

        while(leftTextBubble.getBubble().getFitHeight() > height/2.4 -120){
            leftTextBubble.getText().setText(leftTextBubble.getText().getText().substring(0, leftTextBubble.getText().getText().length()-2));
            leftTextBubble.getBubble().setFitHeight(leftTextBubble.getText().getBoundsInParent().getHeight() + 100);
            leftTextBubble.getText().setTranslateY(20);
        }



        leftTextBubble.setTranslateX(leftCharacter.getTranslateX() + 70);
        leftTextBubble.setTranslateY(leftCharacter.getTranslateY() - 50);

        AtomicReference<Double> dragX = new AtomicReference<>((double) 0);
        AtomicReference<Double> dragY = new AtomicReference<>((double) 0);

        leftTextBubble.setOnMouseEntered(mouseEvent -> {
            leftTextBubble.getScene().setCursor(Cursor.MOVE);
        });

        leftTextBubble.setOnMouseExited(mouseEvent -> {
            leftTextBubble.getScene().setCursor(Cursor.DEFAULT);
        });

        leftTextBubble.setOnMousePressed(pressEvent -> {

            Undo undo = new Undo("moveBubble", this, "left", this.leftTextBubble.getTranslateX() + "#" + this.leftTextBubble.getTranslateY());

            dragX.set(0.0);
            dragY.set(0.0);
            leftTextBubble.setOnMouseDragged(dragEvent -> {

                if(!UndoList.contains(undo))
                    UndoList.addUndo(undo);

                if(!this.isLocked) {
                    double offsetX = leftTextBubble.getTranslateX() + dragEvent.getScreenX() - pressEvent.getScreenX() - dragX.get();
                    double offsetY = leftTextBubble.getTranslateY() + dragEvent.getScreenY() - pressEvent.getScreenY() - dragY.get();

                    if (offsetX < 3)
                        offsetX = 3;
                    else if (offsetX > this.getWidth() - 4 - leftTextBubble.getWidth())
                        offsetX = this.getWidth() - 4 - leftTextBubble.getWidth();

                    if (offsetY < 3)
                        offsetY = 3;
                    else if (offsetY > this.getHeight() - 4 - leftTextBubble.getHeight())
                        offsetY = this.getHeight() - 4 - leftTextBubble.getHeight();


                    leftTextBubble.setTranslateX(offsetX);
                    leftTextBubble.setTranslateY(offsetY);
                    dragX.set(dragEvent.getScreenX() - pressEvent.getScreenX());
                    dragY.set(dragEvent.getScreenY() - pressEvent.getScreenY());
                }
            });
        });

        leftTextBubble.setTranslateX(leftTextBubble.getTranslateX() + dragX.get());
        leftTextBubble.setTranslateY(leftTextBubble.getTranslateY() + dragY.get());
        this.getChildren().add(leftTextBubble);
    }

    public void setRightBubble(Image image, String text, Font font, String status){
        this.getChildren().remove(rightTextBubble);
        double checkS = image.getWidth() + image.getHeight();

        ImageView imageView = new ImageView(image);

        rightTextBubble = new TextBubble(imageView, text, font, status);

        while(rightTextBubble.getBubble().getFitHeight() > height/2.4 -120){
            rightTextBubble.getText().setText(rightTextBubble.getText().getText().substring(0, rightTextBubble.getText().getText().length()-2));
            rightTextBubble.getBubble().setFitHeight(rightTextBubble.getText().getBoundsInParent().getHeight() + 100);
            rightTextBubble.getText().setTranslateY(20);
        }

        rightTextBubble.getBubble().setRotationAxis(Rotate.Y_AXIS);
        rightTextBubble.getBubble().setRotate(180);

        rightTextBubble.setTranslateX(rightCharacter.getTranslateX() - 80);
        rightTextBubble.setTranslateY(rightCharacter.getTranslateY() - 50);

        AtomicReference<Double> dragX = new AtomicReference<>((double) 0);
        AtomicReference<Double> dragY = new AtomicReference<>((double) 0);

        rightTextBubble.setOnMouseEntered(mouseEvent -> {
            rightTextBubble.getScene().setCursor(Cursor.MOVE);
        });

        rightTextBubble.setOnMouseExited(mouseEvent -> {
            rightTextBubble.getScene().setCursor(Cursor.DEFAULT);
        });

        rightTextBubble.setOnMousePressed(pressEvent -> {

            Undo undo = new Undo("moveBubble", this, "right", this.rightTextBubble.getTranslateX() + "#" + this.rightTextBubble.getTranslateY());

            dragX.set(0.0);
            dragY.set(0.0);
            rightTextBubble.setOnMouseDragged(dragEvent -> {

                if(!UndoList.contains(undo))
                    UndoList.addUndo(undo);

                if(!this.isLocked) {
                    double offsetX = rightTextBubble.getTranslateX() + dragEvent.getScreenX() - pressEvent.getScreenX() - dragX.get();
                    double offsetY = rightTextBubble.getTranslateY() + dragEvent.getScreenY() - pressEvent.getScreenY() - dragY.get();
                    if (offsetX < 3)
                        offsetX = 3;
                    else if (offsetX > this.getWidth() - 4 - rightTextBubble.getWidth())
                        offsetX = this.getWidth() - 4 - rightTextBubble.getWidth();
                    if (offsetY < 3)
                        offsetY = 3;
                    else if (offsetY > this.getHeight() - 4 - rightTextBubble.getHeight())
                        offsetY = this.getHeight() - 4 - rightTextBubble.getHeight();
                    rightTextBubble.setTranslateX(offsetX);
                    rightTextBubble.setTranslateY(offsetY);
                    dragX.set(dragEvent.getScreenX() - pressEvent.getScreenX());
                    dragY.set(dragEvent.getScreenY() - pressEvent.getScreenY());
                }
            });
        });

        rightTextBubble.setTranslateX(rightTextBubble.getTranslateX() + dragX.get());
        rightTextBubble.setTranslateY(rightTextBubble.getTranslateY() + dragY.get());

        this.getChildren().add(rightTextBubble);
    }

    public void setTopText(String text, Font font){

        if(topText != null)
            topBottom = "top";
        else
            topBottom = null;

        this.getChildren().remove(topText);
        topText = new TextCaption(text, font);
        this.getChildren().add(topText);
    }

    public TextCaption getTopText() {
        return topText;
    }

    public void setBottomText(String text, Font font){
        if(bottomText != null)
            topBottom = "bottom";
        else
            topBottom = null;

        int height = (int) Screen.getPrimary().getBounds().getHeight();

        this.getChildren().remove(bottomText);
        bottomText = new TextCaption(text, font);
        bottomText.getTextObject().setTextOrigin(VPos.TOP);
        bottomText.setTranslateY(height/2.4);
        this.getChildren().add(bottomText);
    }

    public TextCaption getBottomText() {
        return bottomText;
    }

    public void removeText(boolean[] top, boolean[] bottom){
        if (top[0]){
            this.getChildren().remove(topText);
            topText = null;
        }
        else if(bottom[0]){
            this.getChildren().remove(bottomText);
            bottomText = null;
        }

    }

    //Gets if a comicPanel is locked
    public Boolean getLocked() {
        return isLocked;
    }

    //Sets if a comicPanel is locked
    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getBackgroundString() { return background; }

    public void setBackgroundString(String path) {
        this.background = path;
        this.getStyleClass().clear();
        this.getStyleClass().add("backgroundImage");
        this.setStyle("-fx-background-image: url('" + background + "'); " +
                "-fx-background-size: " + this.getWidth() + " " + this.getHeight() + ";" +
                "-fx-background-color: WHITE");
    }

    public TextBubble getLeftTextBubble() {
        return leftTextBubble;
    }

    public TextBubble getRightTextBubble() {
        return rightTextBubble;
    }

    //Gets the comicPanel's index inside of its comicStrip
    public int getIndex() {
        return index;
    }

    //Sets the comicPanel's index inside of its comicStrip
    public void setIndex(int index) {
        this.index = index;
    }

    public void setLeftTextBubble(TextBubble leftTextBubble) {
        this.getChildren().remove(this.leftTextBubble);
        this.leftTextBubble = leftTextBubble;
        if(this.leftTextBubble != null)
            this.getChildren().add(this.leftTextBubble);

    }

    public void setRightTextBubble(TextBubble rightTextBubble) {
        this.getChildren().remove(this.rightTextBubble);
        this.rightTextBubble = rightTextBubble;
        if(this.rightTextBubble != null)
            this.getChildren().add(this.rightTextBubble);

    }

    public void setTopText(TextCaption topText) {
        this.topText = topText;
    }
}
