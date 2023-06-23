package ru.min.resaleplatform.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.min.resaleplatform.service.impl.mapping.*;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final AdsToAdsDtoMapService adsToAdsDtoMapService;
    private final AdsToAdsPropertiesDtoMapService adsToAdsPropertiesDtoMapService;
    private final AdsToFullAdsDtoMapService adsToFullAdsDtoMapService;
    private final CommentToCommentDtoMapService commentToCommentDtoMapService;
    private final UserDtoToUserMapService userDtoToUserMapService;

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(adsToAdsDtoMapService);
        mapper.addMappings(adsToAdsPropertiesDtoMapService);
        mapper.addMappings(adsToFullAdsDtoMapService);
        mapper.addMappings(commentToCommentDtoMapService);
        mapper.addMappings(userDtoToUserMapService);
        return mapper;
    }
}
