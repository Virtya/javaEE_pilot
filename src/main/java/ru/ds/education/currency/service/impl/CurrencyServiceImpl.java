package ru.ds.education.currency.service.impl;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.currency.dto.CursDataDto;
import ru.ds.education.currency.exception.ResourceAlreadyExistException;
import ru.ds.education.currency.exception.ResourceNotFoundException;
import ru.ds.education.currency.model.CursDataModel;
import ru.ds.education.currency.repository.CurrencyRepository;
import ru.ds.education.currency.service.CurrencyService;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<CursDataDto> getAllCurrencies() {
        List<CursDataDto> cursDataDtos = new LinkedList<>();
        List<CursDataModel> cursDataModels = currencyRepository.findAll();

        for (CursDataModel cursDataModel : cursDataModels) {
            cursDataDtos.add(mapCurModelIntoDto(cursDataModel));
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

        return mapCurModelIntoDto(cursDataModel);
    }

    @Override
    public CursDataDto getCurrencyByNameAndDate(String name, LocalDate date) {
        CursDataModel cursDataModel = currencyRepository.findByCurrencyNameAndCursDate(name, date);

        return mapCurModelIntoDto(cursDataModel);
    }

    @Override
    public void addCurrency(CursDataDto newCur) {

        if (currencyRepository.findByCurrencyName(newCur.getCurrencyName()) != null) {
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
        Optional<CursDataModel> cursData = Optional.ofNullable(
                currencyRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Валюты с id = " + id + " не сушествует"
                        )
                )
        );

        if (cursData.isPresent()) {
            CursDataModel cursDataModel = cursData.get();

            cursDataModel.setCurrencyCode(newCur.getCurrencyCode());
            cursDataModel.setCurrencyCode(newCur.getCurrencyCode());
            cursDataModel.setCurs(newCur.getCurs());
            cursDataModel.setCursDate(newCur.getCursDate());

            currencyRepository.save(cursDataModel);
        }
    }

    @Override
    public void deleteCurrency(Long id) {
        currencyRepository.deleteById(id);
    }

    @Override
    public CursDataDto mapCurModelIntoDto(CursDataModel curModel) {
        mapperFactory.classMap(CursDataModel.class, CursDataDto.class)
                .field("currencyName", "currencyName")
                .field("currencyCode", "currencyCode")
                .field("curs", "curs")
                .field("cursDate", "cursDate")
                .register();

        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.map(curModel, CursDataDto.class);
    }

    @Override
    public CursDataModel mapCurDtoIntoModel(CursDataDto curDto) {
        mapperFactory.classMap(CursDataDto.class, CursDataModel.class)
                .field("currencyName", "currencyName")
                .field("currencyCode", "currencyCode")
                .field("curs", "curs")
                .field("cursDate", "cursDate")
                .register();

        MapperFacade mapper = mapperFactory.getMapperFacade();

        return mapper.map(curDto, CursDataModel.class);
    }
}
