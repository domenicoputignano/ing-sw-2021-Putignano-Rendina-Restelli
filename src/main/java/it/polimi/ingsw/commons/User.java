package it.polimi.ingsw.commons;


import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/** This class provides a representation
 * of the player needed at network level, when player's methods defined
 * in model classes are not required.
 */
public class User implements Serializable {

    /**
     * The unique nickname associated to user, chosen during the configuration phase of the game.
     */
    private final String nickname;
    /**
     * A flag representing if the user is currently playing.
     */
    private final AtomicBoolean isActive;

    public User(String nickname) {
        this.nickname = nickname;
        isActive = new AtomicBoolean(true);
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

    public boolean isActive() {
        return isActive.get();
    }

    public void setActive(boolean active) {
        isActive.getAndSet(active);
    }

    @Override
    public String toString() {
        return nickname;
    }
}
