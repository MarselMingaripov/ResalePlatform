package ru.min.resaleplatform.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.min.resaleplatform.model.Ads;
import ru.min.resaleplatform.model.User;
import ru.min.resaleplatform.model.dto.AdsDto;
import ru.min.resaleplatform.model.dto.AdsPropertiesDto;
import ru.min.resaleplatform.model.dto.FullAdsDto;
import ru.min.resaleplatform.model.dto.ResponseWrapperAds;
import ru.min.resaleplatform.repository.AdsRepository;
import ru.min.resaleplatform.service.AdsService;
import ru.min.resaleplatform.service.ImageService;
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
    private final ImageService imageService;
    private final ModelMapper mapper;
    private final UserService userService;

    @Value("${image.upload.path}")
    private String imageUploadPath;

    private final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    /*@PostConstruct
    private void initMapper(){
        mapper.addMappings(new AdsToAdsPropertiesDtoMapService());
        mapper.addMappings(new AdsToAdsDtoMapService());
        mapper.addMappings(new AdsToFullAdsDtoMapService());
        mapper.addMappings(new CommentToCommentDtoMapService());
        mapper.addMappings(new UserDtoToUserMapService());
    }*/

    @Override
    public AdsDto createAds(AdsPropertiesDto adsPropertiesDto, MultipartFile image){
        Ads ads = mapper.map(adsPropertiesDto, Ads.class);

        try{
            if (!image.isEmpty()){
                adsImageEdit(image, ads);
                logger.info(image.getContentType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = userService.getCurrentUser();
        ads.setAdsAuthor(user);
        adsRepository.save(ads);
        AdsDto adsDto = mapper.map(ads, AdsDto.class);
        return adsDto;
    }



    @Override
    public List<AdsDto> getCurrentUserAds(){
        User user = userService.getCurrentUser();
        List<Ads> myAds = adsRepository.findByAdsAuthor_Email(user.getEmail());
        List<AdsDto> adsDtos = new ArrayList<>();
        for (Ads myAd : myAds) {
            adsDtos.add(mapper.map(myAd, AdsDto.class));
        }
        return adsDtos;
    }

    @Override
    public ResponseWrapperAds getMyAdsInStrangeForm(){
        List<AdsDto> adsDtos = getCurrentUserAds();
        return new ResponseWrapperAds(adsDtos.size(), adsDtos);
    }

    @Override
    public ResponseWrapperAds getAllAds(){
        List<AdsDto> adsDtos = new ArrayList<>();
        List<Ads> ads = adsRepository.findAll();
        for (Ads ad : ads) {
            adsDtos.add(mapper.map(ad, AdsDto.class));
        }
        return new ResponseWrapperAds(adsDtos.size(), adsDtos);
    }

    @Override
    public FullAdsDto findAdsById(int id){
        Ads ads = adsRepository.findById(id).orElseThrow();
        return mapper.map(ads, FullAdsDto.class);
    }

    @Override
    public void deleteAdsById(int id){
        if (adsRepository.existsById(id)) {
            adsRepository.deleteById(id);
        } else {
            throw new NotFoundException("Ads not found");
        }
    }

    @Override
    public AdsDto updateAds(int id, AdsPropertiesDto adsPropertiesDto){
        User user = userService.getCurrentUser();
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Ads not found"));
        if (user.getRole().getAuthority().equals("ADMIN") || ads.getAdsAuthor().getEmail().equals(user.getEmail())){
            ads.setTitle(adsPropertiesDto.getTitle());
            ads.setPrice(adsPropertiesDto.getPrice());
            ads.setDescription(adsPropertiesDto.getDescription());
            adsRepository.save(ads);
        }
        return new AdsDto(user.getId(), ads.getImage(), ads.getId(), ads.getPrice(), ads.getTitle());
    }

    @Override
    public String updateImage(int id, MultipartFile image) throws IOException {
        User user = userService.getCurrentUser();
        String pathToImage = "";
        if (adsRepository.existsById(id) && (user.getRole().getAuthority().equals("ADMIN") ||
                adsRepository.findById(id).get().getAdsAuthor().getEmail().equals(user.getEmail()))){
            Ads ads = adsRepository.findById(id).get();

            try{
                if (!image.isEmpty()){
                    adsImageEdit(image, ads);
                    adsRepository.save(ads);
                    pathToImage = ads.getImage();
                    logger.info(image.getContentType());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pathToImage;
    }

    private void adsImageEdit(MultipartFile image, Ads ads) throws IOException {
        imageService.createDir(imageUploadPath, logger);
        String fileName = UUID.randomUUID() + "_" +
                image.getOriginalFilename();
        image.transferTo(new File(imageUploadPath + fileName));
        ads.setImage("\\static\\" + fileName);
    }
}
