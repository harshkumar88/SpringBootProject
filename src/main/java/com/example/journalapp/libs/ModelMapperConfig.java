package com.example.journalapp.libs;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.convention.MatchingStrategies;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(skipNullCondition());
        return modelMapper;
    }

    private Condition<Object, Object> skipNullCondition() {
        return new Condition<Object, Object>() {
            @Override
            public boolean applies(MappingContext<Object, Object> context) {
                Object source = context.getSource();
                return source != null && !source.equals(0);
            }
        };
    }
}