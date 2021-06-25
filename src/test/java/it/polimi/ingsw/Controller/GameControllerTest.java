package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Network.ClientStatus;
import it.polimi.ingsw.Network.NetworkRemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.Utils.Pair;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class GameControllerTest {
    List<Player> players = new ArrayList<>();
    List<Pair<ResourceType,Integer>> resources = new ArrayList<>();
    List<Pair<ResourceType, Integer>> fourthPlayerResources = new ArrayList<>();
    List<Pair<ResourceType, Integer>> wronglyChosenResources = new ArrayList<>();

    @Test
    void handleResourceChoiceMessage() {
        players.add(spy(new Player("Emilio")));
        players.add(spy(new Player("Franco")));
        players.add(spy(new Player("Mattia")));
        players.add(spy(new Player("Ernesto")));
        Game game = spy(new MultiPlayerMode(spy(players)));
        GameController gameController = new GameController(game);
        resources.add(new Pair<>(ResourceType.coin,1));
        ResourceChoiceMessage message = spy(new ResourceChoiceMessage(resources));
        NetworkRemoteView remoteView = spy(new NetworkRemoteView(players.get(0).getUser(),gameController, mock(ClientStatus.class)));
        NetworkRemoteView fourthRemoteView = spy(new NetworkRemoteView(players.get(3).getUser(), gameController, mock(ClientStatus.class)));

        fourthPlayerResources.add(new Pair<>(ResourceType.coin, 1));
        fourthPlayerResources.add(new Pair<>(ResourceType.shield, 2));

        wronglyChosenResources.add(new Pair<>(ResourceType.stone, 2));
        wronglyChosenResources.add(new Pair<>(ResourceType.coin, 2));

        gameController.handleResourceChoiceMessage(message, remoteView);

        ResourceChoiceMessage messageFromFourthPlayer = spy(new ResourceChoiceMessage(fourthPlayerResources));
        gameController.handleResourceChoiceMessage(messageFromFourthPlayer, fourthRemoteView);
        gameController.handleResourceChoiceMessage(spy(new ResourceChoiceMessage(wronglyChosenResources)), fourthRemoteView);
        verify(message, times(1)).isValidMessage();
        verify(messageFromFourthPlayer, times(1)).isValidMessage();
        verify(players.get(0), times(1)).performInitialResourcesChoice(game, resources);
        verify(players.get(3), times(0)).performInitialResourcesChoice(game, wronglyChosenResources);
        verify(players.get(3), times(1)).performInitialResourcesChoice(game, fourthPlayerResources);
        assertTrue(message.isValidMessage());

    }

    @Test
    void fourthPlayerPositioningValidity() {
        List<Pair<ResourceType, Integer>> resourcesWrongForIndex = new ArrayList<>();
        List<Pair<ResourceType,Integer>> correctResources = new ArrayList<>();

        wronglyChosenResources.add(new Pair<>(ResourceType.stone, 2));
        wronglyChosenResources.add(new Pair<>(ResourceType.coin, 2));
        resourcesWrongForIndex.add(new Pair<>(ResourceType.coin, 4));
        resourcesWrongForIndex.add(new Pair<>(ResourceType.shield, 0));

        correctResources.add(new Pair<>(ResourceType.shield,2));
        correctResources.add(new Pair<>(ResourceType.shield,2));


        List<Pair<ResourceType,Integer>> resourcesWrongForDepotIndexes = new ArrayList<>();
        resourcesWrongForDepotIndexes.add(new Pair<>(ResourceType.stone,3));
        resourcesWrongForDepotIndexes.add(new Pair<>(ResourceType.stone,1));

        GameController gameController = new GameController(mock(Game.class));

        assertFalse(gameController.isValidFourthPlayerPositioning(wronglyChosenResources));
        assertFalse(gameController.isValidFourthPlayerPositioning(resourcesWrongForIndex));
        assertFalse(gameController.isValidFourthPlayerPositioning(resourcesWrongForDepotIndexes));
        assertTrue(gameController.isValidFourthPlayerPositioning(correctResources));
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