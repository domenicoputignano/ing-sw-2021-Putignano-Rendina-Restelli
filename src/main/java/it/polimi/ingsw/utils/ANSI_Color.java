package it.polimi.ingsw.utils;

import it.polimi.ingsw.commons.ColorCard;
import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.commons.ResourceType;

/**
 * Enum that allows colors usage in command line interface.
 */
public enum ANSI_Color {
    GREY("\u001b[38;5;240m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    RED("\u001b[31m");

    public static final String RESET = "\u001B[0m";

    private String escape;

    ANSI_Color(String escape) {
        this.escape = escape;
    }
    public String escape(){
        return escape;
    }

    /**
     * Returns a string representing ANSI sequence of color associated to a certain resource type.
     * @param type resource type to be displayed.
     */
    public static String escape(ResourceType type) {
        if (type == ResourceType.coin) return YELLOW.escape()+"coin"+RESET;
        if (type == ResourceType.servant) return PURPLE.escape()+"servant"+RESET;
        if (type == ResourceType.shield) return BLUE.escape()+"shield"+RESET;
        if (type == ResourceType.stone) return GREY.escape()+"stone"+RESET;
        else return "";
    }

    /**
     * Returns a string representing ANSI sequence of color related to a certain marble.
     * @param color color of marble to be displayed.
     */
    public static String escape(ColorMarble color) {
        if(color == ColorMarble.RED) return RED.escape();
        if(color == ColorMarble.BLUE) return BLUE.escape();
        if(color == ColorMarble.GREY) return GREY.escape();
        if(color == ColorMarble.PURPLE) return PURPLE.escape();
        if(color == ColorMarble.YELLOW) return YELLOW.escape();
        else return "";
    }

    /**
     * Returns a string representing ANSI sequence of color related to a certain card
     * @param color color of the card to be displayed.
     */
    public static String escape(ColorCard color) {
        if(color == ColorCard.green) return GREEN.escape();
        if(color == ColorCard.blue) return BLUE.escape();
        if(color == ColorCard.yellow) return YELLOW.escape();
        if(color == ColorCard.purple) return PURPLE.escape();
        else return "";
    }
}
