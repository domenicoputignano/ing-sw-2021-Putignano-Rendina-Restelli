package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.Card.LeaderEffect;
import java.util.List;



public class Player {
    private String username;
    private int position;
    private PersonalBoard personalBoard;
    private List<LeaderCard> leaderCards;
    private List<LeaderEffect> activeEffects;

    public void takeResourcesFromMarket() {

    }

    public void buyDevCard(Deck deck, int slot) {
    }

    public void startActiveProduction(ActiveProductions mask) {

    }

    public void activateLeaderCard(LeaderCard card) {
        // attualmente sostituita da equivalente metodo nel controller
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
}
