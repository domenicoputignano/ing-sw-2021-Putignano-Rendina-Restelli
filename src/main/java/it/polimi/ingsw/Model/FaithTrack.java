package it.polimi.ingsw.Model;


import it.polimi.ingsw.Client.reducedmodel.ReducedFaithTrack;
import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Model.ConclusionEvents.ActivateVaticanReportEvent;
import it.polimi.ingsw.Model.ConclusionEvents.GameEvent;
import it.polimi.ingsw.Model.ConclusionEvents.HitLastSpace;
import it.polimi.ingsw.Observable;

import java.util.Arrays;

public class FaithTrack extends Observable<GameEvent> {
    private int faithMarker;
    private int passedSection;
    private final int victoryPoints[] = new int[25];
    private final VaticanReportSection[] sections = new VaticanReportSection[3];


    public void moveMarker(int pos) {
        for(int i=0; i<pos; i++){
            this.faithMarker++;
            if(sections[passedSection].isPopeSpace(this.faithMarker)){
                this.activeVaticanReport(passedSection);// attiva rapporto in vaticano
            }
            if(this.faithMarker >= sections[passedSection].getPopeSpace()){
                if(passedSection < 2)// controllo se ho sorpassato la vaticanReportSection attuale
                   this.passedSection++;
            }
            if(this.faithMarker == 24)
                notify(new HitLastSpace());
        }
    }

    public int calcVictoryPoints() {
        if(this.victoryPoints[this.faithMarker]!=0) return this.victoryPoints[this.faithMarker];
        else
        {
            for(int i=this.faithMarker-1;i>=0;i--)
            {
                if(this.victoryPoints[i]!=0) return this.victoryPoints[i];
            }
            return 0;
        }
    }

    public void activeVaticanReport(int vatican_index) {

        if (this.sections[vatican_index].getState() == StateFavorTiles.FACEDOWN)
            notify(new ActivateVaticanReportEvent(vatican_index));
    }

    public void setFavorTile(int index, StateFavorTiles state)
    {
        this.sections[index].setValidFavorTiles(state);
    }

    public StateFavorTiles getStateFavorTile(int index) { return this.sections[index].getState(); }

    public boolean isPopeSpace(int vatican_index, int marker){
        if(sections[vatican_index].isPopeSpace(marker)) return true;
        return false;
    }

    public int getPopeSpace(int passedSection)
    {
        return sections[passedSection].getPopeSpace();
    }

    public int[] getVictoryPoints() {
        return victoryPoints;
    }

    public int getFaithMarker() {
        return faithMarker;
    }

    public int getPassedSection() {
        return passedSection;
    }

    public VaticanReportSection[] getSections() {
        return sections;
    }

    public ReducedFaithTrack getReducedVersion(){
        return new ReducedFaithTrack(this.faithMarker, Arrays.stream(sections).map(VaticanReportSection::getState).toArray(StateFavorTiles[]::new));
    }
}
