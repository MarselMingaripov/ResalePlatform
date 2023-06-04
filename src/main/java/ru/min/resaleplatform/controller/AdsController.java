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
import ru.min.resaleplatform.model.dto.ResponseWrapperComment;
import ru.min.resaleplatform.service.AdsService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private final AdsService adsService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AdsDto> createAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                                @RequestPart("image") MultipartFile multipartFile,
                                            @RequestPart("properties") @Valid AdsPropertiesDto adsPropertiesDto){
        return ResponseEntity.ok(adsService.createAds(adsPropertiesDto, multipartFile));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperComment> getCurrentUserAds(){
        return ResponseEntity.ok(adsService.getMyAdsInStrangeForm());
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapperComment> getAllAds(){
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAdsById(@PathVariable int id){
        return ResponseEntity.ok(adsService.findAdsById(id));
    }
}
