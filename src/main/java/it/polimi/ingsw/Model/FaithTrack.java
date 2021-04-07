package it.polimi.ingsw.Model;


public class FaithTrack {
    private int faithMarker;
    private int passedSection = 0;
    private final int victoryPoints[] = new int[25];
    private VaticanReportSection[] sections = new VaticanReportSection[3];

    public void moveMarker(int pos){
        for(int i=0; i<pos; i++){
            this.faithMarker++;
            if(sections[passedSection].isPopeSpace(this.faithMarker)){
                this.activeVaticanReport(passedSection++);// attiva rapporto in vaticano
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
        // chiama il metodo vaticanReport nella classe GAME
        if (this.sections[vatican_index].getState() == StateFavorTiles.FACEDOWN)
        {
            //this.sections[vatican_index].setValidFavorTiles(StateFavorTiles.FACEUP);
            Game.activateVaticanReport(vatican_index);
        }
    }
}
