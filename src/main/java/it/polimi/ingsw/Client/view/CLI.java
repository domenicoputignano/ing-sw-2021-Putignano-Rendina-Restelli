package it.polimi.ingsw.Client.view;


import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;
import it.polimi.ingsw.Utils.ResourceSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI extends UI {


    private final Scanner input = new Scanner(System.in);

    public Map<ResourceSource, EnumMap<ResourceType,Integer>> askInstructionsOnHowToTakeResources(Map<ResourceType,Integer> neededResources) {
        Map<ResourceSource, EnumMap<ResourceType,Integer>> howToTakeResources = initializeHowToTakeResources();
        boolean availableExtraDepot = client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRADEPOT).size()>0;
        do {
            showPossibleSources(neededResources, availableExtraDepot);
            ResourceSource source;
            do {
                source = fromStringToResourceSource(input.next());
            } while (source == null);
            System.out.println("Choose the resource [Coin (C), Servant (SE), Shield (SH), Stone(ST)] ");
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
            System.out.println("Choose the number ");
            int number;
            boolean rightNumber = false;
            do {
                number = input.nextInt();
                if (neededResources.get(resource) < number) {
                    System.out.println("Error detected, select again number of occurrences you want to pick ");
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


    @Override
    public void showUpdate(ActivateProductionUpdate message) {

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
        for(LeaderCard card : client.getGame().getCurrPlayer().getAvailableLeaderCards()) {
            System.out.printf("Card n.%d "+card+"\n",
                    client.getGame().getCurrPlayer().getAvailableLeaderCards().indexOf(card)+1);
        }
    }





}
