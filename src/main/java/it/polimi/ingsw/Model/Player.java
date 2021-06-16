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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class Player {
    private final Logger LOGGER = Logger.getLogger(Player.class.getName());

    /**
     * the player's user identity
     */
    private final User user;

    /**
     * the player's position in the turn logic
     */
    private int position;

    /**
     * the player's personal board
     */
    private final PersonalBoard personalBoard;

    /**
     * the player's leader cards
     */
    private final List<LeaderCard> leaderCards = new ArrayList<>();

    public Player(String username){
        this.user = new User(username);
        this.personalBoard = new PersonalBoard(this);
    }

    /**
     * Calculates victory points obtained from the leader cards summing the victory points given
     * from each leader card.
     * @return the result calculated
     */
    private int calcVictoryPointsLeaderCard() {
        return leaderCards.stream().filter(LeaderCard::isActive).map(LeaderCard::getVictoryPoints).reduce(0,Integer::sum);
    }

    /**
     * Calculates victory points obtained from the player summing the victory points obtained from
     * the leader cards and from personal board.
     * @return the result calculated
     */
    public int calcVictoryPointsPlayer() {
        return calcVictoryPointsLeaderCard()+this.personalBoard.calcVictoryPoints();
    }

    /**
     * Performs the move action specified calling the handleMove method passing the
     * player's warehouse where to perform the move.
     *
     * @param game the game instance, used to notify clients sending them an update
     *             when the move action has been correctly performed
     * @param move the move action object, containing all the infos needed to perform the move
     * @throws MoveResourcesException when an error occurred while performing the move action
     */
    public void moveResources(Game game, MoveActionInterface move) throws MoveResourcesException {
        if(move.handleMove(this.personalBoard.getWarehouse())){
            game.notifyUpdate(new MoveUpdate(user, personalBoard.getReducedVersion()));
        }
        else throw new MoveResourcesException();
    }

    /**
     * Checks if the requirements of the card specified are satisfied by the player
     * @param index of the leader card to check
     */
    public boolean checkLeaderActivation(int index) {
        return personalBoard.checkLeaderRequirements(leaderCards.get(index));
    }

    /**
     * Checks if a leader card can be activated. A leader card that is already active cannot
     * be activated again.
     *
     * @param index the index of the leader card to check
     */
    public boolean checkLeaderStatus(int index) {
        if(index>=leaderCards.size()) return false;
        if(this.leaderCards.get(index).isActive()) return false;
        return true;
    }

    /**
     * Activates a leader card. If the leader card has an effect of type EXTRADEPOT,
     * instantiates the extra depot in the player's warehouse.
     * This method is called only after having checked all the requirements are satisfied.
     *
     * @param index the index of the leader card to activate
     */
    public void activateLeaderCard(int index) {
            this.leaderCards.get(index).setIsActive();
            if (leaderCards.get(index).getLeaderEffect().getEffect() == Effect.EXTRADEPOT)
                this.personalBoard.getWarehouse().initializeExtraDepot(leaderCards.get(index).getLeaderEffect().getType());
    }


    /**
     * Discards a leader card only if it isn't active. If the card is correctly discarded,
     * also moves the faith marker of the player by one according to game rules.
     *
     * @param index the index of the leader card to discard
     * @return the leader card discarded
     */
    public LeaderCard discardLeaderCard(int index) {
        if (!this.leaderCards.get(index).isActive()) {
            this.personalBoard.getFaithTrack().moveMarker(this, 1);
            return this.leaderCards.remove(index);
        }
        else return null;
    }

    /**
     * Performs the initial leader choice discarding two leader cards specified by the
     * two indexes passed as parameters
     *
     * @param game the game instance, used to notify the clients sending them an update
     * @param firstLeader the index of the first leader card to discard
     * @param secondLeader the index of the second leader card to discard
     */
    public void performInitialLeaderChoice(Game game ,int firstLeader, int secondLeader) {
        throwLeaderCard(firstLeader);
        throwLeaderCard(secondLeader);

        game.notifyUpdate(new InitialLeaderChoiceUpdate(user, personalBoard.getReducedVersion(),
                firstLeader, secondLeader));
    }

    /**
     * Performs the initial resources choice positioning each resource in the depot specified
     *
     * @param game the game instance, used to notify the clients sending them an update
     * @param chosenResources list containing, for each resource, the depot index where to put the resource
     */
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

    /**
     * Method used to retrieve only the chosen resources from the list of pair containing also
     * the depot index where to put the resource. This method has been used in order to clear the
     * infos to notify to the client from unnecessary data.
     *
     * @param chosenResources list containing, for each resource, the depot index where to put the resource
     * @return the list of the chosen resources
     */
    private List<ResourceType> getChosenResources(List<Pair<ResourceType,Integer>> chosenResources) {
        return chosenResources.stream().map(Pair::getKey).collect(Collectors.toList());
    }

    public ReducedPersonalBoard getReducedPersonalBoard() {
        return this.getPersonalBoard().getReducedVersion();
    }

    /**
     * throws a leader card
     *
     * @param leader the index of the leader card to throw
     */
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


    /**
     * Gets only the active leader effects of the player
     * @return the list of active leader effects
     */
    public List<LeaderEffect> getActiveEffects () {
        return leaderCards.stream().filter(LeaderCard::isActive).map(LeaderCard::getLeaderEffect).collect(Collectors.toList());
    }

    /**
     * Gets the serializable version of this player class instance
     * @return the serializable version of the player to send through network
     */
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
