package it.polimi.ingsw.network;

/**
 * Enum that represents different states of a connection. This is necessary because
 * it allows server to make a decision on how to handle client disconnections.
 */
public enum ConnectionStates {
    CONFIGURATION, INGAME, DISCONNECTED
}
