package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.marketTray.Marble;
import it.polimi.ingsw.model.marketTray.StandardMarble;
import it.polimi.ingsw.model.marketTray.WhiteMarble;
import it.polimi.ingsw.model.soloMode.SoloMode;
import it.polimi.ingsw.network.ClientStatus;
import it.polimi.ingsw.network.NetworkRemoteView;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.messages.clientMessages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
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
    void buyDevelopmentCard(){
        Player player = new Player("Andrea");
        SoloMode soloMode = new SoloMode(player);
        soloMode.nextState(GameState.GAMEFLOW);

        Map<ResourceType, Integer> initialResources = new EnumMap<>(ResourceType.class);
        initialResources.put(ResourceType.stone, 10);
        initialResources.put(ResourceType.shield, 10);
        initialResources.put(ResourceType.coin, 10);
        initialResources.put(ResourceType.servant, 10);
        player.getPersonalBoard().getWarehouse().addResourcesToStrongbox(initialResources);

        BuyDevCardMessage message = new BuyDevCardMessage();
        message.setType(new CardType(1, ColorCard.blue));
        message.setDestinationSlot(1);

        DevelopmentCard cardToBuy = soloMode.searchDeck(new CardType(1, ColorCard.blue)).getTop();
        EnumMap<ResourceType, Integer> cost = new EnumMap<>(cardToBuy.getCost());
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new EnumMap<>(ResourceSource.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, cost);
        howToTakeResources.put(ResourceSource.DEPOT, new EnumMap<>(ResourceType.class));
        howToTakeResources.put(ResourceSource.EXTRA, new EnumMap<>(ResourceType.class));
        message.setHowToTakeResources(howToTakeResources);

        TurnController turnController = new TurnController(soloMode, player);
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(soloMode);
        RemoteView spyRemoteView = spy(new NetworkRemoteView(player.getUser(),gameController,clientStatus));
        turnController.handleBuyDevCardMessage(message, spyRemoteView);

        cost.keySet().forEach(x -> assertEquals(10 - cost.get(x),
                player.getPersonalBoard().getWarehouse().getAvailableResources().get(x))) ;

    }

    @Test
    void handleEndTurnMessageTest() {
        List<Player> players = new ArrayList<>();
        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));

        MultiPlayerMode game = new MultiPlayerMode(players);
        game.setTurn(spy(new Turn(game, players.get(0))));
        game.nextState(GameState.GAMEFLOW);
        game.getTurn().normalActionDone();

        MultiPlayerMode gameSpy = spy(game);
        TurnController turnController = new TurnController(gameSpy,players.get(0));
        ClientStatus clientStatus = mock(ClientStatus.class);
        GameController gameController = new GameController(game);
        NetworkRemoteView inTurnView = spy(new NetworkRemoteView(players.get(0).getUser(),gameController,clientStatus));
        NetworkRemoteView notInTurnView = spy(new NetworkRemoteView(players.get(1).getUser(), gameController, clientStatus));
        turnController.handleEndTurnMessage(new EndTurnMessage(), notInTurnView);
        turnController.handleEndTurnMessage(new EndTurnMessage(), inTurnView);
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
    void handleMoveMessageFaults() throws MoveResourcesException {
        List<Player> players = new ArrayList<>();
        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));
        game.nextState(GameState.GAMEFLOW);
        GameController gameController = new GameController(game);


        game.setTurn(spy(new Turn(game,players.get(0))));
        TurnController turnController = new TurnController(game, players.get(0));
        RemoteView firstPlayerView = spy(new NetworkRemoteView(players.get(0).getUser(),gameController,mock(ClientStatus.class)));
        RemoteView secondPlayerView = spy(new NetworkRemoteView(players.get(1).getUser(),gameController,mock(ClientStatus.class)));

        MoveResourcesMessage message = new MoveResourcesMessage();
        MoveActionInterface moveAction = new MoveFromNormalToNormalAction(1,2);
        message.setMoveAction(moveAction);

        MoveResourcesMessage wrongMessage = new MoveResourcesMessage();
        wrongMessage.setMoveAction(new MoveFromNormalToNormalAction(0,4));

        turnController.handleMoveMessage(message, firstPlayerView);
        turnController.handleMoveMessage(wrongMessage, firstPlayerView);
        turnController.handleMoveMessage(message, secondPlayerView);


        verify(game.getCurrPlayer(),times(1)).moveResources(game, moveAction);
    }


    @Test
    void handlingActivateNotOwnedProductionPowers() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {

        List<Player> players = new ArrayList<>();

        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));
        game.nextState(GameState.GAMEFLOW);
        GameController gameController = new GameController(game);

        players.get(0).getPersonalBoard().getWarehouse().addResourcesToDepot(2, ResourceType.coin, 2);
        game.setTurn(spy(new Turn(game,players.get(0))));
        TurnController turnController = new TurnController(game, players.get(0));

        ActivateProductionMessage message = new ActivateProductionMessage();
        ActiveProductions productions = new ActiveProductions();
        productions.setBasic(true);
        message.setProductions(productions);

        message.setInput1(ResourceType.coin);
        message.setInput2(ResourceType.coin);
        message.setOutput(ResourceType.stone);

        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> depot = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.get(ResourceSource.DEPOT).put(ResourceType.shield,2);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);

        message.setHowToTakeResources(howToTakeResources);

        RemoteView playerInTurnView = spy(new NetworkRemoteView(players.get(0).getUser(), gameController, mock(ClientStatus.class)));
        turnController.handleActivateProductionMessage(message,playerInTurnView);
        //Verification that allows to check how many times is tried Activate Production method.
        verify(game.getTurn(), times(1)).getTurnPhase();
    }

    @Test
    void activateProductionPaymentError() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        List<Player> players = new ArrayList<>();

        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));
        game.nextState(GameState.GAMEFLOW);
        GameController gameController = new GameController(game);

        players.get(0).getPersonalBoard().getWarehouse().addResourcesToDepot(2, ResourceType.coin, 2);
        game.setTurn(spy(new Turn(game,players.get(0))));
        TurnController turnController = new TurnController(game, players.get(0));

        ActivateProductionMessage message = new ActivateProductionMessage();
        ActiveProductions productions = new ActiveProductions();
        productions.setBasic(true);
        message.setProductions(productions);

        message.setInput1(ResourceType.coin);
        message.setInput2(ResourceType.coin);
        message.setOutput(ResourceType.stone);

        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> depot = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.get(ResourceSource.STRONGBOX).put(ResourceType.coin,2);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);

        message.setHowToTakeResources(howToTakeResources);

        RemoteView playerInTurnView = spy(new NetworkRemoteView(players.get(0).getUser(), gameController, mock(ClientStatus.class)));
        turnController.handleActivateProductionMessage(message,playerInTurnView);
        //Verification that allows to check how many times is tried Activate Production method.
        verify(game.getTurn(), times(1)).getTurnPhase();
    }

    @Test
    void handleLeaderActionTest() {
        List<Player> players = new ArrayList<>();

        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));
        game.nextState(GameState.GAMEFLOW);
        GameController gameController = new GameController(game);

        game.setTurn(spy(new Turn(game,players.get(0))));
        TurnController turnController = new TurnController(game, players.get(0));

        RemoteView playerInTurnView = spy(new NetworkRemoteView(players.get(0).getUser(),gameController, mock(ClientStatus.class)));

        LeaderActionMessage message = new LeaderActionMessage();
        message.setIndex(1);
        LeaderActionMessage messageSpy = spy(message);
        turnController.handleLeaderActionMessage(messageSpy, playerInTurnView);

        verify(game.getTurn(), times(1)).setTurnState(TurnState.ActionType.LEADERACTION);
        verify(game.getTurn(), times(1)).getTurnPhase();

    }

    @Test
    void leaderActionFaults() {
        List<Player> players = new ArrayList<>();

        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));
        GameController gameController = new GameController(game);
        game.setTurn(spy(new Turn(game,players.get(0))));
        TurnController turnController = new TurnController(game,players.get(0));
        RemoteView playerInTurnView = spy(new NetworkRemoteView(players.get(0).getUser(),gameController,mock(ClientStatus.class)));
        RemoteView notInTurnPlayerView = spy(new NetworkRemoteView(players.get(1).getUser(),gameController,mock(ClientStatus.class)));

        players.get(0).activateLeaderCard(1);

        LeaderActionMessage message = new LeaderActionMessage();
        message.setIndex(5);
        turnController.handleLeaderActionMessage(message, playerInTurnView);

        LeaderActionMessage messageRelatedToAnAlreadyDoneAction = new LeaderActionMessage();
        message.setIndex(2);

        turnController.handleLeaderActionMessage(messageRelatedToAnAlreadyDoneAction, playerInTurnView);
        turnController.handleLeaderActionMessage(message, playerInTurnView);
        turnController.handleLeaderActionMessage(message, notInTurnPlayerView);
        verify(game.getTurn(), times(1)).setTurnState(TurnState.ActionType.LEADERACTION);
        verify(game.getTurn(), times(1)).getTurnPhase();

    }


    @Test
    void handleTakeResourceFromMarketTest() {
        List<Player> players = new ArrayList<>();

        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));


        GameController gameController = new GameController(game);

        game.setTurn(spy(new Turn(game,players.get(0))));

        List<ReducedMarble> selectedMarbles = Arrays.stream(game.getMarketTray().getAvailableMarbles()[0]).
                map(Marble::getReducedVersion).collect(Collectors.toList());
        List<ReducedMarble> secondMarbleSelection = Arrays.stream(game.getMarketTray().getAvailableMarbles()[1]).
                map(Marble::getReducedVersion).collect(Collectors.toList());
        TakeResourcesFromMarketMessage rightMessage = new TakeResourcesFromMarketMessage();
        TakeResourcesFromMarketMessage wrongMessage = new TakeResourcesFromMarketMessage();
        TakeResourcesFromMarketMessage wrongCompiledMessage = new TakeResourcesFromMarketMessage();

        wrongCompiledMessage.setPlayerChoice(MarketChoice.ROW,2);

        wrongMessage.setIndex(2);
        wrongMessage.setPlayerChoice(MarketChoice.ROW);
        rightMessage.setIndex(1);
        rightMessage.setPlayerChoice(MarketChoice.ROW);


        selectedMarbles.forEach(x -> rightMessage.addWhereToPutMarbles(new Pair<>(x, MarbleDestination.DISCARD)));
        secondMarbleSelection.forEach(x -> wrongMessage.addWhereToPutMarbles(new Pair<>(x, MarbleDestination.DISCARD)));


        TurnController turnController = new TurnController(game,players.get(0));
        NetworkRemoteView playerInTurnView = new NetworkRemoteView(players.get(0).getUser(), gameController, mock(ClientStatus.class));
        NetworkRemoteView playerNotInTurnView = new NetworkRemoteView(players.get(1).getUser(), gameController, mock(ClientStatus.class));

        turnController.handleTakeResourcesFromMarketMessage(wrongCompiledMessage, playerInTurnView);
        turnController.handleTakeResourcesFromMarketMessage(wrongMessage, playerNotInTurnView);
        turnController.handleTakeResourcesFromMarketMessage(rightMessage, playerInTurnView);
        turnController.handleTakeResourcesFromMarketMessage(rightMessage, playerInTurnView);
        turnController.handleTakeResourcesFromMarketMessage(wrongMessage, playerInTurnView);
        verify(game.getTurn(), atLeastOnce()).getTurnPhase();

    }

    @Test
    void activateProductionResourceMissing() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        List<Player> players = new ArrayList<>();

        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));
        game.nextState(GameState.GAMEFLOW);
        GameController gameController = new GameController(game);

        players.get(0).getPersonalBoard().getWarehouse().addResourcesToDepot(1, ResourceType.coin, 1);
        game.setTurn(spy(new Turn(game,players.get(0))));
        TurnController turnController = new TurnController(game, players.get(0));

        ActivateProductionMessage message = new ActivateProductionMessage();
        ActiveProductions productions = new ActiveProductions();
        productions.setBasic(true);
        message.setProductions(productions);

        message.setInput1(ResourceType.coin);
        message.setInput2(ResourceType.coin);
        message.setOutput(ResourceType.stone);

        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> depot = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.get(ResourceSource.DEPOT).put(ResourceType.coin,2);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);

        message.setHowToTakeResources(howToTakeResources);

        RemoteView playerInTurnView = spy(new NetworkRemoteView(players.get(0).getUser(), gameController, mock(ClientStatus.class)));
        turnController.handleActivateProductionMessage(message,playerInTurnView);
        //Verification that allows to check how many times is tried Activate Production method.
        verify(game.getTurn(), times(1)).getTurnPhase();
    }


    @Test
    void activateProductionResourceMismatch() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {

        List<Player> players = new ArrayList<>();

        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));
        game.nextState(GameState.GAMEFLOW);
        GameController gameController = new GameController(game);

        game.setTurn(spy(new Turn(game, players.get(0))));
        game.nextState(GameState.GAMEFLOW);

        MultiPlayerMode gameSpy = spy(game);
        TurnController turnController = new TurnController(gameSpy,players.get(0));
        ActivateProductionMessage message = new ActivateProductionMessage();

        ActivateProductionMessage badlyFormattedMessage = new ActivateProductionMessage();

        players.get(0).getPersonalBoard().getWarehouse().addResourcesToDepot(2, ResourceType.coin, 2);
        ActiveProductions productions = new ActiveProductions();
        productions.setBasic(true);
        message.setProductions(productions);
        badlyFormattedMessage.setProductions(productions);
        message.setInput1(ResourceType.coin);
        message.setInput2(ResourceType.coin);
        message.setOutput(ResourceType.stone);

        badlyFormattedMessage.setInput1(ResourceType.coin);
        badlyFormattedMessage.setInput2(ResourceType.coin);


        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> depot = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.get(ResourceSource.DEPOT).put(ResourceType.coin,2);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);

        message.setHowToTakeResources(howToTakeResources);

        RemoteView playerInTurnView = spy(new NetworkRemoteView(players.get(0).getUser(), gameController, mock(ClientStatus.class)));
        RemoteView playerNotInTurnView = spy(new NetworkRemoteView(players.get(1).getUser(), gameController, mock(ClientStatus.class)));
        turnController.handleActivateProductionMessage(message,playerInTurnView);
        turnController.handleActivateProductionMessage(message,playerNotInTurnView);
        turnController.handleActivateProductionMessage(badlyFormattedMessage,playerInTurnView);
        turnController.handleActivateProductionMessage(message,playerInTurnView);

        //Verification that allows to check how many times is tried Activate Production method.
        verify(game.getTurn(), times(2)).getTurnPhase();


    }

    @Test
    void handlingActivateProductionPowers() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        List<Player> players = new ArrayList<>();

        players.add(spy(new Player("Piero")));
        players.add(spy(new Player("Andrea")));
        players.add(spy(new Player("Domenico")));
        MultiPlayerMode game = spy(new MultiPlayerMode(players));
        game.nextState(GameState.GAMEFLOW);
        GameController gameController = new GameController(game);

        players.get(0).getPersonalBoard().getWarehouse().addResourcesToDepot(2, ResourceType.coin, 2);
        game.setTurn(spy(new Turn(game,players.get(0))));
        TurnController turnController = new TurnController(game, players.get(0));

        ActivateProductionMessage message = new ActivateProductionMessage();

        ActivateProductionMessage badlyFormattedMessage = new ActivateProductionMessage();

        ActiveProductions productions = new ActiveProductions();
        productions.setBasic(true);
        productions.setSlot1(true);
        message.setProductions(productions);
        badlyFormattedMessage.setProductions(productions);
        message.setInput1(ResourceType.coin);
        message.setInput2(ResourceType.coin);
        message.setOutput(ResourceType.stone);

        badlyFormattedMessage.setInput1(ResourceType.coin);
        badlyFormattedMessage.setInput2(ResourceType.coin);


        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> depot = new EnumMap<>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.get(ResourceSource.DEPOT).put(ResourceType.coin,2);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);

        message.setHowToTakeResources(howToTakeResources);

        RemoteView playerInTurnView = spy(new NetworkRemoteView(players.get(0).getUser(), gameController, mock(ClientStatus.class)));
        turnController.handleActivateProductionMessage(message,playerInTurnView);

        //Verification that allows to check how many times is tried Activate Production method.
        verify(game.getTurn(), times(0)).getTurnPhase();


    }




}