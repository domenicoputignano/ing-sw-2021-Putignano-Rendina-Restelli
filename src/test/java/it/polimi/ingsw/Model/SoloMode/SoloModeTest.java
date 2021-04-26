package it.polimi.ingsw.Model.SoloMode;

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
        System.out.println(game.getTokens());
    }

    @Test
    void lorenzoPlaysBaseCase() {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        game.refreshTokens();
        TokenEffect topTokenEffect = game.getTokens().get(6).getTokenEffect();
        System.out.println(topTokenEffect);
        game.lorenzoPlays();
        for(int i=0;i<game.getDecks().size();i++)
            System.out.println((i+1)+": "+game.getDecks().get(i));
        System.out.println("Black Cross: "+ game.getLorenzoIlMagnifico().getBlackCross());
        System.out.println("Passed Section: "+ game.getLorenzoIlMagnifico().getPassedSection());
    }

    @Test
    void lorenzoPlays()
    {
        playerList.add(new Player("Pippo"));
        SoloMode game = new SoloMode(playerList.get(0));
        game.setup();
        game.refreshTokens();
        game.getDecks().get(0).draw();
        game.getDecks().get(0).draw();
        game.getDecks().get(0).draw();
        game.getDecks().get(0).draw();
        game.getDecks().get(4).draw();
        game.getDecks().get(4).draw();
        game.getDecks().get(4).draw();
        game.getDecks().get(4).draw();
        game.getDecks().get(8).draw();
        game.getDecks().get(8).draw();
        game.getDecks().get(8).draw();
        TokenEffect topTokenEffect = game.getTokens().get(6).getTokenEffect();
        System.out.println(topTokenEffect);
        game.lorenzoPlays();
        for(int i=0;i<game.getDecks().size();i++)
            System.out.println((i+1)+": "+game.getDecks().get(i));
        System.out.println("Black Cross: "+ game.getLorenzoIlMagnifico().getBlackCross());
        System.out.println("Passed Section: "+ game.getLorenzoIlMagnifico().getPassedSection());
    }
}