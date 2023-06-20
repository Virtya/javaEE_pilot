package ru.ds.education.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.commons.nullanalysis.NotNull;
import org.codehaus.commons.nullanalysis.Nullable;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CursDataDto {

    private String currencyName;

    private Integer currencyCode;

    private Double curs;

    private LocalDate cursDate;
}
