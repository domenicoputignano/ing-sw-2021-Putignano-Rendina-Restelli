package it.polimi.ingsw.Model;

import java.io.FileNotFoundException;
import java.util.List;

public class SoloMode extends Game{
    public SoloMode(Player inkwell, List<Player> playerList, Player currPlayer, int numOfPlayers) throws FileNotFoundException {
        super(inkwell,playerList,currPlayer,numOfPlayers);
    }
}
