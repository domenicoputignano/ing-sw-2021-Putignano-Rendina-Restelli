package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

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

    @Override
    public void handleMessage(Client handler) {
        handler.getGame().updatePersonalBoard(this);
        handler.getUI().render(this);
    }
}
