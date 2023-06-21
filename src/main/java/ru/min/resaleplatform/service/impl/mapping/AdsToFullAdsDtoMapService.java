package ru.min.resaleplatform.service.impl.mapping;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.dto.FullAdsDto;

@Component
public class AdsToFullAdsDtoMapService extends PropertyMap<Ads, FullAdsDto> {
    @Override
    protected void configure() {
        map().setPk(source.getId());
        map().setAuthorFirstName(source.getAdsAuthor().getFirstName());
        map().setAuthorLastName(source.getAdsAuthor().getLastName());
        map().setDescription(source.getDescription());
        map().setEmail(source.getAdsAuthor().getEmail());
        map().setImage(source.getImage());
        map().setPhone(source.getAdsAuthor().getPhone());
        map().setPrice(source.getPrice());
        map().setTitle(source.getTitle());
    }
}
