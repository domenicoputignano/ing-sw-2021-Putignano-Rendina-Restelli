package it.polimi.ingsw.model.soloMode;

import it.polimi.ingsw.commons.ColorCard;
import it.polimi.ingsw.commons.Deck;
import it.polimi.ingsw.commons.StateFavorTiles;
import it.polimi.ingsw.model.gameEvents.*;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.Observable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents Lorenzo il Magnifico, who the player has to face in {@link SoloMode}.
 * Every time the player passes the turn, Lorenzo performs his action by drawing a {@link Token}
 * and performing the associated effect.
 * Lorenzo can move on the faith track like any other player and can activate Vatican Reports.
 */
public class LorenzoIlMagnifico extends Observable<GameEvent> {
    /**
     * The marker of Lorenzo's position on the Faith Track.
     */
    private int blackCross = 0;
    /**
     * The index of the last Vatican Report section passed by Lorenzo.
     */
    private int passedSection = 0;
    /**
     * The player's {@link FaithTrack} instance.
     */
    private final FaithTrack faithTrack;
    /**
     * The instance of the ongoing game.
     */
    private final SoloMode soloGame;

    /**
     * Constructs an instance of Lorenzo il Magnifico from the given parameters.
     * @param faithTrack the faith track of the player.
     * @param soloGame the instance of the ongoing game.
     */
    public LorenzoIlMagnifico(FaithTrack faithTrack, SoloMode soloGame)
    {
        this.faithTrack = faithTrack;
        this.soloGame = soloGame;
        this.addObserver(this.soloGame);
    }

    /**
     * Moves Lorenzo's cross forward on player's Faith Track.
     * @param pos indicates number of cells that has been covered.
     * @return whether Lorenzo has activated a Vatican Report.
     */
    public boolean moveBlackCross(int pos) {
        boolean isAVaticanReportTriggered = false;
        for(int i=0; i<pos; i++){
            this.blackCross++;
            if(faithTrack.isPopeSpace(passedSection,blackCross)){
                if(faithTrack.getStateFavorTile(passedSection) == StateFavorTiles.FACEDOWN) {
                    isAVaticanReportTriggered = true;
                    notify(new LorenzoActivatedAVaticanReport(passedSection));
                }
            }
            if(this.blackCross >= faithTrack.getPopeSpace(passedSection)){
                if(passedSection < 2) //check if current vatican section has been overcame.
                    this.passedSection++;
            }
            if(blackCross >= 24) {
                notify(new BlackCrossHitLastSpace());
                break;
            }
        }
        return isAVaticanReportTriggered;
    }

    /**
     * Perform Lorenzo's action of discarding two development cards from decks. Player loses the possibility to buy them,
     * If cards of a certain color finish, notifies Game instance that the event occurred.
     * @param color represents color of the cards to be discarded.
     */
    public void throwDevCards(ColorCard color)
    {
        for(int i=0; i<2 ; i++) {
               List<Deck> decksInvolved = this.soloGame.getDecks().stream().filter(x -> x.getCardType().getColor() == color).collect(Collectors.toList());
               Optional<Deck> deckInvolved = decksInvolved.stream().filter(x -> x.getCardType().getLevel()==1 && x.getSize()>0).findFirst();
               if(deckInvolved.isPresent())
                    deckInvolved.ifPresent(Deck::draw);
               else
               {
                   deckInvolved = decksInvolved.stream().filter(x -> x.getCardType().getLevel()==2 && x.getSize()>0).findFirst();
                   if(deckInvolved.isPresent())
                       deckInvolved.ifPresent(Deck::draw);
                   else{
                           deckInvolved = decksInvolved.stream().filter(x -> x.getCardType().getLevel()==3 && x.getSize()>0).findFirst();
                            if(deckInvolved.isPresent()) {
                                deckInvolved.ifPresent(Deck::draw);
                                if(this.soloGame.getDecks().stream().filter(x -> x.getCardType().getColor()==color).allMatch(x -> x.getSize()==0)) {
                                    notify(new DevCardColorEnded());
                                }
                            }
                       }
               }
        }
    }

    /**
     * Moves black cross forward by one position, then refresh the stack of tokens.
     */
    public void moveAndShuffle() {
        this.moveBlackCross(1);
        this.soloGame.refreshTokens();
    }

    /**
     * @return Lorenzo's position on the faith track.
     */
    public int getBlackCross() {
        return blackCross;
    }

    /**
     * @return the index of the last Vatican Report section passed by Lorenzo.
     */
    public int getPassedSection() {
        return passedSection;
    }
}
