package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Card.ColorCard;
import it.polimi.ingsw.Model.Card.DevelopmentCard;
import it.polimi.ingsw.Utils.Messages.ActivateProductionMessage;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.ResourceSource;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ActivateProductionTest {


    @Test
    void activateProduction() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, InvalidActionException {
        //initialization of the Game
        List<Player> players = new ArrayList<Player>();
        players.add(new Player("Piero"));
        players.add(new Player("Domenico"));
        players.add(new Player("Andrea"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(players.get(0), players, players.get(0), 4);
        multiPlayerMode.setup();
        Turn turn = new Turn(multiPlayerMode, players.get(0));
        multiPlayerMode.setTurn(turn);


        //Creation of default map
        Map<ResourceType, Integer> map = new EnumMap<ResourceType, Integer>(ResourceType.class);
        map.put(ResourceType.coin, 0);
        map.put(ResourceType.servant, 0);
        map.put(ResourceType.shield, 0);
        map.put(ResourceType.stone, 0);

        //Initialization of ActivateProductions: slot1, slot2 and basicProduction selected
        ActivateProductionMessage activateProductionMessage = new ActivateProductionMessage();
        ActiveProductions activeProductions = new ActiveProductions();
        activeProductions.setSlot2(true);
        activeProductions.setSlot1(true);
        activeProductions.setBasic(true);
        activateProductionMessage.setProductions(activeProductions);
        //input of basic production
        activateProductionMessage.setInput1(ResourceType.coin);
        activateProductionMessage.setInput2(ResourceType.servant);
        //output of basic production
        activateProductionMessage.setOutput(ResourceType.shield);


        //instantiate the map including instructions on how to take resources
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> Strongbox = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> Depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> ExtraDepot = new EnumMap<ResourceType, Integer>(ResourceType.class);


        //Resources that have to be taken
        Depot.put(ResourceType.coin, 2);
        Depot.put(ResourceType.servant, 1);
        Depot.put(ResourceType.shield,1);

        howToTakeResources.put(ResourceSource.DEPOT, Depot);
        howToTakeResources.put(ResourceSource.STRONGBOX, Strongbox);
        howToTakeResources.put(ResourceSource.EXTRA, ExtraDepot);

        activateProductionMessage.setHowToTakeResources(howToTakeResources);

        EnumMap<ResourceType, Integer> devCard1Cost = new EnumMap<ResourceType, Integer>(map);
        devCard1Cost.put(ResourceType.shield,2);

        EnumMap<ResourceType, Integer> devCard1Input = new EnumMap<ResourceType, Integer>(map);
        devCard1Input.put(ResourceType.shield,1);

        EnumMap<ResourceType, Integer> devCard2Input = new EnumMap<>(map);
        devCard1Input.put(ResourceType.coin, 1);

        EnumMap<ResourceType,Integer> devCard1Output = new EnumMap<ResourceType, Integer>(map);
        devCard1Output.put(ResourceType.stone,1);
        devCard1Output.put(ResourceType.shield,1);

        EnumMap<ResourceType,Integer> devCard2Output = new EnumMap<ResourceType, Integer>(map);
        devCard2Output.put(ResourceType.servant,2);

        ProductionRule cardTrade1 = new ProductionRule();
        cardTrade1.setOutputFaith(0);
        cardTrade1.setInputResources(devCard1Input);
        cardTrade1.setOutputResources(devCard1Output);

        ProductionRule cardTrade2 = new ProductionRule();
        cardTrade2.setInputResources(devCard2Input);
        cardTrade2.setOutputResources(devCard2Output);
        cardTrade2.setOutputFaith(1);

        DevelopmentCard card = new DevelopmentCard(devCard1Cost, 1, ColorCard.green, 1, cardTrade1);
        turn.getPlayer().getPersonalBoard().putCardOnTop(card,1);

        DevelopmentCard card2 = new DevelopmentCard(devCard1Cost, 1, ColorCard.yellow, 1, cardTrade2);
        turn.getPlayer().getPersonalBoard().putCardOnTop(card2, 2);


        //mappa finale delle risorse disponibili
        EnumMap<ResourceType,Integer> expected = new EnumMap<ResourceType, Integer>(map);



        turn.getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(2, ResourceType.coin, 2);
        turn.getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(1, ResourceType.servant,1);
        turn.getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(3, ResourceType.shield, 1);

        turn.setTurnState(TurnState.ACTIVATEPRODUCTION);


        expected.put(ResourceType.coin,0);
        expected.put(ResourceType.servant,2);
        expected.put(ResourceType.shield,2);
        expected.put(ResourceType.stone,1);


        ActivateProduction action = (ActivateProduction)turn.getTurnState().getTurnPhase();
        System.out.println("Available resources"+turn.getPlayer().getPersonalBoard().getWarehouse().getAvailableResources());


        //Does concretely selected productions
        turn.getTurnState().getTurnPhase().activateProduction(turn, activateProductionMessage);

        System.out.println("Input resources "+action.getInputResources());
        System.out.println("Output Resources "+action.getOutputResources());

        NormalDepot emptyDepot1 = new NormalDepot(0, null, 1);
        NormalDepot emptyDepot2 = new NormalDepot(0, null, 2);
        NormalDepot emptyDepot3 = new NormalDepot(0, null, 3);

        assertEquals(emptyDepot1, turn.getPlayer().getPersonalBoard().getWarehouse().getNormalDepots().get(0));
        assertEquals(emptyDepot2, turn.getPlayer().getPersonalBoard().getWarehouse().getNormalDepots().get(1));
        assertEquals(emptyDepot3, turn.getPlayer().getPersonalBoard().getWarehouse().getNormalDepots().get(2));

        assertEquals(expected,turn.getPlayer().getPersonalBoard().getWarehouse().getAvailableResources());

        List<Pair<ResourceType,Integer>> result = new ArrayList<>(4);
        expected.forEach((key,value) -> result.add(new Pair<>(key, value)));

        assertEquals(result, turn.getPlayer().getPersonalBoard().getWarehouse().getResourcesInStrongbox());
        assertEquals(1, turn.getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker());


    }
}