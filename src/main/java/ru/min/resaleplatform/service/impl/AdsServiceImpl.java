package ru.min.resaleplatform.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.AdsDto;
import ru.min.resaleplatform.model.dto.AdsPropertiesDto;
import ru.min.resaleplatform.model.dto.ResponseWrapperComment;
import ru.min.resaleplatform.repository.AdsRepository;
import ru.min.resaleplatform.service.AdsService;
import ru.min.resaleplatform.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    @Override
    public AdsDto createAds(AdsPropertiesDto adsPropertiesDto, MultipartFile image){
        Ads ads = new Ads(adsPropertiesDto.getDescription(), adsPropertiesDto.getPrice(), adsPropertiesDto.getTitle());

        try{
            if (!image.isEmpty()){
                String fileName = UUID.randomUUID() + "_" +
                        image.getOriginalFilename();
                image.transferTo(new File("C:\\Users\\User\\Documents\\IdeaProjects\\ResalePlatform\\src\\main\\resources\\static\\" + fileName));
                ads.setImage("\\static\\" + fileName);
                //adsRepository.save(ads);
                logger.info(image.getContentType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = userService.getCurrentUser();
        ads.setAdsAuthor(user);
        adsRepository.save(ads);
        AdsDto adsDto = new AdsDto(user.getId(), ads.getImage(), ads.getId(), ads.getPrice(), ads.getTitle());
        return adsDto;
    }

    @Override
    public List<AdsDto> getCurrentUserAds(){
        User user = userService.getCurrentUser();
        List<Ads> myAds = adsRepository.findByAdsAuthor_Email(user.getEmail());
        List<AdsDto> adsDtos = new ArrayList<>();
        for (Ads myAd : myAds) {
            adsDtos.add(new AdsDto(myAd.getAdsAuthor().getId(), myAd.getImage(), myAd.getId(), myAd.getPrice(), myAd.getTitle()));
        }
        return adsDtos;
    }

    @Override
    public ResponseWrapperComment getMyAdsInStrangeForm(){
        List<AdsDto> adsDtos = getCurrentUserAds();
        return new ResponseWrapperComment(adsDtos.size(), adsDtos);
    }

    @Override
    public ResponseWrapperComment getAllAds(){
        List<AdsDto> adsDtos = new ArrayList<>();
        List<Ads> ads = adsRepository.findAll();
        for (Ads ad : ads) {
            adsDtos.add(new AdsDto(ad.getAdsAuthor().getId(), ad.getImage(), ad.getId(), ad.getPrice(), ad.getTitle()));
        }
        return new ResponseWrapperComment(adsDtos.size(), adsDtos);
    }
}
