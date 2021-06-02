package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Commons.ColorMarble;

public final class ResourceLocator {
    public static String retrieveDevCardImage(String ID) {
        switch (ID) {
            case "1B-1" : return "gui/img/devCards/1B-1.png";
            case "1B-2" : return "gui/img/devCards/1B-2.png";
            case "1B-3" : return "gui/img/devCards/1B-3.png";
            case "1B-4" : return "gui/img/devCards/1B-4.png";
            case "1G-1" : return "gui/img/devCards/1G-1.png";
            case "1G-2" : return "gui/img/devCards/1G-2.png";
            case "1G-3" : return "gui/img/devCards/1G-3.png";
            case "1G-4" : return "gui/img/devCards/1G-4.png";
            case "1P-1" : return "gui/img/devCards/1P-1.png";
            case "1P-2" : return "gui/img/devCards/1P-2.png";
            case "1P-3" : return "gui/img/devCards/1P-3.png";
            case "1P-4" : return "gui/img/devCards/1P-4.png";
            case "1Y-1" : return "gui/img/devCards/1Y-1.png";
            case "1Y-2" : return "gui/img/devCards/1Y-2.png";
            case "1Y-3" : return "gui/img/devCards/1Y-3.png";
            case "1Y-4" : return "gui/img/devCards/1Y-4.png";
            case "2B-1" : return "gui/img/devCards/2B-1.png";
            case "2B-2" : return "gui/img/devCards/2B-2.png";
            case "2B-3" : return "gui/img/devCards/2B-3.png";
            case "2B-4" : return "gui/img/devCards/2B-4.png";
            case "2G-1" : return "gui/img/devCards/2G-1.png";
            case "2G-2" : return "gui/img/devCards/2G-2.png";
            case "2G-3" : return "gui/img/devCards/2G-3.png";
            case "2G-4" : return "gui/img/devCards/2G-4.png";
            case "2P-1" : return "gui/img/devCards/2P-1.png";
            case "2P-2" : return "gui/img/devCards/2P-2.png";
            case "2P-3" : return "gui/img/devCards/2P-3.png";
            case "2P-4" : return "gui/img/devCards/2P-4.png";
            case "2Y-1" : return "gui/img/devCards/2Y-1.png";
            case "2Y-2" : return "gui/img/devCards/2Y-2.png";
            case "2Y-3" : return "gui/img/devCards/2Y-3.png";
            case "2Y-4" : return "gui/img/devCards/2Y-4.png";
            case "3B-1" : return "gui/img/devCards/3B-1.png";
            case "3B-2" : return "gui/img/devCards/3B-2.png";
            case "3B-3" : return "gui/img/devCards/3B-3.png";
            case "3B-4" : return "gui/img/devCards/3B-4.png";
            case "3G-1" : return "gui/img/devCards/3G-1.png";
            case "3G-2" : return "gui/img/devCards/3G-2.png";
            case "3G-3" : return "gui/img/devCards/3G-3.png";
            case "3G-4" : return "gui/img/devCards/3G-4.png";
            case "3P-1" : return "gui/img/devCards/3P-1.png";
            case "3P-2" : return "gui/img/devCards/3P-2.png";
            case "3P-3" : return "gui/img/devCards/3P-3.png";
            case "3P-4" : return "gui/img/devCards/3P-4.png";
            case "3Y-1" : return "gui/img/devCards/3Y-1.png";
            case "3Y-2" : return "gui/img/devCards/3Y-2.png";
            case "3Y-3" : return "gui/img/devCards/3Y-3.png";
            case "3Y-4" : return "gui/img/devCards/3Y-4.png";
            default : return null; // image not found
        }
    }

    public static String retrieveLeaderCardImage(String ID){
        switch(ID) {
            case "SA-SE" : return "gui/img/leaderCards/SA-SE.png";
            case "SA-SH" : return "gui/img/leaderCards/SA-SH.png";
            case "SA-ST" : return "gui/img/leaderCards/SA-ST.png";
            case "SA-CO" : return "gui/img/leaderCards/SA-CO.png";
            case "ED-SE" : return "gui/img/leaderCards/ED-SE.png";
            case "ED-SH" : return "gui/img/leaderCards/ED-SH.png";
            case "ED-ST" : return "gui/img/leaderCards/ED-ST.png";
            case "ED-CO" : return "gui/img/leaderCards/ED-CO.png";
            case "CM-SE" : return "gui/img/leaderCards/CM-SE.png";
            case "CM-SH" : return "gui/img/leaderCards/CM-SH.png";
            case "CM-ST" : return "gui/img/leaderCards/CM-ST.png";
            case "CM-CO" : return "gui/img/leaderCards/CM-CO.png";
            case "EP-SE" : return "gui/img/leaderCards/EP-SE.png";
            case "EP-SH" : return "gui/img/leaderCards/EP-SH.png";
            case "EP-ST" : return "gui/img/leaderCards/EP-ST.png";
            case "EP-CO" : return "gui/img/leaderCards/EP-CO.png";
            default : return null; // image not found
        }
    }

    public static String retrieveMarbleImage(ColorMarble colorMarble){
        switch (colorMarble) {
            case RED : return "gui/img/marbles/redMarble.png";
            case BLUE: return "gui/img/marbles/blueMarble.png";
            case GREY: return "gui/img/marbles/greyMarble.png";
            case PURPLE: return "gui/img/marbles/purpleMarble.png";
            case WHITE: return "gui/img/marbles/whiteMarble.png";
            case YELLOW: return "gui/img/marbles/yellowMarble.png";
            default: return null;
        }
    }
}
