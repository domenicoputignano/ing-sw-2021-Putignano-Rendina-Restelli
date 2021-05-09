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


}
