package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Exceptions.MoveResourcesException;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.InitialLeaderChoiceUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.InitialResourceChoiceUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.MoveUpdate;
import it.polimi.ingsw.Utils.MoveActionInterface;
import it.polimi.ingsw.Utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class Player {
    private final Logger LOGGER = Logger.getLogger(Player.class.getName());
    private final User user;
    private int position;
    private final PersonalBoard personalBoard;
    private final List<LeaderCard> leaderCards = new ArrayList<>();

    public Player(String username){
        this.user = new User(username);
        this.personalBoard = new PersonalBoard(this);
    }

    private int calcVictoryPointsLeaderCard()
    {
        return leaderCards.stream().filter(LeaderCard::isActive).map(LeaderCard::getVictoryPoints).reduce(0,Integer::sum);
    }

    public int calcVictoryPointsPlayer()
    {
        return calcVictoryPointsLeaderCard()+this.personalBoard.calcVictoryPoints();
    }

    public void moveResources(Game game, MoveActionInterface move) throws MoveResourcesException {
        if(move.handleMove(this.personalBoard.getWarehouse())){
            game.notifyUpdate(new MoveUpdate(user, personalBoard.getReducedVersion()));
        }
        else throw new MoveResourcesException();
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
    public LeaderCard discardLeaderCard(int index) {
        if (!this.leaderCards.get(index).isActive())
        {
            this.personalBoard.getFaithTrack().moveMarker(1);
            return this.leaderCards.remove(index);
        }
        else return null;
    }

    public void performInitialLeaderChoice(Game game ,int firstLeader, int secondLeader) {
        throwLeaderCard(firstLeader);
        throwLeaderCard(secondLeader);

        game.notifyUpdate(new InitialLeaderChoiceUpdate(user, personalBoard.getReducedVersion(),
                firstLeader, secondLeader));
    }

    public void performInitialResourcesChoice(Game game,List<Pair<ResourceType,Integer>> chosenResources) {
        chosenResources.forEach( x -> {
                    try {
                        getPersonalBoard().getWarehouse().addResourcesToDepot(x.getValue(), x.getKey(), 1);
                    } catch (DepotOutOfBoundsException | IncompatibleResourceTypeException e) {
                        LOGGER.log(Level.SEVERE, "Thrown an unexpected exception " + e);
                    }
                });

        game.notifyUpdate(new InitialResourceChoiceUpdate(user, getPersonalBoard().getReducedVersion(),
                getChosenResources(chosenResources)));
    }

    private List<ResourceType> getChosenResources(List<Pair<ResourceType,Integer>> chosenResources) {
        return chosenResources.stream().map(Pair::getKey).collect(Collectors.toList());
    }

    public ReducedPersonalBoard getReducedPersonalBoard()
    {
        return this.getPersonalBoard().getReducedVersion();
    }

    private void throwLeaderCard(int leader) {
        leaderCards.remove(leader - 1);
    }

    public User getUser() { return this.user; }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public int getPosition() {
        return position;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }


    public void setPosition(int position) {
        this.position = position;
    }


    public List<LeaderEffect> getActiveEffects () {
        return leaderCards.stream().filter(LeaderCard::isActive).map(LeaderCard::getLeaderEffect).collect(Collectors.toList());
    }

    public ReducedPlayer getReducedVersion() {
        return new ReducedPlayer(user,personalBoard.getReducedVersion(),position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return position == player.position && user.equals(player.user) && personalBoard.equals(player.personalBoard) && leaderCards.equals(player.leaderCards);
    }

    @Override
    public String toString() {
        return "Player{" +
                "username=" + user.getNickname() +
                ", position=" + position +
                '}';
    }
}
