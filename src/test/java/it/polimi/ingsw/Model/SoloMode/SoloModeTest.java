package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;
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
}