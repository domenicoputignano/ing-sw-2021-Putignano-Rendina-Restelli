package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SoloMode.SoloMode;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.ClientStatus;
import it.polimi.ingsw.Network.NetworkRemoteView;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.Utils.Pair;
import org.apache.maven.model.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class GameControllerTest {
    List<Player> players = new ArrayList<>();

    @Test
    void handleResourceChoiceMessage() {
        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Domenico")));
        Game game = spy(new MultiPlayerMode(spy(players)));
        GameController gameController = new GameController(game);
        List<Pair<ResourceType,Integer>> resource = new ArrayList<>();
        resource.add(new Pair<>(ResourceType.coin,1));
        ResourceChoiceMessage message = spy(new ResourceChoiceMessage(resource));
        NetworkRemoteView remoteView = spy(new NetworkRemoteView(players.get(0).getUser(),gameController, mock(ClientStatus.class)));
        gameController.handleResourceChoiceMessage(message, remoteView);
        verify(message, times(1)).isValidMessage();
        verify(players.get(0), times(1)).performInitialResourcesChoice(game, resource);
        assertTrue(message.isValidMessage());
    }

    @Test
    void handleLeaderChoiceMessage() {
        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Domenico")));
        Game game = spy(new MultiPlayerMode(spy(players)));
        GameController gameController = new GameController(game);
        List<Pair<ResourceType,Integer>> resource = new ArrayList<>();
        LeaderChoiceMessage message = spy(new LeaderChoiceMessage(1,2));
        NetworkRemoteView remoteView = spy(new NetworkRemoteView(players.get(0).getUser(),gameController, mock(ClientStatus.class)));
        gameController.handleLeaderChoiceMessage(message, remoteView);
        verify(message, times(1)).isValidMessage();
        verify(players.get(0), times(1)).performInitialLeaderChoice(game,2,1);
        assertTrue(message.isValidMessage());
    }
}