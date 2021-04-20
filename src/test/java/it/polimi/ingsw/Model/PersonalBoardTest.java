package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Model.Card.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersonalBoardTest {

    Player player = new Player("Ernesto");
    Map<ResourceType, Integer> developmentCard1Cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
    List<CardType> requirementsCards1 = new ArrayList<>();
    List<CardType> requirementsCards2 = new ArrayList<>();

    @BeforeEach
    void initialize() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
    }


    @Test
    void isCompatibleSlot() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        developmentCard1Cost.put(ResourceType.coin, 0);
        developmentCard1Cost.put(ResourceType.servant,0);
        developmentCard1Cost.put(ResourceType.shield, 0);
        developmentCard1Cost.put(ResourceType.stone, 0);
        Map<ResourceType, Integer> toAddToStrongbox = new EnumMap<ResourceType, Integer>(developmentCard1Cost);
        Map<ResourceType, Integer> requirementsResources1 = new EnumMap<>(developmentCard1Cost);
        Map<ResourceType, Integer> requirementsResources2 = new EnumMap<>(developmentCard1Cost);
        Map<ResourceType, Integer> developmentCard2Cost = new EnumMap<ResourceType, Integer>(developmentCard1Cost);
        DevelopmentCard developmentCard1 = new DevelopmentCard(developmentCard1Cost,1, ColorCard.yellow,3,null);
        DevelopmentCard developmentCard2 = new DevelopmentCard(developmentCard2Cost,1, ColorCard.green,4,null);
        player.getPersonalBoard().putCardOnTop(developmentCard1,0);
        player.getPersonalBoard().putCardOnTop(developmentCard2,1);
        assertTrue(player.getPersonalBoard().isCompatibleSlot(1, 1));
    }

    @Test
    void isValidRequestedProduction() {
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
        player.getPersonalBoard().putCardOnTop(developmentCard1,0);
        player.getPersonalBoard().putCardOnTop(developmentCard2,1);
        player.getPersonalBoard().getWarehouse().addResourcesToDepot(3,ResourceType.stone,3);
        toAddToStrongbox.put(ResourceType.stone,2);
        player.getPersonalBoard().getWarehouse().addResourcesToStrongbox(toAddToStrongbox);



        assertTrue(player.getPersonalBoard().checkLeaderRequirements(leaderCard1));
        assertFalse(player.getPersonalBoard().checkLeaderRequirements(leaderCard2));


    }

    @Test
    void getWarehouse() {
    }
}