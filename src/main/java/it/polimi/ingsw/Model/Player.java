package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.Card.LeaderEffect;
import it.polimi.ingsw.Utils.MoveActionInterface;
import it.polimi.ingsw.Utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class Player {
    private Logger LOGGER = Logger.getLogger(Player.class.getName());
    private String username;
    private int position;
    private PersonalBoard personalBoard;
    private List<LeaderCard> leaderCards = new ArrayList<>();

    public Player(String username){
        this.username = username;
        this.personalBoard = new PersonalBoard(this);
    }


    public void moveResources(MoveActionInterface move) {
        if(move.handleMove(this.personalBoard.getWarehouse())){
            /*TODO notificare al client che la mossa di move è stata correttamente eseguita*/
        }
        else{
            // TODO notificare al client che vi è stato un errore nell'esecuzione della move
        }
    }

    public boolean checkLeaderActivation(int index)
    {
        return personalBoard.checkLeaderRequirements(leaderCards.get(index));
    }

    public boolean checkLeaderStatus(int index)
    {
        if(index>=leaderCards.size()) return false;
        if(this.leaderCards.get(index).isActive()) return false;
        return true;
    }

    public void activateLeaderCard(int index) {
            this.leaderCards.get(index).setIsActive();
            if (leaderCards.get(index).getLeaderEffect().getEffect() == Effect.EXTRADEPOT)
                this.personalBoard.getWarehouse().initializeExtraDepot(leaderCards.get(index).getLeaderEffect().getType());
    }
    public void discardLeaderCard(int index) {
        if (!this.leaderCards.get(index).isActive())
        {
            this.leaderCards.remove(index);
            this.personalBoard.getFaithTrack().moveMarker(1);
        }

    }

    public void performInitialLeaderChoice(int firstLeader, int secondLeader) {
        throwLeaderCard(firstLeader);
        throwLeaderCard(secondLeader);
    }

    public void performInitialResourcesChoice(List<Pair<ResourceType,Integer>> chosenResources) {
        chosenResources.forEach( x -> {
                    try {
                        getPersonalBoard().getWarehouse().addResourcesToDepot(x.getValue(), x.getKey(), 1);
                    } catch (DepotOutOfBoundsException | IncompatibleResourceTypeException e) {
                        LOGGER.log(Level.SEVERE, "Thrown an unexpected exception " + e);
                    }
                });
    }


    private void throwLeaderCard(int leader) {
        leaderCards.remove(leader - 1);
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

    //private void initializePersonalBoard()
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
