package ru.ds.education.currency.mapper;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;
import ru.ds.education.currency.dto.CursDataDto;
import ru.ds.education.currency.model.CursDataModel;

@Component
@RequiredArgsConstructor
public class MapperCurrency extends ConfigurableMapper {

    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

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
