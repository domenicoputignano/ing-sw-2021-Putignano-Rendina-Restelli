package it.polimi.ingsw.Network;

/**
 * Enum that represents different states of a connection. This is necessary because
 * it allows server to make a decision on how to handle remote hosts disconnections.
 */
public enum ConnectionStates {
    CONFIGURATION, INGAME, DISCONNECTED
}
