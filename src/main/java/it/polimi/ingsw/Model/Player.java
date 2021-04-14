package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.Card.LeaderEffect;
import java.util.List;
import java.util.stream.Collectors;


public class Player {
    private String username;
    private int position;
    private PersonalBoard personalBoard;
    private List<LeaderCard> leaderCards;


    public void takeResourcesFromMarket() {

    }

    public void buyDevCard(Deck deck, int slot) {
    }

    public void startActiveProduction(ActiveProductions mask) {

    }

    public void activateLeaderCard(int index) {
        this.leaderCards.get(index).setIsActive();
        if(leaderCards.get(index).getLeaderEffect().getEffect() == Effect.EXTRADEPOT)
            this.personalBoard.getWarehouse().initializeExtraDepot(leaderCards.get(index).getLeaderEffect().getType());
    }
    public void discardLeaderCard(int index) {
        this.leaderCards.remove(index);
        this.personalBoard.getFaithTrack().moveMarker(1);
    }

    public void throwLeaderCard(LeaderCard card) {
        leaderCards.remove(card);
    }

    public String getUsername() {
        return username;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public int getPosition() {
        return position;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public void initializePersonalBoard()
    {
        this.personalBoard = new PersonalBoard();
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public List<LeaderEffect> getActiveEffects () {
        return leaderCards.stream().filter(x -> x.isActive()).map(x -> x.getLeaderEffect()).collect(Collectors.toList());
    }



}
