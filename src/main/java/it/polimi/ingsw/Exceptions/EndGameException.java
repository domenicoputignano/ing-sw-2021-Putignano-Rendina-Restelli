package it.polimi.ingsw.Exceptions;

import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;

public class EndGameException extends Exception{
    private ConclusionEvent conclusionEvent;

    public EndGameException(ConclusionEvent conclusionEvent)
    {
        this.conclusionEvent = conclusionEvent;
    }

    public ConclusionEvent getConclusionEvent() {
        return conclusionEvent;
    }
}