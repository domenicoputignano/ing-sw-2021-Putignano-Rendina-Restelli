package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Model.SoloMode.Token;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;

import java.util.List;
import java.util.Stack;

public class ReducedSoloMode extends ReducedGame {
    private int blackCross;
    private Stack<Token> tokens;


    public ReducedSoloMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray, Stack<Token> tokens) {
        super(players,decks,marketTray, true);
        this.tokens = tokens;
    }


    @Override
    public void nextTurn(NewTurnUpdate message) {
        currPlayer = this.getPlayer(message.getCurrentUser());
        //TODO: aggiornare lo stato della personal board, con i dati di lorenzo. Metodo chiamato con il messaggio
        //lorenzoPlayedUpdate();
    }
}
