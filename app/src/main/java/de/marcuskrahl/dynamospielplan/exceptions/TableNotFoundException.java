package de.marcuskrahl.dynamospielplan.exceptions;

public class TableNotFoundException extends Exception {
    public TableNotFoundException() {
        super("The table tag was not found in the HTML string");
    }
}
