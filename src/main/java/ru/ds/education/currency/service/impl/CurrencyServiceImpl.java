package ru.ds.education.currency.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ds.education.currency.dto.CursDataDto;
import ru.ds.education.currency.exception.ResourceAlreadyExistException;
import ru.ds.education.currency.exception.ResourceNotFoundException;
import ru.ds.education.currency.mapper.MapperCurrency;
import ru.ds.education.currency.model.CursDataModel;
import ru.ds.education.currency.repository.CurrencyRepository;
import ru.ds.education.currency.service.CurrencyService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final MapperCurrency mapper;

    @Override
    public List<CursDataDto> getAllCurrencies() {
        List<CursDataDto> cursDataDtos = new LinkedList<>();
        List<CursDataModel> cursDataModels = currencyRepository.findAll();

        for (CursDataModel cursDataModel : cursDataModels) {
            cursDataDtos.add(mapper.map(cursDataModel, CursDataDto.class));
        }

        return cursDataDtos;
    }

    @Override
    public CursDataDto getCurrency(Long id) {
        CursDataModel cursDataModel = currencyRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Валюты с id = " + id + " не существует"
                            )
                    );

        return mapper.map(cursDataModel, CursDataDto.class);
    }

    @Override
    public CursDataDto getCurrencyByNameAndDate(String name, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate actualDate = LocalDate.parse(date, formatter);

        CursDataModel cursDataModel = currencyRepository.findByCurrencyNameAndCursDate(name, actualDate);

        if (cursDataModel == null) {
            throw new ResourceNotFoundException("Валюты с именем " + name + " не существует");
        }

        return mapper.map(cursDataModel, CursDataDto.class);
    }

    @Override
    public CursDataDto addCurrency(CursDataDto newCur) {

        if (currencyRepository.existsByCurrencyName(newCur.getCurrencyName())
                || currencyRepository.existsByCurrencyCode(newCur.getCurrencyCode())) {
            throw new ResourceAlreadyExistException("Данная валюта уже добавлена");
        }

        CursDataModel cursDataModel = new CursDataModel();

        cursDataModel.setCurrencyName(newCur.getCurrencyName());
        cursDataModel.setCurrencyCode(newCur.getCurrencyCode());
        cursDataModel.setCurs(newCur.getCurs());
        cursDataModel.setCursDate(newCur.getCursDate());

        currencyRepository.save(cursDataModel);

        return mapper.map(cursDataModel, CursDataDto.class);
    }

    @Override
    public CursDataDto updateCurrency(Long id, CursDataDto newCur) {
        CursDataModel cursData = currencyRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Валюты с id = " + id + " не существует"
                        )
        );

        mapper.map(newCur, cursData);

        currencyRepository.save(cursData);

        return mapper.map(cursData, CursDataDto.class);
    }

    @Override
    public void deleteCurrency(Long id) {
        if (currencyRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Валюты с id = " + id + " не существует");
        }

        currencyRepository.deleteById(id);
    }

}
