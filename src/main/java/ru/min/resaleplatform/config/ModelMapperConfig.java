package ru.min.resaleplatform.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.min.resaleplatform.service.impl.mapping.*;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new AdsToAdsDtoMapService());
        mapper.addMappings(new AdsToAdsPropertiesDtoMapService());
        mapper.addMappings(new AdsToFullAdsDtoMapService());
        mapper.addMappings(new CommentToCommentDtoMapService());
        mapper.addMappings(new UserDtoToUserMapService());
        return mapper;
    }
}
