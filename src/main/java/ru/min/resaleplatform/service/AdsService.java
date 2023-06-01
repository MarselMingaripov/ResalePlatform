package ru.min.resaleplatform.service;

import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.dto.AdsDto;
import ru.min.resaleplatform.model.dto.AdsPropertiesDto;
import ru.min.resaleplatform.model.dto.ResponseWrapperComment;

import java.util.List;

public interface AdsService {
    AdsDto createAds(AdsPropertiesDto adsPropertiesDto, MultipartFile image);

    List<AdsDto> getCurrentUserAds();

    ResponseWrapperComment getMyAdsInStrangeForm();

    ResponseWrapperComment getAllAds();
}
