package ru.min.resaleplatform.service;

import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.dto.AdsDto;
import ru.min.resaleplatform.model.dto.AdsPropertiesDto;
import ru.min.resaleplatform.model.dto.FullAdsDto;
import ru.min.resaleplatform.model.dto.ResponseWrapperAds;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface AdsService {
    AdsDto createAds(AdsPropertiesDto adsPropertiesDto, MultipartFile image);

    List<AdsDto> getCurrentUserAds();

    ResponseWrapperAds getMyAdsInStrangeForm();

    ResponseWrapperAds getAllAds();

    FullAdsDto findAdsById(int id);

    void deleteAdsById(int id);

    AdsDto updateAds(int id, AdsPropertiesDto adsPropertiesDto) throws AccessDeniedException;

    String updateImage(int id, MultipartFile image) throws IOException;
}
