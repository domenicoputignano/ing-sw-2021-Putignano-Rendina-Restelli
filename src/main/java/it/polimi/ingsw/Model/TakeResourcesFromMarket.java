package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.MarketTray.*;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.TakeResourcesFromMarketMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TakeResourcesFromMarket implements AbstractTurnPhase{

    List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
    int faith = 0;

    @Override
    public void takeResourcesFromMarket(Turn turn, TakeResourcesFromMarketMessage takeResourcesFromMarketMessage)
    {
    }

    private void convertWhiteMarbles(Turn turn, List<Pair<Marble, MarbleDestination>> pairList, TakeResourcesFromMarketMessage takeResourcesFromMarketMessage) {
        List<ResourceType> whiteMarbleEffects = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect()==Effect.CMARBLE).
                map(x -> x.getType()).collect(Collectors.toList());
        List<Marble> marbles = pairList.stream().map(Pair::getKey).collect(Collectors.toList());
        for(Marble m : marbles) {
            int i = 0;
            if(m.getColor()==Color.WHITE) {
                if(whiteMarbleEffects.size()==1) {
                    WhiteMarble y = (WhiteMarble) m;
                    y.setEffect(convertTypeToMarbleEffect(whiteMarbleEffects.get(0)));
                } else if(whiteMarbleEffects.size() == 2) {
                    WhiteMarble y = (WhiteMarble) m;
                    y.setEffect(convertTypeToMarbleEffect(whiteMarbleEffects.get(takeResourcesFromMarketMessage.getWhiteEffects().get(i))));
                    i++;
                }
            }
        }
    }

    private void convertMarblesToResources(List<Pair<Marble,MarbleDestination>> marbles) {
        for(Pair<Marble,MarbleDestination> p : marbles) {
            if(p.getKey().getColor() == Color.RED)
                faith++;
            marbles.remove(p);
        }
        ResourceType resourceType;
        for(Pair<Marble,MarbleDestination> p : marbles) {
            try {
                resourceType = p.getKey().addResources();
                whereToPutResources.add(new Pair<ResourceType,MarbleDestination>(resourceType,p.getValue()));
            } catch (NoSuchResourceTypeException e) {
            }
        }
    }

    private WhiteMarbleEffect convertTypeToMarbleEffect(ResourceType type) {
        if(type == ResourceType.coin) return new Coin();
        if(type == ResourceType.servant) return new Servant();
        if(type == ResourceType.shield) return new Shield();
        if(type == ResourceType.stone) return new Stone();
        else return null;
    }
}
