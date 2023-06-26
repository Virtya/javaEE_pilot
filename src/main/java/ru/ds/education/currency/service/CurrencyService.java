package ru.ds.education.currency.service;

import ru.ds.education.currency.dto.CursDataDto;

import java.util.List;

public interface CurrencyService {
    List<CursDataDto> getAllCurrencies();

    CursDataDto getCurrency(Long id);

    CursDataDto getCurrencyByNameAndDate(String name, String date);

    CursDataDto addCurrency(CursDataDto newCur);

    CursDataDto updateCurrency(Long id, CursDataDto newCur);

    void deleteCurrency(Long id);

}
