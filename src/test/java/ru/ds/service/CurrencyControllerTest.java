package ru.ds.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import ru.ds.education.currency.model.CursDataModel;
import ru.ds.education.currency.repository.CurrencyRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurrencyControllerTest extends ServiceApplicationTest{

    private final CurrencyRepository currencyRepository;
    private final LocalDate currentDate = LocalDate.parse("22-06-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    private Long currency1Id;
    private Long currency2Id;
    private Long currency3Id;

    public CurrencyControllerTest(CurrencyRepository currencyRepository,
                                  Long currency1Id, Long currency2Id, Long currency3Id) {
        this.currencyRepository = currencyRepository;
        this.currency1Id = currency1Id;
        this.currency2Id = currency2Id;
        this.currency3Id = currency3Id;
    }

    @BeforeAll
    @Transactional
    public void initDb() {
        CursDataModel cursDataModel = new CursDataModel();
        cursDataModel.setCurrencyName("MNT");
        cursDataModel.setCurrencyCode(496);
        cursDataModel.setCurs(0.02);
        cursDataModel.setCursDate(currentDate);
        currency1Id = currencyRepository.save(cursDataModel).getId();

        cursDataModel = new CursDataModel();
        cursDataModel.setCurrencyName("KRW");
        cursDataModel.setCurrencyCode(410);
        cursDataModel.setCurs(0.06);
        cursDataModel.setCursDate(currentDate);
        currency2Id = currencyRepository.save(cursDataModel).getId();

        cursDataModel = new CursDataModel();
        cursDataModel.setCurrencyName("KZT");
        cursDataModel.setCurrencyCode(398);
        cursDataModel.setCurs(0.18);
        cursDataModel.setCursDate(currentDate);
        currency3Id = currencyRepository.save(cursDataModel).getId();
    }

    @Test
    @SneakyThrows
    public void getCurrencyTest() {
        String responseJson = String.format(
                readFileFromResource("responses/getCurrencyTestResponse.json"),
                currency1Id
        );

        mockMvc.perform(
                        get(URI.create("/cur/${currency1Id}"))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson, false));
    }

    @Test
    @SneakyThrows
    public void createCurrencyTest() {
        String responseJson = String.format(
                readFileFromResource("requests/createCurrencyTestRequest.json"),
                currency2Id
        );

        mockMvc.perform(
              post(URI.create("/cur"))
              .content(readFileFromResource("requests/createCurrencyTestRequest.json"))
              .characterEncoding("utf-8")
              .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isCreated());

        Optional<CursDataModel> cursDataModel = currencyRepository.findById(currency2Id);
        assertTrue(cursDataModel.isPresent());

        JsonNode expectedJson = objectMapper.readTree(responseJson);
        JsonNode actualJson = objectMapper.readTree(String.valueOf(cursDataModel.get()));
        assertEquals(expectedJson, actualJson);

    }
}
