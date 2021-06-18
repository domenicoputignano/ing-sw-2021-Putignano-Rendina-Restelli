package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Client.clientstates.cli.ActionChoiceCLI;
import it.polimi.ingsw.Client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Exceptions.BackToMenuException;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;
import it.polimi.ingsw.Utils.ResourceSource;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CLI extends UI {

    private final Scanner input = new Scanner(System.in);
    private final ExecutorService interactionManagerService = Executors.newSingleThreadExecutor();

    public CLI(Client client) {
        super(client);
    }


    public Map<ResourceSource, EnumMap<ResourceType, Integer>> askInstructionsOnHowToTakeResources(Map<ResourceType, Integer> neededResources) {
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = initializeHowToTakeResources();
        boolean availableExtraDepot = client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRADEPOT).size() > 0;
        System.out.println("Following resources are available in your warehouse\n" +
                client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse());
        /*do {
            showPossibleSources(neededResources, availableExtraDepot);
            ResourceSource source;
            do {
                source = fromStringToResourceSource(input.next());
            } while (source == null);
            System.out.print("Choose the resource [Coin (C), Servant (SE), Shield (SH), Stone(ST)]:  ");
            ResourceType resource;
            do {
                resource = fromStringToResourceType(input.next());
                if (resource != null) {
                    if (neededResources.get(resource) == 0) {
                        System.out.println("You don't need this type of resources, please select again resource ");
                        resource = null;
                    }
                }
            } while (resource == null);
            System.out.print("Choose the number: ");
            int number;
            boolean isRightNumber = false;
            do {
                number = input.nextInt();
                if (neededResources.get(resource) < number) {
                    System.out.print("Error detected, select again number of occurrences you want to pick: ");
                    isRightNumber = false;
                } else {
                    isRightNumber = true;
                }
            } while (!isRightNumber);
            if (howToTakeResources.get(source).containsKey(resource)) {
                howToTakeResources.get(source).merge(resource, number, Integer::sum);
            } else howToTakeResources.get(source).put(resource, number);
            neededResources.put(resource, neededResources.get(resource) - number);
        } while (!neededResources.values().stream().allMatch(x -> x == 0));*/

        while (!neededResources.values().stream().allMatch(x -> x == 0)) {
            showPossibleSources(neededResources, availableExtraDepot);
            ResourceSource source;
            do {
                source = fromStringToResourceSource(input.next());
            } while (source == null);
            System.out.print("Choose the resource [Coin (C), Servant (SE), Shield (SH), Stone(ST)]:  ");
            ResourceType resource;
            do {
                resource = fromStringToResourceType(input.next());
                if (resource != null) {
                    if (neededResources.get(resource) == 0) {
                        System.out.println("You don't need this type of resources, please select again resource ");
                        resource = null;
                    }
                }
            } while (resource == null);
            System.out.print("Choose the number: ");
            int number;
            boolean isRightNumber = false;
            do {
                number = input.nextInt();
                if (neededResources.get(resource) < number) {
                    System.out.print("Error detected, select again number of occurrences you want to pick: ");
                    isRightNumber = false;
                } else {
                    isRightNumber = true;
                }
            } while (!isRightNumber);
            if (howToTakeResources.get(source).containsKey(resource)) {
                howToTakeResources.get(source).merge(resource, number, Integer::sum);
            } else howToTakeResources.get(source).put(resource, number);
            neededResources.put(resource, neededResources.get(resource) - number);
        }
        return howToTakeResources;
    }

    private void showPossibleSources(Map<ResourceType, Integer> neededResources, boolean availableExtraDepot) {
        if (availableExtraDepot) {
            System.out.print("You need now, following resources to perform your action: " + neededResources +
                    "\nSelect how you want to pick resources from your warehouse\nChoose the source: " +
                    "[Depot, Strongbox, Extra] ");
        } else {
            System.out.print("You need now, following resources to perform your action: " + neededResources +
                    "\nSelect how you want to pick resources from your warehouse\nChoose the source: " +
                    "[Depot, Strongbox] ");
        }
    }


    private Map<ResourceSource, EnumMap<ResourceType, Integer>> initializeHowToTakeResources() {
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        howToTakeResources.put(ResourceSource.DEPOT, new EnumMap<>(ResourceType.class));
        howToTakeResources.put(ResourceSource.STRONGBOX, new EnumMap<>(ResourceType.class));
        howToTakeResources.put(ResourceSource.EXTRA, new EnumMap<>(ResourceType.class));
        return howToTakeResources;
    }


    public MarbleDestination chooseMarbleDestination() {
        System.out.println("Where do you want to position it? [DEPOT1|DEPOT2|DEPOT3|EXTRA|DISCARD]");
        String choice;
        MarbleDestination destination;
        boolean destinationOK = false;
        do {
            choice = input.next().toUpperCase();
            destination = parseMarbleDestination(choice);
            if (destination != null) {
                destinationOK = true;
            } else {
                System.out.println("Invalid choice, try again. [DEPOT1|DEPOT2|DEPOT3|EXTRA|DISCARD]");
            }
        } while (!destinationOK);
        return destination;
    }

    private MarbleDestination parseMarbleDestination(String choice) {
        switch (choice) {
            case "DEPOT1":
                return MarbleDestination.DEPOT1;
            case "DEPOT2":
                return MarbleDestination.DEPOT2;
            case "DEPOT3":
                return MarbleDestination.DEPOT3;
            case "EXTRA":
                return MarbleDestination.EXTRA;
            case "DISCARD":
                return MarbleDestination.DISCARD;
            default:
                return null;
        }
    }

    @Override
    public void render(ServerAsksForNickname message) {
        System.out.print("Choose your nickname: ");
    }

    public void render(ServerAskForGameMode message) {
        System.out.print("Choose game mode [Multiplayer | Solo]: ");
    }

    public void render(ServerAskForNumOfPlayer message) {
        System.out.print("Choose the number of players [2-4]: ");
    }

    public void render(GameSetupMessage message) {
        System.out.println("Welcome "+client.getUser().getNickname()+" are you ready to start?\n" +
                "Meanwhile take a look to your personal board!");
        printPersonalBoard(client.getGame().getPlayer(client.getUser()).getPersonalBoard());
    }

    public void render(InitialLeaderChoiceUpdate message) {
        if (isReceiverAction(message.getUser())) {
            System.out.println("You have successfully discarded two leader cards ");
        } else {
            System.out.println("User " + message.getUser() + " has discarded two leader cards");
        }
    }

    public void render(InitialResourceChoiceUpdate message) {
        if (isReceiverAction(message.getUser())) {
            System.out.println("You have chosen " + message.getChosenResources() + " resources, your depots " +
                    "have been updated as follows");
            showDepots();
        } else System.out.println("User " + message.getUser() + " added " + message.getChosenResources() + " to his" +
                " depots ");
    }

    public void render(ServerAsksForPositioning message) {
        if (isReceiverAction(message.getUser())) {
            System.out.println("You have not correctly positioned the following resources: " + message.getResourcesToSettle());
        } else System.out.println("User " + message.getUser() + "has not correctly positioned some resources.");
    }

    public void render(NewTurnUpdate message) {
        if (isReceiverAction(message.getCurrentUser())) {
            System.out.println("It's now your turn, make the move ");
        } else {
            System.out.println("User "+message.getCurrentUser()+" is now playing");
        }
    }

    public void render(TakeResourcesFromMarketUpdate message) {
        if (isReceiverAction(message.getUser())) {
            System.out.printf("You got following resources from market: " + message.getEarnedResources() + " and %d faith points\n", message.getFaithPoints());
            showDepots();
        } else {
            System.out.println("User " + message.getUser() + "" +
                    " has taken following resources from market: " + message.getEarnedResources() + " and he got " +
                    message.getFaithPoints() + " faith points");
        }

    }


    public void render(FaithMarkerUpdate message) {
        if (isReceiverAction(message.getUser()) && !isReceiverAction(message.getTriggeringUser())) {
            System.out.printf("User " + message.getTriggeringUser() + " has performed action" +
                    " involving faith track, you got %d faith points\n", message.getPoints());
        }
        //printFaithTrack(message.getUserPersonalBoard());
    }

    public void render(MoveUpdate message) {
        if (isReceiverAction(message.getUser())) {
            System.out.println("Your move action has been correctly performed\n" +
                    message.getUserPersonalBoard().getWarehouse());
        } else System.out.println("User " + message.getUser() + " has moved resources in his warehouse");
    }

    public void render(LeaderActionUpdate message) {
        if (isReceiverAction(message.getUser())) {
            if (message.hasBeenDiscarded())
                System.out.println("You discarded following leader card\n" + message.getLeaderCard());
            else System.out.println("You activated following leader card\n" + message.getLeaderCard());
        } else {
            if (message.hasBeenDiscarded())
                System.out.println("User " + message.getUser() + " has discarded this leader card\n" +
                        message.getLeaderCard());
            else
                System.out.println("User " + message.getUser() + " has activated this leader card\n" + message.getLeaderCard());
        }
    }

    public void render(PositioningUpdate message) {
        if (isReceiverAction(message.getUser())) {
            if (message.getDiscardedResources().size() > 0) {
                System.out.println("You haven't correctly positioned the following resources " + message.getDiscardedResources() +
                        " so they have been discarded");
            } else
                System.out.print("You have correctly positioned all the resources you had to settle\nYour depots update" +
                        " are shown below\n");
            showDepots();
        } else {
            if (message.getDiscardedResources().size() > 0) {
                System.out.println("User " + message.getUser() + " hasn't correctly positioned the following resources " +
                        message.getDiscardedResources() + " so you got " + message.getDiscardedResources().size() + " faith points");
            } else
                System.out.println("User " + message.getUser() + " has correctly positioned all the resources he had to settle");
        }
    }

    public void render(GameResumedMessage message) {
        System.out.println("User "+message.getSavedUserInstance()+" has rejoined the game");
    }

    public void render(LorenzoPlayedUpdate message) {
        System.out.println(message.getPlayedToken().getTokenEffect().renderTokenEffect());
        System.out.println("Now, it's your turn...");
    }

    public void render(BlackCrossMoveUpdate message) {
        if(message.isVaticanReportTriggered()) {
            System.out.println("Pay attention! Lorenzo has just activated a Vatican Report!");
        } else {
            System.out.println("Lorenzo reached "+message.getBlackCross()+"° position!");
        }
    }

    public void render(BuyDevCardPerformedUpdate message) {
        if (isReceiverAction(message.getUser())) {
            System.out.println("You successfully bought this development card\n " + message.getBoughtCard());
        } else {
            System.out.println("User " + message.getUser() + " successfully bought a development card of level "
                    + message.getBoughtCard().getType().getLevel() + " and color "
                    + message.getBoughtCard().getType().getColor());
        }
    }

    public void render(ActivateProductionUpdate message) {
        if (isReceiverAction(message.getUser())) {
            System.out.println("You successfully activated production, these resources have been converted" +
                    " ( " + message.getPayedResources() + " )");
            System.out.println("Following resources have been stored in your strongbox: " +
                    message.getReceivedResources());
            if (message.getFaithPoints() > 0)
                System.out.printf("You got also %d faith points\n", message.getFaithPoints());
        } else {
            System.out.println("User " + message.getUser() + " activated production and he got following resources: "
                    + message.getReceivedResources());
            if (message.getFaithPoints() > 0)
                System.out.println("He got also " + message.getFaithPoints() + " faith points");
        }
    }

    @Override
    public void render(NotAvailableNicknameMessage message) {
        System.out.print("Nickname not available, select another one: ");
    }

    @Override
    public void render(ActivateVaticanReportUpdate message) {
        if (isReceiverAction(message.getUser()) && isReceiverAction(message.getTriggeringUser())) {
            System.out.println("You activated a Vatican Report, your faith track has been updated as follows ");
            printFaithTrack(message.getUserPersonalBoard());
        } else if (isReceiverAction(message.getUser())) {
            System.out.println("User " + message.getTriggeringUser() + " activated a Vatican Report, your faith track has been updated as follows ");
            printFaithTrack(message.getUserPersonalBoard());
        }
        /*
        if(isReceiverAction(client.getUser())) {
            System.out.println("You activated a Vatican Report on section + "+message.getSection()+"."
                    +"\nYour tile is now face up ");
            System.out.println("Faith track has been updated as follows");
        } else {
            System.out.println("User " + message.getTriggeringUser() + " activated a Vatican Report on section " + message.getSection() + "."
                    + "\nYour tile is " + message.getState());
        }*/
    }

    @Override
    public void render(JoinLobbyMessage message) {
        if (isGuestWhoHasJustJoined(message.getLastAwaitingGuest())) {
            if (message.getNumOfMissingPlayers() <= 0) {
                System.out.println("All required players joined the lobby, game will start soon...");
            } else {
                System.out.println("You successfully joined the lobby, please wait for other " +
                        message.getNumOfMissingPlayers() + " player(s) to join...");
            }
        } else {
            if (message.getNumOfMissingPlayers() <= 0) {
                System.out.println("Guest " + message.getLastAwaitingGuest() + " joined the lobby, game will start soon...");
            } else {
                System.out.println("Guest " + message.getLastAwaitingGuest() + " joined the lobby, please " +
                        "wait for " + message.getNumOfMissingPlayers() + " player(s) to join...");
            }
        }
        System.out.println("Lobby: " + message.getAwaitingGuests());
    }

    @Override
    public void renderError(String errorMessage) {
        System.out.println(errorMessage);
    }

    @Override
    public void manageUserInteraction() {
        interactionManagerService.submit(() -> clientState.manageUserInteraction());
    }


    public ResourceSource fromStringToResourceSource(String source) {
        if (source.equalsIgnoreCase("depot")) return ResourceSource.DEPOT;
        if (source.equalsIgnoreCase("strongbox")) return ResourceSource.STRONGBOX;
        if (source.equalsIgnoreCase("extra")) return ResourceSource.EXTRA;
        else {
            System.out.println("Error detected, please select again ");
            return null;
        }
    }


    public void showLeaderCards(ReducedPersonalBoard playerBoard) {
        for (LeaderCard card : playerBoard.getAvailableLeaderCards()) {
            System.out.printf("Card n.%d\n", client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().indexOf(card) + 1);
            card.displayASCII();
        }
    }


    public void showDepots(User user) {
        int index = 1;
        for (ReducedDepot depot : client.getGame().getPlayer(user).getPersonalBoard().getWarehouse().getNormalDepots()) {
            System.out.print("Depot " + index + ": " + depot + "\n");
            index = index + 1;
        }
        if (Arrays.stream(client.getGame().getPlayer(user).getPersonalBoard().getWarehouse().getExtraDepots()).anyMatch(Objects::nonNull)) {
            for (ReducedDepot depot : client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse().getExtraDepots()) {
                System.out.print("Extra depot of type: " + depot + "\n");
            }
        }
    }

    public void showDepots() {
        showDepots(client.getUser());
    }

    public ResourceType askValidResource(Scanner input) {
        boolean done = false;
        ResourceType resource = null;
        while (!done) {
            String answer = input.next();
            resource = fromStringToResourceType(answer);
            if (resource != null) {
                done = true;
            }
        }
        return resource;
    }

    public int askValidDepotIndex(Scanner input, int maxIndex) {
        boolean done = false;
        int index = 0;
        while (!done) {
            index = input.nextInt();
            if (index < 1 || index > maxIndex) {
                System.out.printf("Invalid chosen index, please select a number between [1 - %d]: ", maxIndex);
            } else done = true;
        }
        return index;
    }

    public int askValidOcc(Scanner input, int maxOcc) {
        boolean done = false;
        int occ = 0;
        while (!done) {
            occ = input.nextInt();
            if (occ < 1 || occ > maxOcc) {
                System.out.printf("Invalid number of occurrences chosen, please select a number between [1 - %d]: ", maxOcc);
            } else done = true;
        }
        return occ;
    }

    public void returnToActionBeginning(AbstractClientState action) {
        changeClientState(action);
        manageUserInteraction();
    }

    public void returnToMenu() {
        returnToActionBeginning(new ActionChoiceCLI(this.client));
    }

    public void playerWantsToGoBack() throws BackToMenuException {
        System.out.print("Type [esc] if you want to go back to menu, else [any button] to continue this action: ");
        String choice = input.next();
        if (choice.equalsIgnoreCase("esc")) {
            throw new BackToMenuException();
        }
    }

    public void showPlayers() {
        client.getGame().printPlayers();
    }

    private ReducedPlayer playerMatching(String requiredUsername) {
        return client.getGame().getPlayer(new User(requiredUsername));
    }

    public void askAndShowPlayerBoard() {
        showPlayers();
        System.out.print("Choose the player to see his personal board: ");
        ReducedPlayer requiredPlayer = playerMatching(input.next());
        if(requiredPlayer.getNickname()!=null) {
            printPersonalBoard(requiredPlayer.getPersonalBoard());
        } else {
            System.out.println("Invalid username!");
        }
    }



    public synchronized void showMarketTray() {
        System.out.println(client.getGame().getMarketTray());
    }

    @Override
    public void changeClientState(AbstractClientState newClientState) {
        clientState = newClientState;
    }

    public void printDecks() {
        System.out.println(".---------------------------..---------------------------..---------------------------..---------------------------.");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.green, 1) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.blue, 1) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.yellow, 1) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.purple, 1) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.green, 2) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.blue, 2) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.yellow, 2) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.purple, 2) + "|");
        System.out.println("|      .-------------.      ||      .-------------.      ||      .-------------.      ||      .-------------.      |");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.green, 4) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.blue, 4) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.yellow, 4) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.purple, 4) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.green, 5) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.blue, 5) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.yellow, 5) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.purple, 5) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.green, 6) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.blue, 6) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.yellow, 6) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.purple, 6) + "|");
        System.out.println("|      '.............'      ||      '.............'      ||      '.............'      ||      '.............'      |");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.green, 8) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.blue, 8) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.yellow, 8) + "||" + client.getGame().getDeckTopCardAsASCII(3, ColorCard.purple, 8) + "|");
        System.out.println("'---------------------------''---------------------------''---------------------------''---------------------------'");
        System.out.println(".---------------------------..---------------------------..---------------------------..---------------------------.");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.green, 1) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.blue, 1) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.yellow, 1) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.purple, 1) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.green, 2) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.blue, 2) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.yellow, 2) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.purple, 2) + "|");
        System.out.println("|      .-------------.      ||      .-------------.      ||      .-------------.      ||      .-------------.      |");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.green, 4) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.blue, 4) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.yellow, 4) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.purple, 4) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.green, 5) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.blue, 5) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.yellow, 5) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.purple, 5) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.green, 6) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.blue, 6) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.yellow, 6) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.purple, 6) + "|");
        System.out.println("|      '.............'      ||      '.............'      ||      '.............'      ||      '.............'      |");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.green, 8) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.blue, 8) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.yellow, 8) + "||" + client.getGame().getDeckTopCardAsASCII(2, ColorCard.purple, 8) + "|");
        System.out.println("'---------------------------''---------------------------''---------------------------''---------------------------'");
        System.out.println(".---------------------------..---------------------------..---------------------------..---------------------------.");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.green, 1) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.blue, 1) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.yellow, 1) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.purple, 1) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.green, 2) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.blue, 2) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.yellow, 2) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.purple, 2) + "|");
        System.out.println("|      .-------------.      ||      .-------------.      ||      .-------------.      ||      .-------------.      |");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.green, 4) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.blue, 4) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.yellow, 4) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.purple, 4) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.green, 5) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.blue, 5) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.yellow, 5) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.purple, 5) + "|");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.green, 6) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.blue, 6) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.yellow, 6) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.purple, 6) + "|");
        System.out.println("|      '.............'      ||      '.............'      ||      '.............'      ||      '.............'      |");
        System.out.println("|" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.green, 8) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.blue, 8) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.yellow, 8) + "||" + client.getGame().getDeckTopCardAsASCII(1, ColorCard.purple, 8) + "|");
        System.out.println("'---------------------------''---------------------------''---------------------------''---------------------------'");
    }

    public void printSlots(ReducedPersonalBoard playerBoard) {
        System.out.println("           SLOT 1                      SLOT 2                         SLOT 3           ");
        System.out.println(".---------------------------..---------------------------..---------------------------.");
        System.out.println("|" + playerBoard.getSlotTopCardAsASCII(0, 1) + "||" + playerBoard.getSlotTopCardAsASCII(1, 1) + "||" + playerBoard.getSlotTopCardAsASCII(2, 1) + "|");
        System.out.println("|" + playerBoard.getSlotTopCardAsASCII(0, 2) + "||" + playerBoard.getSlotTopCardAsASCII(1, 2) + "||" + playerBoard.getSlotTopCardAsASCII(2, 2) + "|");
        System.out.println("|      .-------------.      ||      .-------------.      ||      .-------------.      |");
        System.out.println("|" + playerBoard.getSlotTopCardAsASCII(0, 4) + "||" + playerBoard.getSlotTopCardAsASCII(1, 4) + "||" + playerBoard.getSlotTopCardAsASCII(2, 4) + "|");
        System.out.println("|" + playerBoard.getSlotTopCardAsASCII(0, 5) + "||" + playerBoard.getSlotTopCardAsASCII(1, 5) + "||" + playerBoard.getSlotTopCardAsASCII(2, 5) + "|");
        System.out.println("|" + playerBoard.getSlotTopCardAsASCII(0, 6) + "||" + playerBoard.getSlotTopCardAsASCII(1, 6) + "||" + playerBoard.getSlotTopCardAsASCII(2, 6) + "|");
        System.out.println("|      '.............'      ||      '.............'      ||      '.............'      |");
        System.out.println("|" + playerBoard.getSlotTopCardAsASCII(0, 8) + "||" + playerBoard.getSlotTopCardAsASCII(1, 8) + "||" + playerBoard.getSlotTopCardAsASCII(2, 8) + "|");
        System.out.println("'---------------------------''---------------------------''---------------------------'");
    }

    public void printFaithTrack(ReducedPersonalBoard playerBoard) {
        System.out.print("                              ! Vatican Section  ");
        System.out.print("" + playerBoard.getFaithTrack().getFavorTile(0));
        System.out.print("                 !  Vatican Section       ");
        System.out.print("" + playerBoard.getFaithTrack().getFavorTile(1));
        System.out.print("           !   Vatican Section            ");
        System.out.print("" + playerBoard.getFaithTrack().getFavorTile(2));
        System.out.println();
        System.out.println("   0     1     2     3     4     5     6     7     8     9     10    11    12    13    14    15    16    17    18    19    20    21    22    23   24");
        System.out.println(".-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.");
        System.out.print("|");
        for (int i = 0; i < 25; i++) {
            System.out.print(playerBoard.getFaithTrack().getFaithMarkerCell(i));
        }
        System.out.println();
        System.out.println("'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'-----'");
        System.out.println("                   PV=1              PV=2        POPE  PV=4              PV=6              PV=9  POPE        PV=12             PV=16             PV=20");
        System.out.println("                                                                                                                                                 POPE  ");
        if(client.getGame().isSoloMode())
            System.out.println(" Lorenzo's position : "+((ReducedSoloMode)client.getGame()).getBlackCross());
    }


    public void printPersonalBoard(ReducedPersonalBoard playerBoard) {
        printFaithTrack(playerBoard);
        printSlots(playerBoard);
        printWarehouse(playerBoard);
        showLeaderCards(playerBoard);
    }

    public void printWarehouse(ReducedPersonalBoard playerBoard) {
        System.out.println(playerBoard.getWarehouse());
    }


}