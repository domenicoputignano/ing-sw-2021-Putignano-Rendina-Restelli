package it.polimi.ingsw.controller;


import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.MultiPlayerMode;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import it.polimi.ingsw.network.ClientStatus;
import it.polimi.ingsw.network.NetworkRemoteView;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.messages.clientMessages.LeaderChoiceMessage;
import it.polimi.ingsw.utils.messages.clientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.utils.Pair;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        NetworkRemoteView fourthPlayerRemoteView = spy(new NetworkRemoteView(players.get(3).getUser(), gameController, mock(ClientStatus.class)));


        List<Pair<ResourceType,Integer>> resourcesCausingMismatch = new ArrayList<>();
        resourcesCausingMismatch.add(new Pair<>(ResourceType.servant,1));
        resourcesCausingMismatch.add(new Pair<>(ResourceType.stone,1));
        RemoteView secondPlayerView = spy(new NetworkRemoteView(players.get(1).getUser(), gameController, mock(ClientStatus.class)));


        fourthPlayerResources.add(new Pair<>(ResourceType.coin, 1));
        fourthPlayerResources.add(new Pair<>(ResourceType.shield, 2));


        wronglyChosenResources.add(new Pair<>(ResourceType.stone, 2));
        wronglyChosenResources.add(new Pair<>(ResourceType.coin, 2));

        List<Pair<ResourceType,Integer>> wrongMessageResources = new ArrayList<>(wronglyChosenResources);
        wrongMessageResources.add(new Pair<>(ResourceType.shield,3));

        gameController.handleResourceChoiceMessage(message, remoteView);

        ResourceChoiceMessage messageFromFourthPlayer = spy(new ResourceChoiceMessage(fourthPlayerResources));
        gameController.handleResourceChoiceMessage(messageFromFourthPlayer, fourthPlayerRemoteView);
        gameController.handleResourceChoiceMessage(spy(new ResourceChoiceMessage(wronglyChosenResources)), fourthPlayerRemoteView);

        gameController.handleResourceChoiceMessage(spy(new ResourceChoiceMessage(resourcesCausingMismatch)), secondPlayerView);
        gameController.handleResourceChoiceMessage(spy(new ResourceChoiceMessage(wrongMessageResources)),secondPlayerView);

        verify(message, times(1)).isValidMessage();
        verify(messageFromFourthPlayer, times(1)).isValidMessage();
        verify(players.get(0), times(1)).performInitialResourcesChoice(game, resources);
        verify(players.get(3), times(0)).performInitialResourcesChoice(game, wronglyChosenResources);
        verify(players.get(3), times(1)).performInitialResourcesChoice(game, fourthPlayerResources);
        verify(players.get(1), times(0)).performInitialResourcesChoice(game, resourcesCausingMismatch);
        verify(players.get(1), times(0)).performInitialResourcesChoice(game, wrongMessageResources);

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
        LeaderChoiceMessage wrongMessage = spy(new LeaderChoiceMessage(1,5));

        NetworkRemoteView firstPlayerView = spy(new NetworkRemoteView(players.get(0).getUser(),gameController, mock(ClientStatus.class)));
        RemoteView secondPlayerView = spy(new NetworkRemoteView(players.get(1).getUser(), gameController, mock(ClientStatus.class)));
        gameController.handleLeaderChoiceMessage(message, firstPlayerView);
        gameController.handleLeaderChoiceMessage(wrongMessage, secondPlayerView);
        verify(message, times(1)).isValidMessage();
        verify(players.get(0), times(1)).performInitialLeaderChoice(game,2,1);
        verify(players.get(1), times(0)).performInitialLeaderChoice(game, 5,1);
        assertTrue(message.isValidMessage());
    }

    /**
     * Verifies if game controller calls right methods over turn controller.
     */
    @Test
    void playerConnectionHandlingTest() {
        players.add(spy(new Player("Domenico")));
        Game game = spy(new MultiPlayerMode(spy(players)));
        GameController gameController = new GameController(game);
        User domenico = new User("Domenico");
        gameController.handlePlayerDisconnection(domenico,mock(NetworkRemoteView.class));
        verify(game,times(1)).handlePlayerDisconnection(players.get(0));
        gameController.handlePlayerReconnection(domenico);
        verify(game, times(1)).handlePlayerReconnection(domenico);
    }

    /**
     * This methods tests game controller method through making assumptions on how many times
     * a solo mode game changes its state.
     */
    @Test
    void soloModeConnectionsHandling() {
        Player soloModePlayer = spy(new Player("Piero"));
        SoloMode game = spy(new SoloMode(soloModePlayer));
        GameController gameController = new GameController(game);
        User piero = new User("Piero");
        gameController.handlePlayerDisconnection(piero, mock(NetworkRemoteView.class));
        gameController.handlePlayerReconnection(piero);
        gameController.handlePlayerDisconnection(piero, mock(NetworkRemoteView.class));
        verify(game, times(2)).nextState(GameState.PAUSED);
        verify(game, times(1)).nextState(GameState.GAMEFLOW);
    }





}