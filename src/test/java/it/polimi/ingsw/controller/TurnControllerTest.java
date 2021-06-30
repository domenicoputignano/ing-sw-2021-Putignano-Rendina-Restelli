package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.exceptions.MoveResourcesException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.marketTray.Marble;
import it.polimi.ingsw.model.marketTray.StandardMarble;
import it.polimi.ingsw.model.marketTray.WhiteMarble;
import it.polimi.ingsw.network.ClientStatus;
import it.polimi.ingsw.network.NetworkRemoteView;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.messages.clientMessages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TurnControllerTest {


    Map<ResourceType,Integer> genericMap = new EnumMap<>(ResourceType.class);


    @BeforeEach
    void initializeMap() {
        genericMap.put(ResourceType.coin, 0);
        genericMap.put(ResourceType.shield, 0);
        genericMap.put(ResourceType.servant, 0);
        genericMap.put(ResourceType.stone, 0);
    }

    @Test
    void handleBuyDevCardMessageTest() {
        List<Player> players = new ArrayList<>();
        Player first = spy(new Player("Piero"));
        Player second = spy(new Player("Andrea"));
        Player third = spy(new Player("Domenico"));
        players.add(first);
        players.add(second);
        players.add(third);


        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(spy(new Turn(game, first)));
        game.nextState(GameState.GAMEFLOW);
        MultiPlayerMode gameSpy = spy(game);


        BuyDevCardMessage buyDevCardMessage = new BuyDevCardMessage();
        buyDevCardMessage.setType(new CardType(1, ColorCard.green));
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<>(ResourceType.class);
        strongBox.put(ResourceType.shield, 2);
        EnumMap<ResourceType,Integer> depot = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);
        buyDevCardMessage.setHowToTakeResources(howToTakeResources);
        buyDevCardMessage.setDestinationSlot(1);


        BuyDevCardMessage messageSpy = spy(buyDevCardMessage);

        GameController gameController = new GameController(game);
        ClientStatus clientStatus = mock(ClientStatus.class);

        NetworkRemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));
        NetworkRemoteView spyRemoteView2 = spy(new NetworkRemoteView(second.getUser(),gameController, clientStatus));

        TurnController turnController = new TurnController(gameSpy,first);

        turnController.handleBuyDevCardMessage(messageSpy,spyRemoteView);
        turnController.handleBuyDevCardMessage(messageSpy,spyRemoteView2);


        verify(messageSpy, times(1)).isValidMessage();
        verify(game.getTurn(), times(1)).getTurnPhase();
    }


    @Test
    void handleEndTurnMessageTest() {
        List<Player> players = new ArrayList<>();
        Player first = spy(new Player("Piero"));
        Player second = spy(new Player("Andrea"));
        Player third = spy(new Player("Domenico"));
        players.add(first);
        players.add(second);
        players.add(third);

        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(spy(new Turn(game, first)));
        game.nextState(GameState.GAMEFLOW);
        game.getTurn().normalActionDone();

        MultiPlayerMode gameSpy = spy(game);
        TurnController turnController = new TurnController(gameSpy,first);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        NetworkRemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));
        turnController.handleEndTurnMessage(new EndTurnMessage(),spyRemoteView);
        verify(gameSpy, times(1)).nextTurn();
    }

    @Test
    void handlePositioningTest(){
        List<Player> players = new ArrayList<>();
        Player first = spy(new Player("Piero"));
        Player second = spy(new Player("Andrea"));
        Player third = spy(new Player("Domenico"));
        players.add(first);
        players.add(second);
        players.add(third);

        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(new Turn(game, first));
        game.nextState(GameState.GAMEFLOW);

        TakeResourcesFromMarketMessage takeResourcesFromMarketMessage = new TakeResourcesFromMarketMessage();
        takeResourcesFromMarketMessage.setPlayerChoice(MarketChoice.ROW, 2);

        Marble[] marbles = game.getMarketTray().getAvailableMarbles()[1];
        marbles[0] = new StandardMarble(ColorMarble.BLUE, ResourceType.shield);
        marbles[1] = new StandardMarble(ColorMarble.BLUE, ResourceType.shield);
        marbles[2] = new WhiteMarble();
        marbles[3] = new WhiteMarble();

        takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<>(marbles[0].getReducedVersion(),MarbleDestination.EXTRA));
        takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<>(marbles[1].getReducedVersion(),MarbleDestination.EXTRA));
        takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<>(marbles[2].getReducedVersion(),MarbleDestination.NOTNEEDED));
        takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<>(marbles[3].getReducedVersion(),MarbleDestination.NOTNEEDED));

        TurnController turnController = new TurnController(game,first);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        RemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));
        turnController.handleTakeResourcesFromMarketMessage(takeResourcesFromMarketMessage, spyRemoteView);

        PositioningMessage positioningMessage = new PositioningMessage();
        List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
        whereToPutResources.add(new Pair<>(ResourceType.shield, MarbleDestination.DEPOT3));
        whereToPutResources.add(new Pair<>(ResourceType.shield, MarbleDestination.DEPOT3));
        positioningMessage.setWhereToPutResources(whereToPutResources);

        turnController.handlePositioningMessage(positioningMessage, spyRemoteView);

        assertEquals(2, (int) first.getPersonalBoard().getWarehouse().getAvailableResources().get(ResourceType.shield));
    }

    @Test
    void secondPositioningFailedTest(){
        List<Player> players = new ArrayList<>();
        Player first = new Player("Piero");
        Player second = new Player("Andrea");
        Player third = new Player("Domenico");
        players.add(first);
        players.add(second);
        players.add(third);

        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(new Turn(game, first));
        game.nextState(GameState.GAMEFLOW);

        TakeResourcesFromMarketMessage takeResourcesFromMarketMessage = new TakeResourcesFromMarketMessage();
        takeResourcesFromMarketMessage.setPlayerChoice(MarketChoice.ROW, 2);

        Marble[] marbles = game.getMarketTray().getAvailableMarbles()[1];
        marbles[0] = new StandardMarble(ColorMarble.BLUE, ResourceType.shield);
        marbles[1] = new StandardMarble(ColorMarble.BLUE, ResourceType.shield);
        marbles[2] = new WhiteMarble();
        marbles[3] = new WhiteMarble();

        takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<>(marbles[0].getReducedVersion(),MarbleDestination.EXTRA));
        takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<>(marbles[1].getReducedVersion(),MarbleDestination.EXTRA));
        takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<>(marbles[2].getReducedVersion(),MarbleDestination.NOTNEEDED));
        takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<>(marbles[3].getReducedVersion(),MarbleDestination.NOTNEEDED));

        TurnController turnController = new TurnController(game,first);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        RemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));
        turnController.handleTakeResourcesFromMarketMessage(takeResourcesFromMarketMessage, spyRemoteView);

        PositioningMessage positioningMessage = new PositioningMessage();
        List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
        whereToPutResources.add(new Pair<>(ResourceType.shield, MarbleDestination.EXTRA));
        whereToPutResources.add(new Pair<>(ResourceType.shield, MarbleDestination.EXTRA));
        positioningMessage.setWhereToPutResources(whereToPutResources);

        turnController.handlePositioningMessage(positioningMessage, spyRemoteView);

        assertEquals(0, (int) first.getPersonalBoard().getWarehouse().getAvailableResources().get(ResourceType.shield));
        if(game.getPlayer(second.getUser()).getPosition() == 2){
            assertEquals(2, second.getPersonalBoard().getFaithTrack().getFaithMarker());
        } else if (game.getPlayer(second.getUser()).getPosition() == 3) {
            assertEquals(3, second.getPersonalBoard().getFaithTrack().getFaithMarker());
        }
        if(game.getPlayer(third.getUser()).getPosition() == 2){
            assertEquals(2, third.getPersonalBoard().getFaithTrack().getFaithMarker());
        } else if (game.getPlayer(third.getUser()).getPosition() == 3) {
            assertEquals(3, third.getPersonalBoard().getFaithTrack().getFaithMarker());
        }
    }

    @Test
    void handleEndTurnMessageTestKO() {
        List<Player> players = new ArrayList<>();
        Player first = spy(new Player("Piero"));
        Player second = spy(new Player("Andrea"));
        Player third = spy(new Player("Domenico"));
        players.add(first);
        players.add(second);
        players.add(third);

        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(spy(new Turn(game, first)));
        game.nextState(GameState.GAMEFLOW);

        MultiPlayerMode gameSpy = spy(game);
        TurnController turnController = new TurnController(gameSpy,first);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        RemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));
        turnController.handleEndTurnMessage(new EndTurnMessage(),spyRemoteView);
        verify(gameSpy, times(0)).nextTurn();
    }


    @Test
    void handleMoveMessageTest() throws MoveResourcesException {
        List<Player> players = new ArrayList<>();
        Player first = spy(new Player("Piero"));
        Player second = spy(new Player("Andrea"));
        Player third = spy(new Player("Domenico"));
        players.add(first);
        players.add(second);
        players.add(third);

        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(spy(new Turn(game, first)));
        game.nextState(GameState.GAMEFLOW);

        MultiPlayerMode gameSpy = spy(game);
        TurnController turnController = new TurnController(gameSpy,first);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        RemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));

        MoveResourcesMessage message = new MoveResourcesMessage();
        MoveActionInterface moveAction = new MoveFromNormalToNormalAction(1,2);
        message.setMoveAction(moveAction);

        MoveResourcesMessage messageSpy = spy(message);
        turnController.handleMoveMessage(messageSpy,spyRemoteView);
        verify(messageSpy, times(1)).isValidMessage();
        assertTrue(message.isValidMessage());
        verify(gameSpy.getCurrPlayer(),times(1)).moveResources(gameSpy, moveAction);
    }


    @Test
    void handleLeaderActionTest() {

        List<Player> players = new ArrayList<>();
        Player first = spy(new Player("Piero"));
        Player second = spy(new Player("Andrea"));
        Player third = spy(new Player("Domenico"));
        players.add(first);
        players.add(second);
        players.add(third);

        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(spy(new Turn(game, first)));
        game.nextState(GameState.GAMEFLOW);

        MultiPlayerMode gameSpy = spy(game);
        TurnController turnController = new TurnController(gameSpy,first);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        RemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));

        LeaderActionMessage message = new LeaderActionMessage();
        message.setIndex(1);
        LeaderActionMessage messageSpy = spy(message);
        turnController.handleLeaderActionMessage(messageSpy, spyRemoteView);

        verify(gameSpy.getTurn(), times(1)).setTurnState(TurnState.ActionType.LEADERACTION);
        verify(gameSpy.getTurn(), times(1)).getTurnPhase();

    }

    @Test
    void handleTakeResourcesFromMarketTest() {
        List<Player> players = new ArrayList<>();
        Player first = spy(new Player("Piero"));
        Player second = spy(new Player("Andrea"));
        Player third = spy(new Player("Domenico"));
        players.add(first);
        players.add(second);
        players.add(third);

        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(spy(new Turn(game, first)));
        game.nextState(GameState.GAMEFLOW);

        MultiPlayerMode gameSpy = spy(game);
        TurnController turnController = new TurnController(gameSpy,first);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        RemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));

        TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
        message.setIndex(1);
        message.setPlayerChoice(MarketChoice.COLUMN);
        message.addWhereToPutMarbles(new Pair<>(new ReducedMarble(ColorMarble.BLUE),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new ReducedMarble(ColorMarble.YELLOW),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new ReducedMarble(ColorMarble.PURPLE),MarbleDestination.DEPOT3));

        TakeResourcesFromMarketMessage messageSpy = spy(message);
        turnController.handleTakeResourcesFromMarketMessage(messageSpy,spyRemoteView);

        verify(messageSpy, times(1)).isValidMessage();
        assertTrue(messageSpy.isValidMessage());
    }

    @Test
    void handleActivateProductionTest() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {

        List<Player> players = new ArrayList<>();
        Player first = spy(new Player("Piero"));
        Player second = spy(new Player("Andrea"));
        Player third = spy(new Player("Domenico"));
        players.add(first);
        players.add(second);
        players.add(third);
        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(spy(new Turn(game, first)));
        game.nextState(GameState.GAMEFLOW);
        MultiPlayerMode gameSpy = spy(game);

        TurnController turnController = new TurnController(gameSpy,first);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        RemoteView spyRemoteView = spy(new NetworkRemoteView(first.getUser(),gameController,clientStatus));

        ActivateProductionMessage message = new ActivateProductionMessage();
        ActiveProductions productions = new ActiveProductions();
        productions.setSlot1(true);
        message.setProductions(productions);

        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> depot = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<>(ResourceType.class);
        depot.put(ResourceType.coin, 1);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);

        message.setHowToTakeResources(howToTakeResources);

        ActivateProductionMessage messageSpy = spy(message);

        first.getPersonalBoard().getWarehouse().addResourcesToDepot(1, ResourceType.coin, 1);
        genericMap.put(ResourceType.coin,1);

        ProductionRule rule = new ProductionRule(genericMap,genericMap,1);

        DevelopmentCard card = new DevelopmentCard(genericMap, 1, ColorCard.green, 3, rule);

        DevelopmentCard spyCard = spy(card);

        first.getPersonalBoard().putCardOnTop(spyCard,1);
        turnController.handleActivateProductionMessage(messageSpy, spyRemoteView);
        verify(gameSpy, atLeastOnce()).getTurn();
        verify(gameSpy.getTurn(), atLeastOnce()).setTurnState(TurnState.ActionType.ACTIVATEPRODUCTION);
        verify(gameSpy.getTurn(), times(1)).getTurnPhase();
        verify(gameSpy.getTurn(), times(1)).normalActionDone();
    }


}