package it.polimi.ingsw.Client.view;


import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;
import it.polimi.ingsw.Utils.ResourceSource;
import org.apache.maven.model.Resource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI extends UI {


    private Scanner input = new Scanner(System.in);

    private Map<ResourceSource, EnumMap<ResourceType,Integer>> askInstructionsOnHowToTakeResources(Map<ResourceType,Integer> neededResources) {
        Map<ResourceSource, EnumMap<ResourceType,Integer>> howToTakeResources = initializeHowToTakeResources();
        System.out.print("You need following resources to perform your action: " +neededResources+
                "\nSelect how you want to pick resources from your warehouse\nChoose the source: " +
                "[Depot, Strongbox, Extra] ");
        ResourceSource source = fromStringToResourceSource(input.nextLine());
        System.out.println("Choose the resource [Coin (C), Servant (SE), Shield (SH), Stone(ST)] ");
        ResourceType resource = fromStringToResourceType(input.nextLine());
        System.out.println("Choose the number ");
        int number = input.nextInt();
        howToTakeResources.get(source).put(resource, number);
        neededResources.put(resource, neededResources.get(resource)-number);

        return howToTakeResources;
    }


    private Map<ResourceSource, EnumMap<ResourceType,Integer>> initializeHowToTakeResources(){
        Map<ResourceSource, EnumMap<ResourceType,Integer>> howToTakeResources = new HashMap<>();
        howToTakeResources.put(ResourceSource.DEPOT, new EnumMap<ResourceType, Integer>(ResourceType.class));
        howToTakeResources.put(ResourceSource.STRONGBOX, new EnumMap<ResourceType, Integer>(ResourceType.class));
        howToTakeResources.put(ResourceSource.EXTRA, new EnumMap<ResourceType, Integer>(ResourceType.class));
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



}
