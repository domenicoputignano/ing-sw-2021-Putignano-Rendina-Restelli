package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SoloMode.SoloMode;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.ClientStatus;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.Utils.Pair;
import org.apache.maven.model.Resource;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class GameControllerTest {

    @Test
    void handleResourceChoiceMessage() {
    }

    @Test
    void handleLeaderChoiceMessage() {

    }
}