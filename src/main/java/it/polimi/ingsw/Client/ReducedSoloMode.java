package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.SoloMode.Token;

import java.util.Stack;

public class ReducedSoloMode extends ReducedGame {
    private int blackCross;
    private Stack<Token> tokens;


    @Override
    public void nextTurn() {
        //TODO: aggiornare lo stato della personal board, con i dati di lorenzo. Metodo chiamato con il messaggio
        //lorenzoPlayedUpdate();
    }
}
