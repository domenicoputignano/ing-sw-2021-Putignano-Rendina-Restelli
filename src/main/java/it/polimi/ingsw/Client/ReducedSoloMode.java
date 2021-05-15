package it.polimi.ingsw.Client;

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
        //TODO: aggiornare lo stato della personal board, con i dati di lorenzo. Metodo chiamato con il messaggio
        //lorenzoPlayedUpdate();
    }
}
