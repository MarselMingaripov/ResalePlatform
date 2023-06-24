package ru.min.resaleplatform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Создание объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AdsDto> createAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                                @RequestPart("image") MultipartFile multipartFile,
                                            @RequestPart("properties") @Valid AdsPropertiesDto adsPropertiesDto){
        return ResponseEntity.ok(adsService.createAds(adsPropertiesDto, multipartFile));
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getCurrentUserAds(){
        return ResponseEntity.ok(adsService.getMyAdsInStrangeForm());
    }

    @Operation(summary = "Получение всех объявлений",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @GetMapping()
    public ResponseEntity<ResponseWrapperAds> getAllAds(){
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Operation(summary = "Получение объявления по ид",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Объявление не найдено по ид"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAdsById(@PathVariable int id){
        return ResponseEntity.ok(adsService.findAdsById(id));
    }

    @Operation(summary = "Удаление объявления по ид",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
                    @ApiResponse(responseCode = "404", description = "Объявление не найдено по ид"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdsById(@PathVariable int id){
        adsService.deleteAdsById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление объявления по ид",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
                    @ApiResponse(responseCode = "404", description = "Объявление не найдено по ид"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id,
                                            @RequestBody AdsPropertiesDto adsPropertiesDto) throws AccessDeniedException {
        return ResponseEntity.ok(adsService.updateAds(id, adsPropertiesDto));
    }

    @Operation(summary = "Обновление изображения объявления по ид",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
                    @ApiResponse(responseCode = "404", description = "Объявление не найдено по ид"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @PatchMapping("/{id}/image")
    public ResponseEntity<String> updateAdsImage(@PathVariable int id,
                                                 @RequestParam(value = "image") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(adsService.updateImage(id, multipartFile));
    }
}
