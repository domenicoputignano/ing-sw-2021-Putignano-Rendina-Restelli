package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Commons.ResourceType;

public enum ANSI_Color {
        GREY("\u001B[37m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m");

        public static final String RESET = "\u001B[0m";

        private String escape;

        ANSI_Color(String escape) {
            this.escape = escape;
        }
        public String escape(){
            return escape;
        }

        public static String escape(ResourceType type) {
            if (type == ResourceType.coin) return YELLOW.escape();
            if (type == ResourceType.servant) return PURPLE.escape();
            if (type == ResourceType.shield) return BLUE.escape();
            if (type == ResourceType.stone) return GREY.escape();
            else return "";
        }
}