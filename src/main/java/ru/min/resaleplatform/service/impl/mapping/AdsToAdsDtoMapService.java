package ru.min.resaleplatform.service.impl.mapping;

import org.modelmapper.PropertyMap;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.dto.AdsDto;

public class AdsToAdsDtoMapService extends PropertyMap<Ads, AdsDto> {
    @Override
    protected void configure() {
        map().setAuthor(Integer.valueOf(source.getAdsAuthor().getId()));
        map().setImage(source.getImage());
        map().setPk(source.getId());
        map().setPrice(source.getPrice());
        map().setTitle(source.getTitle());
    }
}
