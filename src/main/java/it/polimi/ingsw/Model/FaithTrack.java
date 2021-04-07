package it.polimi.ingsw.Model;


import it.polimi.ingsw.Observable;

public class FaithTrack extends Observable<Integer> {
    private int faithMarker;
    private int passedSection = 0;
    private final int victoryPoints[] = new int[25];
    private VaticanReportSection[] sections = new VaticanReportSection[3];

    public void moveMarker(int pos){
        for(int i=0; i<pos; i++){
            this.faithMarker++;
            if(sections[passedSection].isPopeSpace(this.faithMarker)){
                this.activeVaticanReport(passedSection);// attiva rapporto in vaticano
            }
            if(this.faithMarker >= sections[passedSection].getPopeSpace()){ // controllo se ho sorpassato la vaticanReportSection attuale
                this.passedSection++;
            }
        }
    }
    public int calcVictoryPoints()
    {
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
            notify(vatican_index);
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
}
