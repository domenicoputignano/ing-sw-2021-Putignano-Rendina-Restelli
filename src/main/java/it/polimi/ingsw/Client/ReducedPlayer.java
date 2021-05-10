package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.User;

import java.util.List;

public class ReducedPlayer {

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

    public ReducedPersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public void updatePersonalBoard(ReducedPersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    public int getPosition() {
        return position;
    }

    public List<LeaderCard> getCompatibleLeaderEffect(Effect effect) {
        return personalBoard.findEffect(effect);
    }

}
