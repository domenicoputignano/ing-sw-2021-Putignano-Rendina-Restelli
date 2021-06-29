package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.Effect;
import it.polimi.ingsw.commons.LeaderCard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.UpdateMessage;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents a simplified version of a Player with respect to the server's class.
 * It contains only the information required client side.
 */
public class ReducedPlayer implements Serializable {
    /**
     * Attributes that represent the contents of the Player
     */
    private User nickname;
    private ReducedPersonalBoard personalBoard;
    private int position;

    /**
     * Initialize an instance by setting nickname,personalBoard,position of the Player.
     */
    public ReducedPlayer(User nickname, ReducedPersonalBoard personalBoard, int position) {
        this.nickname = nickname;
        this.personalBoard = personalBoard;
        this.position = position;
    }

    public User getNickname() {
        return nickname;
    }

    /**
     * method that returns the leader cards available
     * @return list of available leader cards
     */
    public List<LeaderCard> getAvailableLeaderCards() { return personalBoard.getAvailableLeaderCards(); }

    public ReducedPersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * method that returns the number of active leader cards
     * @return number of active leader cards
     */
    public int getNumOfNotActiveLeaderCards() {  return personalBoard.getNumOfNotActiveLeaderCards(); }

    /**
     * method that returns the number of leader cards
     * @return number of leader cards
     */
    public int getNumOfLeaderCards() { return personalBoard.getNumOfLeaderCards(); }

    /**
     * method used to update the PersonalBoard after an action has been performed
     * @param personalBoard reduced personal Board
     * @see ReducedPersonalBoard
     */
    public void updatePersonalBoard(ReducedPersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    /**
     * method that checks if a leader action can be performed
     * @return true if a leader action can be performed, false otherwise
     */
    public boolean isAvailableLeaderAction() {
        return personalBoard.isAvailableLeaderAction();
    }

    public int getPosition() {
        return position;
    }

    public List<LeaderCard> getCompatibleLeaderEffect(Effect effect) {
        return personalBoard.findEffect(effect);
    }

}
