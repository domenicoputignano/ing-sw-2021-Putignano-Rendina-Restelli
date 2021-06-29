package it.polimi.ingsw.utils.messages.clientMessages;


import java.io.Serializable;

/**
 *  Interface that provides an abstraction of messages that remote host can expect from client.
 *  It inherit from {@link Serializable} class in order to be sent through the network.
 */
public interface ClientMessage extends Serializable {

}
