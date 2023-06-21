package ru.ds.education.currency.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final MapperCurrency mapper;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository, MapperCurrency mapper) {
        this.currencyRepository = currencyRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CursDataDto> getAllCurrencies() {
        List<CursDataDto> cursDataDtos = new LinkedList<>();
        List<CursDataModel> cursDataModels = currencyRepository.findAll();

        for (CursDataModel cursDataModel : cursDataModels) {
            cursDataDtos.add(mapper.mapCurModelIntoDto(cursDataModel));
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

        return mapper.mapCurModelIntoDto(cursDataModel);
    }

    @Override
    public CursDataDto getCurrencyByNameAndDate(String name, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate actualDate = LocalDate.parse(date, formatter);

        CursDataModel cursDataModel = currencyRepository.findByCurrencyNameAndCursDate(name, actualDate);

        if (cursDataModel == null) {
            throw new ResourceNotFoundException("Валюты с именем " + name + " не существует");
        }

        return mapper.mapCurModelIntoDto(cursDataModel);
    }

    @Override
    public void addCurrency(CursDataDto newCur) {

        if (currencyRepository.existsByCurrencyName(newCur.getCurrencyName())
                || currencyRepository.existsByCurrencyCode(newCur.getCurrencyCode())) {
            throw new ResourceAlreadyExistException("Данная валюта уже добавлена");
        }

        CursDataModel cursDataModel =
                CursDataModel.builder()
                            .currencyName(newCur.getCurrencyName())
                            .currencyCode(newCur.getCurrencyCode())
                            .curs(newCur.getCurs())
                            .cursDate(newCur.getCursDate())
                            .build();

        currencyRepository.save(cursDataModel);
    }

    @Override
    public void updateCurrency(Long id, CursDataDto newCur) {
        CursDataModel cursData = currencyRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Валюты с id = " + id + " не существует"
                        )
        );

        String tempName = newCur.getCurrencyName();
        Integer tempCode = newCur.getCurrencyCode();
        Double tempCurs = newCur.getCurs();
        LocalDate tempDate = newCur.getCursDate();

        if (tempName != null) {
            cursData.setCurrencyName(tempName);
        }

        if (tempCode != null) {
            cursData.setCurrencyCode(tempCode);
        }

        if (tempCurs != null) {
            cursData.setCurs(tempCurs);
        }

        if (tempDate != null) {
            cursData.setCursDate(tempDate);
        }

        currencyRepository.save(cursData);
    }

    @Override
    public void deleteCurrency(Long id) {
        currencyRepository.deleteById(id);
    }

}
