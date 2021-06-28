package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.Effect;
import it.polimi.ingsw.commons.LeaderCard;
import it.polimi.ingsw.commons.User;

import java.io.Serializable;
import java.util.List;

public class ReducedPlayer implements Serializable {

    private User nickname;
    private ReducedPersonalBoard personalBoard;
    private int position;


    public ReducedPlayer(User nickname, ReducedPersonalBoard personalBoard, int position) {
        this.nickname = nickname;
        this.personalBoard = personalBoard;
        this.position = position;
    }

    public User getNickname() {
        return nickname;
    }

    public List<LeaderCard> getAvailableLeaderCards() { return personalBoard.getAvailableLeaderCards(); }

    public ReducedPersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public int getNumOfNotActiveLeaderCards() {  return personalBoard.getNumOfNotActiveLeaderCards(); }

    public int getNumOfLeaderCards() { return personalBoard.getNumOfLeaderCards(); }

    public void updatePersonalBoard(ReducedPersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

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
