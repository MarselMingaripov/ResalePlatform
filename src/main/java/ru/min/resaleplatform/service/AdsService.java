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
    /**
     * создать объявление
     * @param adsPropertiesDto - dto объявления
     * @param image - файл изображения
     * @return - dto объявления
     */
    AdsDto createAds(AdsPropertiesDto adsPropertiesDto, MultipartFile image);

    /**
     * получить объявления текущего пользователя
     * @return
     */
    List<AdsDto> getCurrentUserAds();

    /**
     * получить все объявления в виде dto для фронта
     * @return
     */
    ResponseWrapperAds getMyAdsInStrangeForm();

    /**
     * получить все объявления
     * @return
     */
    ResponseWrapperAds getAllAds();

    /**
     * поиск объявления по ид
     * @param id
     * @return
     */
    FullAdsDto findAdsById(int id);

    /**
     * удаление объявления по ид
     * @param id
     */
    void deleteAdsById(int id);

    /**
     * обновление объявления по ид
     * @param id
     * @param adsPropertiesDto
     * @return
     * @throws AccessDeniedException
     */
    AdsDto updateAds(int id, AdsPropertiesDto adsPropertiesDto) throws AccessDeniedException;

    /**
     * обновление изображения объявления по ид
     * @param id
     * @param image
     * @return
     * @throws IOException
     */
    String updateImage(int id, MultipartFile image) throws IOException;
}
