package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Commons.ColorCard;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Model.ConclusionEvents.BlackCrossHitLastSpace;
import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;
import it.polimi.ingsw.Model.ConclusionEvents.DevCardColorEnded;
import it.polimi.ingsw.Model.ConclusionEvents.GameEvent;
import it.polimi.ingsw.Model.FaithTrack;
import it.polimi.ingsw.Observable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class LorenzoIlMagnifico extends Observable<GameEvent> {
    private int blackCross = 0;
    private int passedSection = 0;
    private final FaithTrack faithTrack;
    private final SoloMode soloGame;

    public LorenzoIlMagnifico(FaithTrack faithTrack, SoloMode soloGame)
    {
        this.faithTrack = faithTrack;
        this.soloGame = soloGame;
        this.addObserver(this.soloGame);
    }

    public void moveBlackCross(int pos) {
        for(int i=0; i<pos; i++){
            this.blackCross++;
            if(faithTrack.isPopeSpace(passedSection,blackCross)){
                this.faithTrack.activeVaticanReport(soloGame.getCurrPlayer(),passedSection);// attiva rapporto in vaticano
            }
            if(this.blackCross >= faithTrack.getPopeSpace(passedSection)){
                if(passedSection < 2)// controllo se ho sorpassato la vaticanReportSection attuale
                    this.passedSection++;
            }
            if(blackCross == 24) {
                notify(new BlackCrossHitLastSpace());
                break;
            }
        }
    }

    public void throwDevCards(ColorCard color)
    {
        for(int i=0;i<2;i++) {
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

    public void moveAndShuffle() {
        this.moveBlackCross(1);
        this.soloGame.refreshTokens();
    }

    public int getBlackCross() {
        return blackCross;
    }

    public int getPassedSection() {
        return passedSection;
    }
}
