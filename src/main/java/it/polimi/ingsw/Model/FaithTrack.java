package it.polimi.ingsw.Model;


public class FaithTrack {
    private int faithMarker;
    private int passedSection = 0;
    private static final int[] victoryPoints = new int[25];
    private VaticanReportSection[] sections = new VaticanReportSection[3];

    public void moveMarker(int pos){
        for(int i=0; i<pos; i++){
            this.faithMarker++;
            if(sections[passedSection].isPopeSpace(this.faithMarker)){
                this.activeVaticanReport(passedSection++);// attiva rapporto in vaticano
            }
            if(this.faithMarker > sections[passedSection].getPopeSpace()){ // controllo se ho sorpassato la vaticanReportSection attuale
                this.passedSection++;
            }
        }
    }

    public void activeVaticanReport(int vatican_index){
        // chiama il metodo vaticanReport nella classe GAME
    }
}
