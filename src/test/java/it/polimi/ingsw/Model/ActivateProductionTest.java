package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Card.ColorCard;
import it.polimi.ingsw.Model.Card.DevelopmentCard;
import it.polimi.ingsw.Utils.ActivateProductionMessage;
import it.polimi.ingsw.Utils.ResourceSource;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ActivateProductionTest {


    @Test
    void activateProduction() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, InvalidActionException {

        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Piero"));
        players.add(new Player("Domenico"));
        players.add(new Player("Andrea"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(players.get(0), players, players.get(0), 4);
        multiPlayerMode.setup();
        Turn turn = new Turn(multiPlayerMode, players.get(0));
        multiPlayerMode.setTurn(turn);




        Map<ResourceType, Integer> map = new EnumMap<ResourceType, Integer>(ResourceType.class);
        map.put(ResourceType.coin, 0);
        map.put(ResourceType.servant, 0);
        map.put(ResourceType.shield, 0);
        map.put(ResourceType.stone, 0);

        //Initialization of ActivateProdcutio
        ActivateProductionMessage activateProductionMessage = new ActivateProductionMessage();
        ActiveProductions activeProductions = new ActiveProductions();
        activeProductions.setSlot1(true);
        activeProductions.setBasic(true);
        activateProductionMessage.setProductions(activeProductions);
        activateProductionMessage.setInput1(ResourceType.coin);
        activateProductionMessage.setInput2(ResourceType.coin);
        activateProductionMessage.setOutput(ResourceType.shield);


        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> Strongbox = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> Depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> ExtraDepot = new EnumMap<ResourceType, Integer>(ResourceType.class);



        //Resources that have to be taken
        Depot.put(ResourceType.coin, 2);
        Strongbox.put(ResourceType.shield, 1);

        howToTakeResources.put(ResourceSource.DEPOT, Depot);
        howToTakeResources.put(ResourceSource.STRONGBOX, Strongbox);
        howToTakeResources.put(ResourceSource.EXTRA, ExtraDepot);

        activateProductionMessage.setHowToTakeResources(howToTakeResources);

        EnumMap<ResourceType, Integer> devCard1Cost = new EnumMap<ResourceType, Integer>(map);
        devCard1Cost.put(ResourceType.shield,2);

        EnumMap<ResourceType, Integer> devCard1Input = new EnumMap<ResourceType, Integer>(map);
        devCard1Input.put(ResourceType.shield,1);

        EnumMap<ResourceType,Integer> devCard1Output = new EnumMap<ResourceType, Integer>(map);
        devCard1Output.put(ResourceType.stone,1);
        devCard1Output.put(ResourceType.shield,1);

        ProductionRule cardTrade = new ProductionRule();
        cardTrade.setOutputFaith(0);
        cardTrade.setInputResources(devCard1Input);
        cardTrade.setOutputResources(devCard1Output);

        DevelopmentCard card = new DevelopmentCard(devCard1Cost, 1, ColorCard.green, 1,cardTrade);
        turn.getPlayer().getPersonalBoard().putCardOnTop(card,1);

        //mappa finale delle risorse disponibili
        EnumMap<ResourceType,Integer> expected = new EnumMap<ResourceType, Integer>(map);



        map.put(ResourceType.shield,2);
        turn.getPlayer().getPersonalBoard().getWarehouse().addResourcesToStrongbox(map);
        turn.getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(2, ResourceType.coin, 2);

        turn.setTurnState(TurnState.ACTIVATEPRODUCTION);




        turn.getTurnState().getTurnPhase().activateProduction(turn, activateProductionMessage);


        expected.put(ResourceType.shield,3);
        expected.put(ResourceType.stone,1);
        assertEquals(expected,turn.getPlayer().getPersonalBoard().getWarehouse().getAvailableResources());
    }
}