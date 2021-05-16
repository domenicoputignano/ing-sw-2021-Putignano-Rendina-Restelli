package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.User;

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

    public int getNumOfAvailableLeaderCards() {  return personalBoard.getNumOfAvailableLeaderCards(); }

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
