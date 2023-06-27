package ru.ds.education.currency.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Schema(description = "Сообщение, содержащее название валюты и необходимое время")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RequestMessageDto {
    private String currencyName;

    private String currencyDate;
}
