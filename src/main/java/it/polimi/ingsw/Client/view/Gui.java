package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Client.view.GUI.*;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;
import javafx.application.Platform;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gui extends UI{

    public Gui(Client client) {
        super(client);
    }

    @Override
    public void manageUserInteraction() {

    }

    @Override
    public void changeClientState(AbstractClientState clientState) {

    }


    @Override
    public void render(ServerAsksForNickname message) {
        try {
            GUIApp.waitForGameSetup(client);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, "Listening thread has been accidentally interrupted");
        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, "Listening thread has been accidentally interrupted");
        }
        Platform.runLater(() -> GUIApp.showScene("/gui/fxml/UserName.fxml")
        );
    }

    @Override
    public void render(ServerAskForGameMode message) {
        Platform.runLater(() -> GUIApp.showScene("/gui/fxml/SelectModePage.fxml")
        );
    }

    @Override
    public void render(ServerAskForNumOfPlayer message) {
        Platform.runLater(() -> GUIApp.showScene("/gui/fxml/SelectNumOfPlayersPage.fxml")
        );
    }

    @Override
    public void render(GameSetupMessage message) {
        Platform.runLater(() -> GUIApp.showScene("/gui/fxml/LeaderChoicePage.fxml"));
    }

    @Override
    public void render(GameResumedMessage message) {
        Platform.runLater(() -> {
            GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
            ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
        });
    }

    @Override
    public void render(InitialLeaderChoiceUpdate message) {
        if(isReceiverAction(message.getUser())){
            if(isSoloMode()){
                Platform.runLater(() -> {
                    GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                    ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                });
            } else {
                if(client.getUserPosition() > 1) {
                    Platform.runLater(() -> GUIApp.showScene("/gui/fxml/ResourceChoicePage.fxml"));
                } else {
                    // TODO MANDARE IN UNA SCHERMATA DI ATTESA
                }
            }
        }

    }

    @Override
    public void render(InitialResourceChoiceUpdate message) {
        // Useless in GUI because this update is followed by a NewTurnUpdate which begins the game
    }

    @Override
    public void render(ServerAsksForPositioning message) {
        if(isReceiverAction(message.getUser())){
            Platform.runLater(() -> {
                GUIApp.controller.showPopup("/gui/fxml/PositioningResourcesPage.fxml", 1180, 750);
                ((PositioningResourcesController)GUIApp.controller).setResourcesToSettle(message.getResourcesToSettle());
            });
        }
    }

    @Override
    public void render(NewTurnUpdate message) {
        Platform.runLater(() -> {
            GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
            ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
            if(isReceiverAction(message.getCurrentUser())){
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                ((GenericPopupController)GUIApp.controller).setText("It's your turn!");
            } else {
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                ((GenericPopupController)GUIApp.controller).setText("It's " + message.getCurrentUser().getNickname() + "'s turn!\n" +
                        "You can see his personal board by clicking on \"view other players\" button");
            }
        });
    }

    @Override
    public void render(TakeResourcesFromMarketUpdate message) {
        if(isReceiverAction(message.getUser())){
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showTakeResourcesFromMarketUpdate();
                ((TakeResPopupController)GUIApp.controller).setImages(message);
            });
        } else {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                ((GenericPopupController)GUIApp.controller).setText("User " + message.getUser().getNickname() + " has correctly taken resources from market. " +
                        "Click \"view other players\" to see the results");
            });
        }
    }

    @Override
    public void render(FaithMarkerUpdate message) {
        if(isReceiverAction(message.getUser())) {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
            });
        }
    }

    @Override
    public void render(MoveUpdate message) {
        if(isReceiverAction(message.getUser())){
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showMoveResourcesUpdate();
                ((GenericPopupController)GUIApp.controller).setText("You move action has been correctly performed");
            });
        } else {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showMoveResourcesUpdate();
                ((GenericPopupController)GUIApp.controller).setText("User " + message.getUser() + " has moved resources");
            });
        }
    }

    @Override
    public void render(LeaderActionUpdate message) {
        if(isReceiverAction(message.getUser())){
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showLeaderActionUpdate();
                ((LeaderActionPopupController)GUIApp.controller).setImage(message);
            });
        } else {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                if(message.hasBeenDiscarded()){
                    ((GenericPopupController)GUIApp.controller).setText("User " + message.getUser() + " has discarded a leader card!");
                } else {
                    ((GenericPopupController)GUIApp.controller).setText("User " + message.getUser() + " has activated a leader card!");
                }
            });
        }
    }

    @Override
    public void render(PositioningUpdate message) {
        if(isReceiverAction(message.getUser())){
            String targetUsers;
            if(isSoloMode())
                targetUsers = "Lorenzo";
            else targetUsers = "the other players";
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                if(message.getDiscardedResources().size()==0)
                    ((GenericPopupController)GUIApp.controller).setText("You correctly positioned all the pending resources!\nLook at your depots to see the results !");
                else
                    ((GenericPopupController)GUIApp.controller).setText("You incorrectly positioned some pending resources!\nThe resources have been discarded and "+targetUsers+" obtained one faith point for each resource discarded ! ");

            });
        }
        else {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController) GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                if (message.getDiscardedResources().size() == 0)
                    ((GenericPopupController) GUIApp.controller).setText("Player " + message.getUser() + " has correctly positioned all the pending resources!\nClick view other players to see the results !");
                else
                    ((GenericPopupController) GUIApp.controller).setText("Player " + message.getUser() + " incorrectly positioned some pending resources!\nThe resources have been discarded and you obtained " + message.getDiscardedResources().size() + " faith points ! ");
            });
        }
    }

    @Override
    public void render(LorenzoPlayedUpdate message) {
        Platform.runLater(() -> {
            GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
            ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
            GUIApp.controller.showLorenzoPlayedPopup();
            ((LorenzoPlayedPopupController)GUIApp.controller).setTokenRender(message);
        });

    }

    @Override
    public void render(BuyDevCardPerformedUpdate message) {
        if(isReceiverAction(message.getUser())) {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showBuyDevCardUpdate();
                ((BuyDevCardPopupController)GUIApp.controller).setImage(message);
            });
        } else {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                ((GenericPopupController)GUIApp.controller).setText("User " + message.getUser() + " has correctly bought a card. " +
                        "Click \"view other players\" on your player board to see the results");
            });
        }
    }

    @Override
    public void render(ActivateProductionUpdate message) {
        if(isReceiverAction(message.getUser())) {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/ActivateProductionPopup.fxml", 500, 400);
                ((ActivateProductionUpdateController)GUIApp.controller).setObtainedResources(message);
            });
        } else {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                ((GenericPopupController)GUIApp.controller).setText("User " + message.getUser() + " has correctly activated productions. " +
                        "Click \"view other players\" on your player board to see the results");
            });
        }
    }

    @Override
    public void render(NotAvailableNicknameMessage message) {
        Platform.runLater(() -> {
            GUIApp.showScene("/gui/fxml/UserName.fxml");
            ((UserNameController) GUIApp.controller).setErrorText("Nickname not available, choose another");
        });
    }

    @Override
    public void render(ActivateVaticanReportUpdate message) {
        Platform.runLater(() -> {
            GUIApp.showScene("/gui/FXML/PlayerBoard.fxml");
            ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
            GUIApp.controller.showPopup("/gui/FXML/ActivateVaticanReportPopup.fxml", 500, 400);
            ((ActivateVaticanReportPopupController)GUIApp.controller).setPopup(message);
        });

    }

    @Override
    public void render(JoinLobbyMessage message) {
        List<String> nicknames = message.getAwaitingGuests();
        for(String nickname : nicknames) {
            if(nicknames.indexOf(nickname) == 0) {
                if(isReceiverAction(new User(nickname))) Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer1("You"));
                else Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer1(nickname));
            }
            if(nicknames.indexOf(nickname) == 1) {
                if(isReceiverAction(new User(nickname))) Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer2("You"));
                else Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer2(nickname));
            }
            if(nicknames.indexOf(nickname) == 2) {
                if(isReceiverAction(new User(nickname))) Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer3("You"));
                else Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer3(nickname));
            }
            if(nickname.indexOf(nickname) == 3) {
                if(isReceiverAction(new User(nickname))) Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer4("You"));
                else Platform.runLater(() -> ((LobbyController) GUIApp.controller).setPlayer4(nickname));
            }
        }
    }

    @Override
    public void render(LastTurnMessage message) {
        String haveToPlayMessage;
        if(client.getUserPosition()>client.getGame().getPlayer(message.getTriggeringUser()).getPosition()){
            haveToPlayMessage = "You don't have played this turn yet";
        } else {
            haveToPlayMessage = "You have already played this turn";
        }
        if(isReceiverAction(message.getTriggeringUser())){
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                ((GenericPopupController)GUIApp.controller).setText("You triggered a conclusion event!\n" +message.getConclusionEvent().eventTrigger()+
                        "last turn begins!");
            });
        } else {
            Platform.runLater(() -> {
                GUIApp.showScene("/gui/fxml/PlayerBoard.fxml");
                ((PlayerBoardController)GUIApp.controller).initializePersonalBoard(client.getGame().getPlayer(client.getUser()));
                GUIApp.controller.showPopup("/gui/fxml/GenericPopup.fxml", 500, 400);
                ((GenericPopupController)GUIApp.controller).setText("User " +message.getTriggeringUser()+ " triggered a conclusion event!\n" +message.getConclusionEvent().eventTrigger()+
                        "last turn begins!\n" + haveToPlayMessage);
            });
        }
    }

    @Override
    public void render(UserDisconnectedMessage message) {
        //TODO notificare con un popup che scompare dopo un deltaT.
    }

    @Override
    public void render(RankMessage message){
        // TODO aggiungere popup di fine partita multigiocatore
    }

    @Override
    public void render(SoloModeMatchWinnerMessage message){
        Platform.runLater(() -> {
            GUIApp.controller.showPopup("/gui/fxml/SoloModeWinnerMessage.fxml", 600, 500);
            ((SoloModeWinnerMessageController) GUIApp.controller).setText(message);
        });
    }

    @Override
    public void renderError(String errorMessage) {
        Platform.runLater(() -> {
            GUIApp.controller.showErrorMessage();
            ((ErrorPopupController) GUIApp.controller).setErrorText(errorMessage);
        });
    }
}
