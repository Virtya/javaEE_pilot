package ru.ds.education.currency.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ErrorDto {

    private String message;

    private LocalDate timestamp;
}
