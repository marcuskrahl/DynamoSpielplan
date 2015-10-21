package de.marcuskrahl.dynamospielplan.exceptions;

public class InvalidMatchTypeException extends HtmlParseException {
    public InvalidMatchTypeException(String matchType) {super("Invalid match type: "+matchType);}
}
