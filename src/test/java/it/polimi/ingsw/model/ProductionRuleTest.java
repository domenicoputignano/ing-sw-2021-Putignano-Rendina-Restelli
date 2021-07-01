package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test a production rule and its changes.
 */
class ProductionRuleTest {

    ProductionRule productionRule = new ProductionRule();

    /**
     * Checks correct initialization of production rule itself.
     */
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


    /**
     * Covers input resources setter.
     */
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

    /**
     * Covers output resources setter.
     */
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

    /**
     * Setter of faith points.
     */
    @Test
    void setOutputFaith() {
        int num = 3;
        productionRule.setOutputFaith(num);
        assertEquals(num, productionRule.getOutputFaith());
    }
}