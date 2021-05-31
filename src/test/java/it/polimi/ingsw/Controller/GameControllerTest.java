package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SoloMode.SoloMode;
import it.polimi.ingsw.Network.ClientStatus;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderChoiceMessage;
import org.junit.jupiter.api.Test;


import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class GameControllerTest {

    @Test
    void handleResourceChoiceMessage() {
    }

    @Test
    void handleLeaderChoiceMessage() {
        Player first = new Player("Piero");

        Player spyPlayer = spy(first);

        SoloMode soloMode = new SoloMode(spyPlayer);
        SoloMode spyOfGame = spy(soloMode);

        ClientStatus clientStatus = mock(ClientStatus.class);

        GameController controller = new GameController(spyOfGame);
        RemoteView remoteView = new RemoteView(first.getUser(),controller,clientStatus);
        RemoteView spyOfRemoteView = spy(remoteView);

        LeaderChoiceMessage message = new LeaderChoiceMessage(1,2);
        LeaderChoiceMessage spyOfMessage = spy(message);

        controller.handleLeaderChoiceMessage(spyOfMessage, spyOfRemoteView);

        assertTrue(message.isValidMessage());
        verify(spyOfMessage,times(1)).isValidMessage();
        verify(spyOfGame, times(1)).getGameState();
        verify(spyPlayer, times(1)).performInitialLeaderChoice(spyOfGame,2,1);

    }
}