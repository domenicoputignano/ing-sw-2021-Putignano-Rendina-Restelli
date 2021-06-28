package it.polimi.ingsw.Exceptions;

/**
 * Exception thrown when player is trying to activate a {@link it.polimi.ingsw.Commons.LeaderCard}
 * but he doesn't satisfy its requirements because of missing development cards or resources.
 */
public class LeaderRequirementsException extends Exception{
    public LeaderRequirementsException() {super();}
}
