package ru.ds.education.currency.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ds.education.currency.dto.CursDataDto;
import ru.ds.education.currency.service.CurrencyService;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cur")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/{id}")
    public ResponseEntity<CursDataDto> getCurrencyById(@PathVariable Long id){
        return new ResponseEntity<>(currencyService.getCurrency(id), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CursDataDto> getCurrencyByNameAndDate(@PathVariable String name,
                                                                @PathVariable LocalDate date) {
        return new ResponseEntity<>(currencyService.getCurrencyByNameAndDate(name, date), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity <List<CursDataDto>> getCurrencies(){
        return new ResponseEntity<>(currencyService.getAllCurrencies(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addCurrency(@RequestBody CursDataDto currency){
        currencyService.addCurrency(currency);
     }

    @PutMapping ("/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void updateCurrency(@PathVariable Long id, @RequestBody CursDataDto currency){
        currencyService.updateCurrency(id, currency);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCurrency(@PathVariable Long id){
        currencyService.deleteCurrency(id);
    }
}
