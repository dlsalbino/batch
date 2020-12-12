package br.com.azusah.batch.application.usecase;

import br.com.azusah.batch.domain.entity.Coffee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CoffeeItemProcessor implements ItemProcessor<Coffee, Coffee> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeItemProcessor.class);

    @Override
    public Coffee process(final Coffee coffee) throws Exception {
        Coffee transformedCoffee = Coffee.builder()
                .brand(coffee.getBrand().toUpperCase())
                .origin(coffee.getOrigin().toUpperCase())
                .characteristics(coffee.getCharacteristics().toUpperCase())
                .build();

        LOGGER.info("Converting ({}) into ({})", coffee, transformedCoffee);

        return transformedCoffee;
    }
}
