package it.polimi.ingsw.model;


import it.polimi.ingsw.client.reducedmodel.ReducedFaithTrack;
import it.polimi.ingsw.commons.StateFavorTiles;
import it.polimi.ingsw.model.gameEvents.ActivateVaticanReportEvent;
import it.polimi.ingsw.model.gameEvents.GameEvent;
import it.polimi.ingsw.model.gameEvents.HitLastSpace;
import it.polimi.ingsw.Observable;

import java.util.Arrays;

/**
 * This class represents the Faith Track of Maestri del Rinascimento board game.
 * It is a part of the {@link PersonalBoard} where the player can move his faith marker.
 * It is composed by 25 cells and 3 Vatican Report Sections.
 */
public class FaithTrack extends Observable<GameEvent> {
    /**
     * The position of the player on the track.
     */
    private int faithMarker;
    /**
     * The index of the last passed Vatican Report Section.
     */
    private int passedSection;
    /**
     * Each cell of the Faith Track is associated with some victory points, gained by the player
     * when he reaches that cell.
     */
    private final int[] victoryPoints = new int[25];
    /**
     * The three Vatican Report Sections forming the Faith Track.
     */
    private final VaticanReportSection[] sections = new VaticanReportSection[3];

    /**
     * Moves the player forward by the given number of positions.
     * @param movingPlayer the player to move.
     * @param pos the number of positions to move the player by.
     */
    public void moveMarker(Player movingPlayer,int pos) {
        for(int i=0; i<pos; i++){
            this.faithMarker++; // moves the marker forward
            if(sections[passedSection].isPopeSpace(this.faithMarker)){
                this.activeVaticanReport(movingPlayer,passedSection);// actives a vatican report
            }
            if(this.faithMarker >= sections[passedSection].getPopeSpace()){
                if(passedSection < 2) // checks if the current vatican section has been passed
                   this.passedSection++;
            }
            if(this.faithMarker == 24) { // conclusion event reached
                notify(new HitLastSpace());
                break;
            }
        }
    }

    /**
     * Computes the number of victory points earned by the player from his faith track.
     * @return the number of victory points earned.
     */
    private int calcVictoryPointsFaithTrack() {
        if(this.victoryPoints[this.faithMarker]!=0) return this.victoryPoints[this.faithMarker];
        else {
            for(int i=this.faithMarker-1;i>=0;i--) {
                if(this.victoryPoints[i]!=0) return this.victoryPoints[i];
            }
            return 0;
        }
    }

    /**
     * Computes the number of victory points earned by the player from the Favor Tiles.
     * @return the number of victory points earned.
     */
    private int calcVictoryPointsFavorTiles() {
        return Arrays.stream(sections).map(VaticanReportSection::getVictoryPoints).reduce(0,Integer::sum);
    }

    /**
     * Computes the overall number of victory points earned by the player from his Faith Track.
     * @return the number of victory points earned.
     */
    public int calcVictoryPoints() {
        return calcVictoryPointsFaithTrack()+calcVictoryPointsFavorTiles();
    }

    /**
     * Actives a Vatican Report only if the Favor Tile associated with the pope space reached is still faced down.
     * Notifies the {@link Game} of the activation.
     * @param triggerPlayer the player who activated the vatican report.
     * @param vatican_index the index of the section where the vatican report has been activated.
     */
    public void activeVaticanReport(Player triggerPlayer,int vatican_index) {
        if (this.sections[vatican_index].getState() == StateFavorTiles.FACEDOWN)
            notify(new ActivateVaticanReportEvent(triggerPlayer,vatican_index));
    }

    /**
     * Sets the Favor Tile at the given index to the given state.
     * @param index the index of the Favor Tile to set.
     * @param state the state to set.
     */
    public void setFavorTile(int index, StateFavorTiles state) {
        this.sections[index].setValidFavorTiles(state);
    }

    /**
     * @param index the index of the favor tile to get.
     * @return the state of the Favor Tile at the index given.
     */
    public StateFavorTiles getStateFavorTile(int index) { return this.sections[index].getState(); }

    /**
     * Checks if the player has reached a pope space.
     * @param vatican_index the index of the section where the player is.
     * @param marker the position of the player on the Faith Track.
     * @return the check validity.
     */
    public boolean isPopeSpace(int vatican_index, int marker){
        if(sections[vatican_index].isPopeSpace(marker)) return true;
        return false;
    }

    /**
     * @return the position of the pope space cell on the given section.
     */
    public int getPopeSpace(int passedSection) {
        return sections[passedSection].getPopeSpace();
    }

    /**
     * Getter method used for test purposes only.
     */
    public int[] getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * @return the player's position of the Faith Track.
     */
    public int getFaithMarker() {
        return faithMarker;
    }

    /**
     * @return the index of the last passed Vatican Report Section.
     */
    public int getPassedSection() {
        return passedSection;
    }

    /**
     * @return the three Vatican Report Sections forming the Faith Track.
     */
    public VaticanReportSection[] getSections() {
        return sections;
    }

    /**
     * Gets the reduced version of this class in order to be sent through network
     * and update the client's reduced model.
     * @return the serializable version of the faith track to send through network.
     */
    public ReducedFaithTrack getReducedVersion(){
        return new ReducedFaithTrack(this.faithMarker, Arrays.stream(sections).map(VaticanReportSection::getState).toArray(StateFavorTiles[]::new));
    }
}
