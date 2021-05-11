package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LeaderActionTest {

    MultiPlayerMode multiPlayerMode;
    @BeforeEach
    void initialization()
    {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        for(Player p: multiPlayerMode.getPlayerList())
        {
            p.getLeaderCards().remove(2);
            p.getLeaderCards().remove(2);
        }
    }
    @Test
    void leaderActionDiscardLeaderCard() throws InvalidActionException, LeaderStatusException, LeaderRequirementsException {
        LeaderActionMessage leaderActionMessage = new LeaderActionMessage();
        leaderActionMessage.setIndex(1);
        leaderActionMessage.setToDiscard(true);
        int start = multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker();
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getFaithTrack().moveMarker(8);
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
        multiPlayerMode.getTurn().getTurnPhase().leaderAction(multiPlayerMode.getTurn(),leaderActionMessage);
        assertEquals(1, multiPlayerMode.getTurn().getPlayer().getLeaderCards().size());
        assertEquals(start+9,multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker());
    }
    @Test
    void leaderActionDiscardActiveLeaderCard() throws LeaderStatusException, LeaderRequirementsException {
        LeaderActionMessage leaderActionMessage = new LeaderActionMessage();
        leaderActionMessage.setIndex(1);
        leaderActionMessage.setToDiscard(true);
        int start = multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker();
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getFaithTrack().moveMarker(8);
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
        assertThrows(LeaderStatusException.class,()-> multiPlayerMode.getTurn().getTurnPhase().leaderAction(multiPlayerMode.getTurn(),leaderActionMessage));
        assertEquals(2,multiPlayerMode.getTurn().getPlayer().getLeaderCards().size());
        assertTrue(multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).isActive());
        assertEquals(start+8,multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker());
    }

    @Test
    void leaderActionOk() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, LeaderStatusException, LeaderRequirementsException {
        LeaderActionMessage leaderActionMessage = new LeaderActionMessage();
        leaderActionMessage.setIndex(1);
        leaderActionMessage.setToDiscard(false);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(1, ResourceType.coin,1);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(2,ResourceType.shield,2);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(3,ResourceType.servant,3);
        Map<ResourceType,Integer> strongboxResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
        strongboxResources.put(ResourceType.coin,6);
        strongboxResources.put(ResourceType.servant,6);
        strongboxResources.put(ResourceType.stone,6);
        strongboxResources.put(ResourceType.shield,6);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToStrongbox(strongboxResources);
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
        if(multiPlayerMode.getCurrPlayer().getLeaderCards().get(0).getRequirementsCards().size()>0)
            assertThrows(LeaderRequirementsException.class,()-> multiPlayerMode.getTurn().getTurnPhase().leaderAction(multiPlayerMode.getTurn(),leaderActionMessage));
        else {
            multiPlayerMode.getTurn().getTurnPhase().leaderAction(multiPlayerMode.getTurn(),leaderActionMessage);
            assertTrue(multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).isActive());
        }
        assertFalse(multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(1).isActive());
    }

    /*@Test
    void leaderActionOkRequirementsCards() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, LeaderStatusException, LeaderRequirementsException {
        LeaderActionMessage leaderActionMessage = new LeaderActionMessage();
        leaderActionMessage.setIndex(1);
        leaderActionMessage.setToDiscard(false);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(1,ResourceType.coin,1);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(2,ResourceType.shield,2);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(3,ResourceType.servant,3);
        Map<ResourceType,Integer> strongboxResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
        strongboxResources.put(ResourceType.coin,6);
        strongboxResources.put(ResourceType.servant,6);
        strongboxResources.put(ResourceType.stone,6);
        strongboxResources.put(ResourceType.shield,6);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToStrongbox(strongboxResources);

        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.BUYDEVCARD);
        BuyDevCardMessage buyDevCardMessage = new BuyDevCardMessage();


        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
        if(multiPlayerMode.getCurrPlayer().getLeaderCards().get(0).getRequirementsCards().size()>0)
            assertThrows(LeaderRequirementsException.class,()-> multiPlayerMode.getTurn().getTurnPhase().leaderAction(multiPlayerMode.getTurn(),leaderActionMessage));
        else {
            multiPlayerMode.getTurn().getTurnPhase().leaderAction(multiPlayerMode.getTurn(),leaderActionMessage);
            assertTrue(multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).isActive());
        }
        assertFalse(multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(1).isActive());
    }*/
    @Test
    void leaderActionKo() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, LeaderStatusException, LeaderRequirementsException {
        LeaderActionMessage leaderActionMessage = new LeaderActionMessage();
        leaderActionMessage.setIndex(1);
        leaderActionMessage.setToDiscard(false);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(1,ResourceType.coin,1);
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
        assertThrows(LeaderRequirementsException.class,() -> multiPlayerMode.getTurn().getTurnPhase().leaderAction(multiPlayerMode.getTurn(),leaderActionMessage));
        assertFalse(multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).isActive());
        assertFalse(multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(1).isActive());
    }
}