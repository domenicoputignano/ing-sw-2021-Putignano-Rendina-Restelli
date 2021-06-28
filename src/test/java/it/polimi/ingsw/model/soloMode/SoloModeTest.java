package it.polimi.ingsw.model.soloMode;

import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.marketTray.Marble;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.MarketChoice;
import it.polimi.ingsw.utils.messages.clientMessages.*;
import it.polimi.ingsw.utils.Pair;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SoloModeTest {

    List<Player> playerList = new ArrayList<>();
    public List<Player> getPlayerList() {
        return playerList;
    }

    @Test
    void setup() {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        assertNotNull(game.getDecks());
        assertNotNull(game.getCurrPlayer());
        assertNotNull(game.getMarketTray());
        for(Player p:playerList) {
            assertNotNull(game.getPlayerList().get(getPlayerList().indexOf(p)).getPersonalBoard());
            assertNotNull(game.getPlayerList().get(getPlayerList().indexOf(p)).getLeaderCards());
            assertEquals(getPlayerList().indexOf(p)+1, game.getPlayerList().get(getPlayerList().indexOf(p)).getPosition());
        }
    }

    @Test
    void refreshTokens() {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        game.refreshTokens();
        assertTrue(game.getTokens().size()==7);
    }

    @Test
    void lorenzoPlaysBaseCase() {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        game.refreshTokens();
        game.getLorenzoIlMagnifico().moveBlackCross(7);
        int startBlackCross = game.getLorenzoIlMagnifico().getBlackCross();
        int startPassedSection = game.getLorenzoIlMagnifico().getPassedSection();
        TokenEffect topTokenEffect = game.peekToken().getTokenEffect();
        System.out.println(topTokenEffect);
        game.lorenzoPlays();
        if(topTokenEffect instanceof DiscardTwoGreenCards) assertTrue(game.getDecks().get(0).getCards().size()==2);
        else if(topTokenEffect instanceof DiscardTwoPurpleCards) assertTrue(game.getDecks().get(1).getCards().size()==2);
        else if(topTokenEffect instanceof DiscardTwoBlueCards) assertTrue(game.getDecks().get(2).getCards().size()==2);
        else if(topTokenEffect instanceof DiscardTwoYellowCards) assertTrue(game.getDecks().get(3).getCards().size()==2);
        else if(topTokenEffect instanceof MoveBlackCrossAndShuffle)
        {
           assertEquals(game.getLorenzoIlMagnifico().getBlackCross(),startBlackCross+1);
           assertEquals(game.getLorenzoIlMagnifico().getPassedSection(),startPassedSection+1);
            assertTrue(game.getTokens().size()==7);
        }
        else{
            assertEquals(game.getLorenzoIlMagnifico().getBlackCross(),startBlackCross+2);
            assertEquals(game.getLorenzoIlMagnifico().getPassedSection(),startPassedSection+1);
        }

    }

    @Test
    void lorenzoPlaysEndGameCase() {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        game.refreshTokens();
        for(int j=0;j<12;j++)
            for(int i=0;i<4;i++) {
                game.getDecks().get(j).draw();
                if((j==8||j==9||j==10||j==11) && i==2)
                    i=4;
            }
        game.getLorenzoIlMagnifico().moveBlackCross(7);
        int startBlackCross = game.getLorenzoIlMagnifico().getBlackCross();
        int startPassedSection = game.getLorenzoIlMagnifico().getPassedSection();
        TokenEffect topTokenEffect = game.peekToken().getTokenEffect();
        System.out.println(topTokenEffect);
        game.lorenzoPlays();
        if(topTokenEffect instanceof DiscardTwoGreenCards) assertTrue(game.getDecks().get(0).getCards().size()==0);
        else if(topTokenEffect instanceof DiscardTwoPurpleCards) assertTrue(game.getDecks().get(1).getCards().size()==0);
        else if(topTokenEffect instanceof DiscardTwoBlueCards) assertTrue(game.getDecks().get(2).getCards().size()==0);
        else if(topTokenEffect instanceof DiscardTwoYellowCards) assertTrue(game.getDecks().get(3).getCards().size()==0);
        else if(topTokenEffect instanceof MoveBlackCrossAndShuffle)
        {
            assertEquals(game.getLorenzoIlMagnifico().getBlackCross(),startBlackCross+1);
            assertEquals(game.getLorenzoIlMagnifico().getPassedSection(),startPassedSection+1);
            assertTrue(game.getTokens().size()==7);
        }
        else{
            assertEquals(game.getLorenzoIlMagnifico().getBlackCross(),startBlackCross+2);
            assertEquals(game.getLorenzoIlMagnifico().getPassedSection(),startPassedSection+1);
        }
    }

    @Test
    void activateVaticanReport()
    {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        assertEquals(StateFavorTiles.FACEDOWN, game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        game.getCurrPlayer().getPersonalBoard().moveMarker(game.getCurrPlayer(), 8);
        assertEquals(StateFavorTiles.FACEUP, game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
    }

    @RepeatedTest(1)
    void activateVaticanReportByLorenzoIlMagnifico() {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        assertEquals(StateFavorTiles.FACEDOWN, game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        game.getLorenzoIlMagnifico().moveBlackCross(8);
        assertEquals(StateFavorTiles.DISCARDED, game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
    }

    @RepeatedTest(10)
    void takeResourcesFromMarketGamePlay() throws InvalidActionException, WhiteEffectMismatchException, NeedPositioningException {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.getCurrPlayer().getLeaderCards().remove(2);
        game.getCurrPlayer().getLeaderCards().remove(2);
        TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
        message.setPlayerChoice(MarketChoice.ROW, 2);
        game.getTurn().getPlayer().getLeaderCards().clear();
        game.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE, ResourceType.shield),null,null,0));
        game.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.coin),null,null,0));
        game.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        game.getTurn().getPlayer().getLeaderCards().get(1).setIsActive();
        List<Marble> marbles = game.getMarketTray().peekMarbles(MarketChoice.ROW, 1);
        List<Integer> effects = new ArrayList<>();
        int i = 0;
        for(Marble m : marbles) {
            if(m.getColor() == ColorMarble.WHITE && (i%2!=0)) {
                effects.add(1);
                message.addWhereToPutMarbles(new Pair<>(new ReducedMarble(ColorMarble.WHITE),MarbleDestination.DEPOT1));
                i++;
            }
            else if(m.getColor() == ColorMarble.WHITE && (i%2==0)){
                    effects.add(2);
                    message.addWhereToPutMarbles(new Pair<>(new ReducedMarble(ColorMarble.WHITE),MarbleDestination.DEPOT1));
                    i++;
            } else {
                message.addWhereToPutMarbles(new Pair<>(m.getReducedVersion(),MarbleDestination.DISCARD));
            }
        }
        message.setWhiteEffects(effects);
        game.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        if(i>1) {
            assertThrows(NeedPositioningException.class,()->game.getTurn().getTurnPhase().takeResourcesFromMarket(game.getTurn(),message));
        }
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) game.getTurn().getTurnPhase();
        assertTrue(takeResourcesFromMarket.checkValidWhiteEffects(game.getTurn(), message.getWhiteEffects(),message.getRequestedMarbles()));
    }

    @Test
    void LeaderAction() throws InvalidActionException, LeaderStatusException, LeaderRequirementsException {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.getCurrPlayer().getLeaderCards().remove(2);
        game.getCurrPlayer().getLeaderCards().remove(2);
        LeaderActionMessage leaderActionMessage = new LeaderActionMessage();
        leaderActionMessage.setIndex(1);
        leaderActionMessage.setToDiscard(true);
        game.getTurn().getPlayer().getPersonalBoard().moveMarker(game.getTurn().getPlayer(), 8);
        int start = game.getTurn().getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker();
        game.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
        game.getTurn().getTurnPhase().leaderAction(game.getTurn(),leaderActionMessage);
        assertTrue(game.getTurn().getPlayer().getLeaderCards().size()==1);
        assertEquals(start+1,game.getTurn().getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker());

    }
}