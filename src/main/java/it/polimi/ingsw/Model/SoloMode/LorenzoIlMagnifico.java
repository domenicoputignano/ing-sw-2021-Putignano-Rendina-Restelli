package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Model.FaithTrack;

public class LorenzoIlMagnifico {
    private int blackCross = 0;
    private int passedSection = 0;
    private final FaithTrack faithTrack;
    private SoloMode soloGame;

    public LorenzoIlMagnifico(FaithTrack faithTrack, SoloMode soloGame)
    {
        this.faithTrack = faithTrack;
        this.soloGame = soloGame;
    }

    public void moveBlackCross(int pos){
        for(int i=0; i<pos; i++){
            this.blackCross++;
            if(faithTrack.isPopeSpace(passedSection,blackCross)){
                this.faithTrack.activeVaticanReport(passedSection);// attiva rapporto in vaticano
            }
            if(this.blackCross >= faithTrack.getPopeSpace(passedSection)){
                if(passedSection < 2)// controllo se ho sorpassato la vaticanReportSection attuale
                    this.passedSection++;
            }
        }
    }

    public void moveAndShuffle()
    {
        this.soloGame.refreshTokens();
    }

    public int getBlackCross() {
        return blackCross;
    }

    public int getPassedSection() {
        return passedSection;
    }
}
