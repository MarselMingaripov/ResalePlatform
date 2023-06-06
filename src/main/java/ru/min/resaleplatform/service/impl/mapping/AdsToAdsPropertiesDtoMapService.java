package ru.min.resaleplatform.service.impl.mapping;

import org.modelmapper.PropertyMap;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.dto.AdsPropertiesDto;

public class AdsToAdsPropertiesDtoMapService extends PropertyMap<Ads, AdsPropertiesDto> {


    @Override
    protected void configure() {
        map().setDescription(source.getDescription());
        map().setPrice(source.getPrice());
        map().setTitle(source.getTitle());
    }
}
