package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductionRuleTest {

    ProductionRule productionRule = new ProductionRule();

    @Test
    void initialize(){
        Map<ResourceType, Integer> input = new EnumMap<ResourceType, Integer>(ResourceType.class);
        input.put(ResourceType.coin, 1);
        input.put(ResourceType.shield, 1);
        input.put(ResourceType.servant, 2);
        input.put(ResourceType.stone, 0);
        Map<ResourceType, Integer> output = new EnumMap<ResourceType, Integer>(ResourceType.class);
        output.put(ResourceType.coin, 1);
        output.put(ResourceType.shield, 2);
        output.put(ResourceType.servant, 1);
        output.put(ResourceType.stone, 0);
        int num = 2;
        ProductionRule productionRule1 = new ProductionRule(input, output, num);
        assertNull(productionRule.getInputResources());
        assertNull(productionRule.getOutputResources());
        assertEquals(0, productionRule.getOutputFaith());
        assertEquals(input, productionRule1.getInputResources());
        assertEquals(output, productionRule1.getOutputResources());
        assertEquals(num, productionRule1.getOutputFaith());
    }



    @Test
    void setInputResources() {
        Map<ResourceType, Integer> input = new EnumMap<ResourceType, Integer>(ResourceType.class);
        input.put(ResourceType.coin, 1);
        input.put(ResourceType.shield, 1);
        input.put(ResourceType.servant, 2);
        input.put(ResourceType.stone, 0);
        productionRule.setInputResources(input);
        assertEquals(input, productionRule.getInputResources());
    }

    @Test
    void setOutputResources() {
        Map<ResourceType, Integer> output = new EnumMap<ResourceType, Integer>(ResourceType.class);
        output.put(ResourceType.coin, 1);
        output.put(ResourceType.shield, 2);
        output.put(ResourceType.servant, 1);
        output.put(ResourceType.stone, 0);
        productionRule.setOutputResources(output);
        assertEquals(output, productionRule.getOutputResources());
    }

    @Test
    void setOutputFaith() {
        int num = 3;
        productionRule.setOutputFaith(num);
        assertEquals(num, productionRule.getOutputFaith());
    }
}