package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.ReducedMarble;
import it.polimi.ingsw.Client.clientstates.cli.TakeResourcesFromMarketCLI;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.LeaderEffect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.TakeResourcesFromMarketMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractTakeResourcesFromMarket extends AbstractClientState {
    protected TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
    protected List<ReducedMarble> selectedMarbles = new ArrayList<>();

    public AbstractTakeResourcesFromMarket(Client client) {
        super(client);
    }

    protected void computeSelectedMarbles() {
        this.selectedMarbles = client.getGame().getMarketTray().peekMarbles(message.getPlayerChoice(), message.getIndex()-1);
    }

    protected List<ResourceType> getConvertMarbleActiveEffects(){
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.CMARBLE).stream().
                map(x -> x.getLeaderEffect().getType()).collect(Collectors.toList());
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public TakeResourcesFromMarketCLI getCLIVersion() {
        return new TakeResourcesFromMarketCLI(client);
    }
}
