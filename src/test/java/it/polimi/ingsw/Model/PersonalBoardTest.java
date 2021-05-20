package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {

    Player player = new Player("Ernesto");
    Map<ResourceType, Integer> developmentCard1Cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
    List<CardType> requirementsCards1 = new ArrayList<>();
    List<CardType> requirementsCards2 = new ArrayList<>();
    PersonalBoard personalBoard = new PersonalBoard(player);





    @BeforeEach
    void initialize() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        List<Player> players = new ArrayList<>();

    }

    @Test
    void peekTopCard(){

        developmentCard1Cost.put(ResourceType.coin, 0);
        developmentCard1Cost.put(ResourceType.servant,0);
        developmentCard1Cost.put(ResourceType.shield, 2);
        developmentCard1Cost.put(ResourceType.stone, 1);
        DevelopmentCard developmentCard1 = new DevelopmentCard(developmentCard1Cost,1, ColorCard.yellow,3,null);

        personalBoard.putCardOnTop(developmentCard1, 1);

        assertEquals(developmentCard1, personalBoard.peekTopCard(1));
    }


    @Test
    void isCompatibleSlot() {
        developmentCard1Cost.put(ResourceType.coin, 0);
        developmentCard1Cost.put(ResourceType.servant,0);
        developmentCard1Cost.put(ResourceType.shield, 0);
        developmentCard1Cost.put(ResourceType.stone, 0);
        Map<ResourceType, Integer> developmentCard2Cost = new EnumMap<ResourceType, Integer>(developmentCard1Cost);
        DevelopmentCard developmentCard1 = new DevelopmentCard(developmentCard1Cost,1, ColorCard.yellow,3,null);
        DevelopmentCard developmentCard2 = new DevelopmentCard(developmentCard2Cost,1, ColorCard.green,4,null);
        player.getPersonalBoard().putCardOnTop(developmentCard1,1);
        player.getPersonalBoard().putCardOnTop(developmentCard2,2);
        assertFalse(player.getPersonalBoard().isCompatibleSlot(1, 1));
        assertFalse(player.getPersonalBoard().isCompatibleSlot(3, 3));
        assertTrue(player.getPersonalBoard().isCompatibleSlot(1, 3));
        assertFalse(player.getPersonalBoard().isCompatibleSlot(3, 1));
        assertTrue(player.getPersonalBoard().isCompatibleSlot(2, 1));
    }

    @Test
    void isValidRequestedProduction() {
        developmentCard1Cost.put(ResourceType.coin, 0);
        developmentCard1Cost.put(ResourceType.servant,0);
        developmentCard1Cost.put(ResourceType.shield, 0);
        developmentCard1Cost.put(ResourceType.stone, 0);
        Map<ResourceType, Integer> developmentCard2Cost = new EnumMap<ResourceType, Integer>(developmentCard1Cost);
        DevelopmentCard developmentCard1 = new DevelopmentCard(developmentCard1Cost,1, ColorCard.yellow,3,null);
        DevelopmentCard developmentCard2 = new DevelopmentCard(developmentCard2Cost,1, ColorCard.green,4,null);
        LeaderCard leaderCard1 = new LeaderCard(new LeaderEffect(Effect.EXTRAPRODUCTION, ResourceType.coin), null, requirementsCards1, 5);
        leaderCard1.setIsActive();
        player.getLeaderCards().add(leaderCard1);
        LeaderCard leaderCard2 = new LeaderCard(new LeaderEffect(Effect.EXTRAPRODUCTION, ResourceType.shield), null, requirementsCards1, 5);
        leaderCard2.setIsActive();

        player.getLeaderCards().add(leaderCard2);
        player.getPersonalBoard().putCardOnTop(developmentCard1,1);
        player.getPersonalBoard().putCardOnTop(developmentCard2,2);

        ActiveProductions activeProductions1 = new ActiveProductions();
        activeProductions1.setSlot1(true);
        activeProductions1.setSlot2(true);
        activeProductions1.setExtraSlot1(true);

        ActiveProductions activeProductions2 = new ActiveProductions();
        activeProductions2.setSlot1(true);
        activeProductions2.setSlot3(true);
        activeProductions2.setExtraSlot2(true);

        assertTrue(player.getPersonalBoard().isValidRequestedProduction(activeProductions1));
        assertFalse(player.getPersonalBoard().isValidRequestedProduction(activeProductions2));

        ActiveProductions activeProductions3 = new ActiveProductions();
        activeProductions3.setSlot1(true);
        activeProductions3.setSlot2(true);
        activeProductions3.setExtraSlot2(true);

        assertTrue(player.getPersonalBoard().isValidRequestedProduction(activeProductions3));

    }

    @Test
    void checkLeaderRequirements() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        developmentCard1Cost.put(ResourceType.coin, 0);
        developmentCard1Cost.put(ResourceType.servant,0);
        developmentCard1Cost.put(ResourceType.shield, 0);
        developmentCard1Cost.put(ResourceType.stone, 0);
        Map<ResourceType, Integer> toAddToStrongbox = new EnumMap<ResourceType, Integer>(developmentCard1Cost);
        Map<ResourceType, Integer> requirementsResources1 = new EnumMap<>(developmentCard1Cost);
        Map<ResourceType, Integer> requirementsResources2 = new EnumMap<>(developmentCard1Cost);
        Map<ResourceType, Integer> developmentCard2Cost = new EnumMap<ResourceType, Integer>(developmentCard1Cost);

        requirementsCards2.add(new CardType(1, ColorCard.green));
        requirementsCards2.add(new CardType(1, ColorCard.yellow));
        requirementsCards2.add(new CardType(2, ColorCard.yellow));
        requirementsCards2.add(new CardType(2, ColorCard.yellow));
        requirementsCards2.add(new CardType(3, ColorCard.yellow));

        requirementsResources1.put(ResourceType.stone, 5);
        LeaderCard leaderCard1 = new LeaderCard(new LeaderEffect(Effect.EXTRADEPOT, ResourceType.coin), requirementsResources1, requirementsCards1, 5);
        LeaderCard leaderCard2 = new LeaderCard(new LeaderEffect(Effect.EXTRADEPOT, ResourceType.shield), requirementsResources2, requirementsCards2, 3);
        DevelopmentCard developmentCard1 = new DevelopmentCard(developmentCard1Cost,1, ColorCard.yellow,3,null);
        DevelopmentCard developmentCard2 = new DevelopmentCard(developmentCard2Cost,1, ColorCard.green,4,null);
        player.getLeaderCards().add(leaderCard1);
        player.getLeaderCards().add(leaderCard2);
        player.getPersonalBoard().putCardOnTop(developmentCard1,1);
        player.getPersonalBoard().putCardOnTop(developmentCard2,2);
        player.getPersonalBoard().getWarehouse().addResourcesToDepot(3,ResourceType.stone,3);
        toAddToStrongbox.put(ResourceType.stone,2);
        player.getPersonalBoard().getWarehouse().addResourcesToStrongbox(toAddToStrongbox);



        assertTrue(player.getPersonalBoard().checkLeaderRequirements(leaderCard1));
        assertFalse(player.getPersonalBoard().checkLeaderRequirements(leaderCard2));


    }

    @Test
    void getReducedVersionTest() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, DepotNotFoundException {
        Map<ResourceType,Integer> developmentCard2Cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
        developmentCard2Cost.put(ResourceType.coin, 2);
        developmentCard2Cost.put(ResourceType.servant,1);
        developmentCard2Cost.put(ResourceType.shield, 0);
        developmentCard2Cost.put(ResourceType.stone, 0);

        Map<ResourceType,Integer> developmentCard3Cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
        developmentCard3Cost.put(ResourceType.coin, 2);
        developmentCard3Cost.put(ResourceType.servant,1);
        developmentCard3Cost.put(ResourceType.shield, 0);
        developmentCard3Cost.put(ResourceType.stone, 0);

        DevelopmentCard developmentCard2 = new DevelopmentCard(developmentCard2Cost,1, ColorCard.yellow,4, null);
        DevelopmentCard developmentCard3 = new DevelopmentCard(developmentCard3Cost, 1, ColorCard.green, 3, null);

        personalBoard.putCardOnTop(developmentCard2, 2);
        personalBoard.putCardOnTop(developmentCard3, 3);


        Slot[] expectedSlots = new Slot[3];
        expectedSlots[0] = new Slot();
        expectedSlots[1] = new Slot();
        expectedSlots[2] = new Slot();
        expectedSlots[1].putCardOnTop(developmentCard2);
        expectedSlots[2].putCardOnTop(developmentCard3);

        ReducedDepot[] expectedReducedDepots = new ReducedDepot[3];
        expectedReducedDepots[0] = new ReducedDepot(1, ResourceType.coin,1);
        expectedReducedDepots[1] = new ReducedDepot(2, ResourceType.servant,2);
        expectedReducedDepots[2] = new ReducedDepot(0, null, 3);

        personalBoard.getWarehouse().addResourcesToDepot(1,ResourceType.coin,1);
        personalBoard.getWarehouse().addResourcesToDepot(2, ResourceType.servant,2);

        personalBoard.getWarehouse().getterUnsafeExtraDepots()[0] = new ExtraDepot(ResourceType.servant);
        personalBoard.getWarehouse().addResourcesToExtraDepot(ResourceType.servant,1);

        ReducedDepot reducedExtraDepotExpected = new ReducedDepot(1,ResourceType.servant,2);

        assertTrue(Arrays.asList(expectedSlots).containsAll(Arrays.asList(personalBoard.getReducedVersion().getSlots())) &&
                Arrays.asList(personalBoard.getReducedVersion().getSlots()).containsAll(Arrays.asList(expectedSlots)));

        assertEquals(reducedExtraDepotExpected,personalBoard.getReducedVersion().getWarehouse().getExtraDepots()[0]);

        for(int i = 0; i < expectedReducedDepots.length; i++)
            assertEquals(expectedReducedDepots[i],personalBoard.getReducedVersion().getWarehouse().getNormalDepots()[i]);


    }

}