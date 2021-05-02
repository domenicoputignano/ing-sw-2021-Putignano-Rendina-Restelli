package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.LeaderEffect;
import it.polimi.ingsw.Model.MarketTray.Marble;
import it.polimi.ingsw.Model.MarketTray.WhiteMarble;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Pair;
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
    void lorenzoPlaysBaseCase() throws EndGameException {
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
    void lorenzoPlaysEndGameCase() throws EndGameException {
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
        game.getCurrPlayer().getPersonalBoard().getFaithTrack().moveMarker(8);
        assertEquals(StateFavorTiles.FACEUP, game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
    }

    @Test
    void activateVaticanReportByLorenzoIlMagnifico() throws EndGameException {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        assertEquals(StateFavorTiles.FACEDOWN, game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        game.getLorenzoIlMagnifico().moveBlackCross(8);
        assertEquals(StateFavorTiles.DISCARDED, game.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
    }

    @Test
    void takeResourcesFromMarketGamePlay() throws InvalidActionException {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        game.getCurrPlayer().getLeaderCards().remove(2);
        game.getCurrPlayer().getLeaderCards().remove(2);
        TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
        message.setPlayerChoice(MarketChoice.ROW, 2);
        game.getTurn().getPlayer().getLeaderCards().clear();
        game.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE, ResourceType.shield),null,null,0));
        game.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.coin),null,null,0));
        game.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        game.getTurn().getPlayer().getLeaderCards().get(1).setIsActive();
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT3));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT3));
        List<Integer> effects = new ArrayList<>();
        effects.add(1);
        effects.add(1);
        effects.add(1);
        effects.add(2);
        message.setWhiteEffects(effects);
        message.setWhereToPutMarbles(wheretoPutMarbles);
        game.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        game.getTurn().getTurnPhase().takeResourcesFromMarket(game.getTurn(),message);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) game.getTurn().getTurnPhase();
        assertTrue(takeResourcesFromMarket.checkValidWhiteEffects(game.getTurn(), message.getWhiteEffects(),message.getRequestedMarbles()));
    }

    @Test
    void LeaderAction() throws InvalidActionException {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        game.getCurrPlayer().getLeaderCards().remove(2);
        game.getCurrPlayer().getLeaderCards().remove(2);
        LeaderActionMessage leaderActionMessage = new LeaderActionMessage();
        leaderActionMessage.setIndex(1);
        leaderActionMessage.setToDiscard(true);
        game.getTurn().getPlayer().getPersonalBoard().getFaithTrack().moveMarker(8);
        int start = game.getTurn().getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker();
        game.getTurn().setTurnState(TurnState.ActionType.LEADERACTION);
        game.getTurn().getTurnPhase().leaderAction(game.getTurn(),leaderActionMessage);
        assertTrue(game.getTurn().getPlayer().getLeaderCards().size()==1);
        assertEquals(start+1,game.getTurn().getPlayer().getPersonalBoard().getFaithTrack().getFaithMarker());

    }
}