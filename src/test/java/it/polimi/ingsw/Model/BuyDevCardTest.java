package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Card.CardType;
import it.polimi.ingsw.Model.Card.ColorCard;
import it.polimi.ingsw.Model.Card.DevelopmentCard;
import it.polimi.ingsw.Utils.BuyDevCardMessage;
import it.polimi.ingsw.Utils.ResourceSource;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BuyDevCardTest {




    @Test
    void buyDevCard() throws InvalidActionException {
        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Piero"));
        players.add(new Player("Domenico"));
        players.add(new Player("Andrea"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(players.get(0), players, players.get(0), 4);
        multiPlayerMode.setup();
        Turn turn = new Turn(multiPlayerMode, players.get(0));
        multiPlayerMode.setTurn(turn);


        CardType cardType = new CardType(1, ColorCard.green);

        EnumMap<ResourceType, Integer> devCard1Cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType, Integer> devCard2Cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
        devCard1Cost.put(ResourceType.shield,2);
        devCard1Cost.put(ResourceType.coin,0);
        devCard1Cost.put(ResourceType.servant,0);
        devCard1Cost.put(ResourceType.stone, 0);

        devCard2Cost.put(ResourceType.shield,1);
        devCard2Cost.put(ResourceType.coin,0);
        devCard2Cost.put(ResourceType.servant,1);
        devCard2Cost.put(ResourceType.stone,1);

        EnumMap<ResourceType, Integer> devCard1Input = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType, Integer> devCard1Output = new EnumMap<ResourceType, Integer>(ResourceType.class);

        devCard1Input.put(ResourceType.shield, 0);
        devCard1Input.put(ResourceType.coin, 1);
        devCard1Input.put(ResourceType.stone, 0);
        devCard1Input.put(ResourceType.servant, 0);

        devCard1Output.put(ResourceType.shield, 2);
        devCard1Output.put(ResourceType.coin, 0);
        devCard1Output.put(ResourceType.stone, 0);
        devCard1Output.put(ResourceType.servant, 0);

        ProductionRule productionRule = new ProductionRule();
        productionRule.setInputResources(devCard1Input);
        productionRule.setOutputResources(devCard1Output);
        productionRule.setOutputFaith(1);

        DevelopmentCard card1 = new DevelopmentCard(devCard1Cost,1,ColorCard.green,1,productionRule);

        //map used to initialize all data structure that requires a map to be built
        Map<ResourceType, Integer> map = new EnumMap<ResourceType, Integer>(ResourceType.class);
        map.put(ResourceType.coin, 0);
        map.put(ResourceType.servant, 0);
        map.put(ResourceType.shield, 2);
        map.put(ResourceType.stone, 0);


        //initialize of StrongBox resources with map
        multiPlayerMode.getPlayerList().get(0).getPersonalBoard().getWarehouse().addResourcesToStrongbox(map);

        Map<ResourceSource,EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();

        EnumMap<ResourceType,Integer> strongBox = new EnumMap<ResourceType, Integer>(ResourceType.class);
        strongBox.put(ResourceType.shield, 2);
        EnumMap<ResourceType,Integer> depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<ResourceType, Integer>(ResourceType.class);

        try {
            multiPlayerMode.getPlayerList().get(0).getPersonalBoard().getWarehouse().addResourcesToDepot(1, ResourceType.coin,1);
        } catch (DepotOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IncompatibleResourceTypeException e) {
            e.printStackTrace();
        }

        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);
        howToTakeResources.put(ResourceSource.DEPOT, depot);

        BuyDevCardMessage buyDevCardMessage = new BuyDevCardMessage();
        buyDevCardMessage.setType(cardType);
        buyDevCardMessage.setDestinationSlot(1);
        buyDevCardMessage.setHowToTakeResources(howToTakeResources);
        turn.setTurnState(TurnState.BUYDEVCARD);


        Map<ResourceType, Integer> expected = new EnumMap<ResourceType, Integer>(ResourceType.class);
        expected.put(ResourceType.servant, 0);
        expected.put(ResourceType.coin, 1);
        expected.put(ResourceType.shield, 0);
        expected.put(ResourceType.stone, 0);

        Map<ResourceType,Integer> expectedStrongBox = new EnumMap<ResourceType, Integer>(ResourceType.class);

        DevelopmentCard card = multiPlayerMode.getDecks().get(0).getTop();

        if(multiPlayerMode.getDecks().get(0).getTop().equals(card1)) {
            System.out.println(1);
            //TODO ricontrollare metodo dello strongbox
            turn.getTurnState().getTurnPhase().buyDevCard(turn, buyDevCardMessage);
            assertTrue(turn.isDoneNormalAction());
            assertEquals(turn.getPlayer().getPersonalBoard().peekTopCard(1), card1);
            assertEquals(expected,turn.getPlayer().getPersonalBoard().getWarehouse().getAvailableResources());
        }

    }


}