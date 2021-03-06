package undo;

import comic.ComicPanel;

public class Undo {

    //Undo Class contains a variety of objects that the user can use to create an Undo object representing their Undo action

    private String operation;
    private ComicPanel comicPanel;
    private String value_1;
    private String value_2;
    private Object obj;

    public Undo() {
    }

    public Undo(String operation) {
        this.operation = operation;
    }

    public Undo(String operation, ComicPanel comicPanel) {
        this.operation = operation;
        this.comicPanel = comicPanel;
    }

    public Undo(String operation, ComicPanel comicPanel, String value_1) {
        this.operation = operation;
        this.comicPanel = comicPanel;
        this.value_1 = value_1;
    }

    public Undo(String operation, ComicPanel comicPanel, String value_1, String value_2) {
        this.operation = operation;
        this.comicPanel = comicPanel;
        this.value_1 = value_1;
        this.value_2 = value_2;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public ComicPanel getComicPanel() {
        return comicPanel;
    }

    public void setComicPanel(ComicPanel comicPanel) {
        this.comicPanel = comicPanel;
    }

    public String getValue_1() {
        return value_1;
    }

    public void setValue_1(String value_1) {
        this.value_1 = value_1;
    }

    public String getValue_2() {
        return value_2;
    }

    public void setValue_2(String value_2) {
        this.value_2 = value_2;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
