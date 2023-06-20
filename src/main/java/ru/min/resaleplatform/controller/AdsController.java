package ru.min.resaleplatform.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.min.resaleplatform.model.dto.AdsDto;
import ru.min.resaleplatform.model.dto.AdsPropertiesDto;
import ru.min.resaleplatform.model.dto.FullAdsDto;
import ru.min.resaleplatform.model.dto.ResponseWrapperAds;
import ru.min.resaleplatform.service.AdsService;
import ru.min.resaleplatform.service.ImageService;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private final AdsService adsService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AdsDto> createAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                                @RequestPart("image") MultipartFile multipartFile,
                                            @RequestPart("properties") @Valid AdsPropertiesDto adsPropertiesDto){
        return ResponseEntity.ok(adsService.createAds(adsPropertiesDto, multipartFile));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getCurrentUserAds(){
        return ResponseEntity.ok(adsService.getMyAdsInStrangeForm());
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapperAds> getAllAds(){
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAdsById(@PathVariable int id){
        return ResponseEntity.ok(adsService.findAdsById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdsById(@PathVariable int id){
        adsService.deleteAdsById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id,
                                            @RequestBody AdsPropertiesDto adsPropertiesDto) throws AccessDeniedException {
        return ResponseEntity.ok(adsService.updateAds(id, adsPropertiesDto));
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<String> updateAdsImage(@PathVariable int id,
                                                 @RequestParam(value = "image") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(adsService.updateImage(id, multipartFile));
    }
}
