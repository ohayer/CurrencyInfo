package pl.currencyinfo.curinf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.currencyinfo.curinf.exception.ErrorResponse;
import pl.currencyinfo.curinf.model.Rate;
import pl.currencyinfo.curinf.nbpData.NbpCurrencyService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrenciesController {
    @Autowired
    private NbpCurrencyService nbpCurrencyService;
    @Operation(summary = "Returning all currencies from NBP API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currencies retrieved successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BigDecimal.class))}),
            @ApiResponse(responseCode = "400", description = "Error while retrieving currencies.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No currencies found.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content)
    })
    @GetMapping("/list")
    public List<Rate> getAllCurrencies() {
        return nbpCurrencyService.getAllRates();
    }
}
