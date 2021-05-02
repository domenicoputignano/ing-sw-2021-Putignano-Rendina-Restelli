package it.polimi.ingsw.Commons;


/* This class provides a representation
 * of the player needed at network level, when player's methods defined
 * in model classes are not required.
 */
public class User {

    private String nickname;

    public User(String nickname) {
        this.nickname = nickname;
    }

}
