package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.Card.LeaderEffect;
import it.polimi.ingsw.Utils.MoveActionInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Player {
    private String username;
    private int position;
    private PersonalBoard personalBoard;
    private List<LeaderCard> leaderCards = new ArrayList<>();

    public Player(String username){
        this.username = username;
        this.initializePersonalBoard();
    }


    public void moveResources(MoveActionInterface move) {
        if(move.handleMove(this.personalBoard.getWarehouse())){
            /*TODO notificare al client che la mossa di move è stata correttamente eseguita*/
        }
        else{
            // TODO notificare al client che vi è stato un errore nell'esecuzione della move
        }
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

    private void initializePersonalBoard()
    {
        this.personalBoard = new PersonalBoard(this);
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public List<LeaderEffect> getActiveEffects () {
        return leaderCards.stream().filter(LeaderCard::isActive).map(LeaderCard::getLeaderEffect).collect(Collectors.toList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return position == player.position && username.equals(player.username) && personalBoard.equals(player.personalBoard) && leaderCards.equals(player.leaderCards);
    }

}
