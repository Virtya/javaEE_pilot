package ru.ds.education.currency.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseMessageDto {
    String currencyName;
    String currencyDate;
    String currencyRate;
}
