package ru.min.resaleplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapperAds {

    private int count;
    private List<AdsDto> results;
}
