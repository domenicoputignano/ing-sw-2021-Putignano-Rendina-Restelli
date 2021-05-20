package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Client.clientstates.cli.ActionChoiceCLI;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;
import it.polimi.ingsw.Utils.ResourceSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CLI extends UI {


    private final Scanner input = new Scanner(System.in);
    private final ExecutorService interactionManagerService = Executors.newCachedThreadPool();

    public CLI(Client client) {
        super(client);
    }


    public Map<ResourceSource, EnumMap<ResourceType,Integer>> askInstructionsOnHowToTakeResources(Map<ResourceType,Integer> neededResources) {
        Map<ResourceSource, EnumMap<ResourceType,Integer>> howToTakeResources = initializeHowToTakeResources();
        boolean availableExtraDepot = client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRADEPOT).size()>0;
        do {
            showPossibleSources(neededResources, availableExtraDepot);
            ResourceSource source;
            do {
                source = fromStringToResourceSource(input.next());
            } while (source == null);
            System.out.print("Choose the resource [Coin (C), Servant (SE), Shield (SH), Stone(ST)]:  ");
            ResourceType resource;
            do {
                resource = fromStringToResourceType(input.next());
                if(resource!=null) {
                    if (neededResources.get(resource) == 0) {
                        System.out.println("You don't need this type of resources, please select again ");
                        resource = null;
                    }
                }
            } while (resource == null);
            System.out.println("Choose the number: ");
            int number;
            boolean rightNumber = false;
            do {
                number = input.nextInt();
                if (neededResources.get(resource) < number) {
                    System.out.println("Error detected, select again number of occurrences you want to pick: ");
                    rightNumber = false;
                } else {
                    rightNumber = true;
                }
            } while (!rightNumber);
            if(howToTakeResources.get(source).containsKey(resource)) {
                howToTakeResources.get(source).merge(resource, number, Integer::sum);
            } else howToTakeResources.get(source).put(resource, number);
            neededResources.put(resource, neededResources.get(resource) - number);
        } while(neededResources.values().stream().allMatch(x -> x==0));
        return howToTakeResources;
    }

    private void showPossibleSources(Map<ResourceType, Integer> neededResources, boolean availableExtraDepot) {
        if(availableExtraDepot) {
            System.out.print("You need now, following resources to perform your action: " +neededResources+
                    "\nSelect how you want to pick resources from your warehouse\nChoose the source: " +
                    "[Depot, Strongbox, Extra] ");
        } else {
            System.out.print("You need now, following resources to perform your action: " +neededResources+
                    "\nSelect how you want to pick resources from your warehouse\nChoose the source: " +
                    "[Depot, Strongbox] ");
        }
    }


    private Map<ResourceSource, EnumMap<ResourceType,Integer>> initializeHowToTakeResources(){
        Map<ResourceSource, EnumMap<ResourceType,Integer>> howToTakeResources = new HashMap<>();
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
            if(destination!=null) {
                destinationOK = true;
            } else {
                System.out.println("Invalid choice, try again. [DEPOT1|DEPOT2|DEPOT3|EXTRA|DISCARD]");
            }
        } while(!destinationOK);
        return destination;
    }

    private MarbleDestination parseMarbleDestination(String choice) {
        switch (choice) {
            case "DEPOT1" : return MarbleDestination.DEPOT1;
            case "DEPOT2" : return MarbleDestination.DEPOT2;
            case "DEPOT3" : return MarbleDestination.DEPOT3;
            case "EXTRA" : return MarbleDestination.EXTRA;
            case "DISCARD" : return MarbleDestination.DISCARD;
            default : return null;
        }
    }

    @Override
    public void showUpdate(ServerMessage message) {
        clientState.render(message);
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
    public void render(GameSetupMessage message) { System.out.println("Game setup has been done, now you need to choose two leader cards to discard "); }
    public void render(InitialLeaderChoiceUpdate message) {
        if(isReceiverAction(message.getUser())) {
            System.out.println("You have successfully discarded two leader cards ");
        } else {
            System.out.println("User "+message.getUser()+" has discarded two leader cards");
        }
    }
    public void render(InitialResourceChoiceUpdate message) {
        if(isReceiverAction(message.getUser())) {
            System.out.println("You have chosen " + message.getChosenResources() + " resources ");
        } else System.out.println("User "+message.getUser()+" added "+message.getChosenResources()+" to his" +
                " depots ");
    }
    public void render(ServerAsksForPositioning message) {
        if(isReceiverAction(message.getUser())) {
            System.out.println("You have not correctly positioned the following resources: " + message.getResourcesToSettle());
        } else System.out.println("User " + message.getUser() + "has not correctly positioned some resources.");
    }
    public void render(NewTurnUpdate message) {
        if(isReceiverAction(message.getCurrentUser())) {
            System.out.println("It's now your turn, make the move ");
        } else {
            System.out.printf("It's %s's turn, please wait\n", message.getCurrentUser());
        }
    }
    public void render(TakeResourcesFromMarketUpdate message) {
        if(isReceiverAction(message.getUser())) {
            System.out.printf("You got following resources from market: "+message.getEarnedResources()+" and %d faith points",
                    message.getFaithPoints());
        } else {
            System.out.printf("User "+message.getUser()+"" +
                    " has taken following resources from market: "+message.getEarnedResources()+" and he got %d faith points \n", message.getFaithPoints());
        }
        System.out.println("Resulting market tray is shown below\n");
        showMarketTray();
    }
    public void render(FaithMarkerUpdate message) {
        if(message.getUser().equals(message.getTriggeringUser())&&isReceiverAction(message.getTriggeringUser())) {
            System.out.printf("Your action involved faith track, other players got %d faith points\n", message.getPoints());
        } else {
            if(isReceiverAction(message.getUser())) {
                System.out.printf("User "+message.getTriggeringUser()+" has performed action" +
                        "involving faith track, you got %d faith points\n", message.getPoints());
            }
            else {
                System.out.printf("User "+message.getTriggeringUser()+" has performed action" +
                        "involving faith track, %s got %d faith points\n", message.getUser(), message.getPoints());
            }
        }
    }

    @Override
    public void manageUserInteraction() {
        interactionManagerService.submit(() -> clientState.manageUserInteraction());
    }

    public ResourceType fromStringToResourceType(String resource) {
        if(resource.equalsIgnoreCase("C")) return ResourceType.coin;
        if(resource.equalsIgnoreCase("SE")) return ResourceType.servant;
        if(resource.equalsIgnoreCase("SH")) return ResourceType.shield;
        if(resource.equalsIgnoreCase("ST")) return ResourceType.stone;
        else {
            System.out.println("Error detected, please select again ");
            return null;
        }
    }

    public ResourceSource fromStringToResourceSource(String source) {
        if(source.equalsIgnoreCase("depot")) return ResourceSource.DEPOT;
        if(source.equalsIgnoreCase("strongbox")) return ResourceSource.STRONGBOX;
        if(source.equalsIgnoreCase("extra")) return ResourceSource.EXTRA;
        else {
            System.out.println("Error detected, please select again ");
            return null;
        }
    }



    public void showLeaderCards() {
        for(LeaderCard card : client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards()) {
            System.out.printf("Card n.%d "+card+"\n",
                    client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().indexOf(card)+1);
        }
    }

    public ResourceType askValidResource(Scanner input) {
        boolean done = false;
        ResourceType resource = null;
        while(!done) {
            String answer = input.nextLine();
            resource = fromStringToResourceType(answer);
            if(resource!=null){
                done = true;
            }
        }
        return resource;
    }

    public int askValidDepotIndex(Scanner input, int maxIndex) {
        boolean done = false;
        int index = 0;
        while(!done) {
            index = input.nextInt();
            if(index < 1 || index > maxIndex) {
                System.out.printf("Invalid chosen index, please select a number between [1 - %d]: ", maxIndex);
            } else done = true;
        }
        return index;
    }

    public int askValidOcc(Scanner input, int maxOcc) {
        boolean done = false;
        int occ = 0;
        while(!done) {
            occ = input.nextInt();
            if(occ < 1 || occ > maxOcc) {
                System.out.printf("Invalid number of occurrences chosen, please select a number between [1 - %d]: ", maxOcc);
            } else done = true;
        }
        return occ;
    }

    public void returnToActionChoice() {
        changeClientState(new ActionChoiceCLI(this.client));
        this.clientState.manageUserInteraction();
    }

    public void showMarketTray() {
        System.out.println(client.getGame().getMarketTray());
    }

    @Override
    public void changeClientState(AbstractClientState newClientState) {
        clientState = newClientState;
    }

}
