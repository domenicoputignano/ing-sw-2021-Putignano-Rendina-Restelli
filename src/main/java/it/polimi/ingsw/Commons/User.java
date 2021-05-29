package it.polimi.ingsw.Commons;


import java.io.Serializable;
import java.util.Objects;

/* This class provides a representation
 * of the player needed at network level, when player's methods defined
 * in model classes are not required.
 */
public class User implements Serializable {

    private final String nickname;
    private transient boolean isActive;

    public User(String nickname) {
        this.nickname = nickname;
        isActive = true;
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

    public synchronized boolean isActive() {
        return isActive;
    }

    public synchronized void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return nickname;
    }
}
