package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.User;

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
}
