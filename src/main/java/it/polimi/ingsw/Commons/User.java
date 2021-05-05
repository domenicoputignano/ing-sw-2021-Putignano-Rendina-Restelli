package it.polimi.ingsw.Commons;


import java.util.Objects;

/* This class provides a representation
 * of the player needed at network level, when player's methods defined
 * in model classes are not required.
 */
public class User {

    private final String nickname;

    public User(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    public String getNickname() {
        return nickname;
    }
}
