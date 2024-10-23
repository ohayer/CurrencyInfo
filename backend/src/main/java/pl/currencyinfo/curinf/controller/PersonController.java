package pl.currencyinfo.curinf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.currencyinfo.curinf.exception.ErrorResponse;
import pl.currencyinfo.curinf.model.CurrencyBodyRequest;
import pl.currencyinfo.curinf.model.FortuneModel;
import pl.currencyinfo.curinf.service.PersonService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currencies/")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Operation(summary = "Calculate person's fortune in a specified currency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fortune calculated successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BigDecimal.class))}),
            @ApiResponse(responseCode = "404", description = "Person or currency not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content)
    })
    @PostMapping("get-current-currency-value-command")
    public ResponseEntity<FortuneModel> getCurrentCurrencyValueCommand(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing currency code and person's name",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CurrencyBodyRequest.class),
                            examples = @ExampleObject(
                                    name = "Example Body",
                                    value = "{\"currency\": \"USD\", \"name\": \"Jan Kowalski\"}"
                            )
                    )
            ) @RequestBody CurrencyBodyRequest body) {
        BigDecimal value = personService.calculatePersonWealthInCurrency(body);
        FortuneModel response = new FortuneModel(value);
        return ResponseEntity.ok(response);
    }
}
