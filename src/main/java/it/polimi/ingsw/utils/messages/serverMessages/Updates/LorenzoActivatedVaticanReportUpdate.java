package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.StateFavorTiles;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class representing an update sent in case of a solo mode game, when {@link it.polimi.ingsw.model.soloMode.LorenzoIlMagnifico}
 * activates a Vatican Report.
 */
public class LorenzoActivatedVaticanReportUpdate extends UpdateMessage{

    private final int vatican_index;
    private final StateFavorTiles resultingStateFavorTile;

    public LorenzoActivatedVaticanReportUpdate(User user, ReducedPersonalBoard userPersonalBoard, int vatican_index, StateFavorTiles resultingStateFavorTile) {
        this.user = user;
        this.userPersonalBoard = userPersonalBoard;
        this.vatican_index = vatican_index;
        this.resultingStateFavorTile = resultingStateFavorTile;
    }

    public int getVatican_index() {
        return vatican_index;
    }

    public StateFavorTiles getResultingStateFavorTile() {
        return resultingStateFavorTile;
    }
    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.getGame().updatePersonalBoard(this);
        handler.getUI().render(this);
    }
}
