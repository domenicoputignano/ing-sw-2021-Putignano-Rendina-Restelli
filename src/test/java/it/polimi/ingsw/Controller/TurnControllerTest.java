package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Commons.CardType;
import it.polimi.ingsw.Commons.ColorCard;

import it.polimi.ingsw.Commons.ResourceType;

import it.polimi.ingsw.Model.*;

import it.polimi.ingsw.Network.ClientStatus;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.BuyDevCardMessage;
import it.polimi.ingsw.Utils.ResourceSource;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.mockito.Mockito.*;

class TurnControllerTest {



    @Test
    void handleBuyDevCardMessage() {
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
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<ResourceType, Integer>(ResourceType.class);
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

        RemoteView spyRemoteView = spy(new RemoteView(first.getUser(),gameController,clientStatus));
        RemoteView spyRemoteView2 = spy(new RemoteView(second.getUser(),gameController, clientStatus));

        TurnController turnController = new TurnController(gameSpy,first);

        turnController.handleBuyDevCardMessage(messageSpy,spyRemoteView);
        turnController.handleBuyDevCardMessage(messageSpy,spyRemoteView2);


        verify(messageSpy, times(1)).isValidMessage();
        verify(game.getTurn(), times(1)).getTurnPhase();
    }




}