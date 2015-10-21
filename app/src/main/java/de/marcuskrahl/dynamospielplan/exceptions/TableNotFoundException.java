package de.marcuskrahl.dynamospielplan.exceptions;

public class TableNotFoundException extends HtmlParseException {
    public TableNotFoundException() {
        super("The table tag was not found in the HTML string");
    }
}
