package ru.ds.education.currency.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Курс валют")
@Data
@AllArgsConstructor
public class CursDataDto {

    @Size(min = 3, max = 3, message = "Некорректное сокращение валюты")
    @Schema(description = "Сокращённое название валюты", example = "USD")
    private String currencyName;

    @Min(value = 1, message = "Значение должно быть больше 0")
    @Max(value = 999, message = "Значение должно быть меньше 1000")
    @Schema(description = "Цифровой код валюты", example = "020")
    private Integer currencyCode;

    @Min(value = 0, message = "Значение должно быть больше 0")
    @Schema(description = "Текущий курс валюты относительно рубля", example = "78.23")
    private Double curs;

    @Schema(description = "Дата получения информации о курсе валюты", example = "2023-06-21")
    private LocalDate cursDate;
}
