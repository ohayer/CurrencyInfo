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
import pl.currencyinfo.curinf.service.PersonSearchInfoService;
import pl.currencyinfo.curinf.entity.PersonSearchInfo;

import java.util.List;

@RestController
@RequestMapping("/currencies/")
public class PersonSearchInfoController {

    @Autowired
    PersonSearchInfoService service;

    @Operation(summary = "Get all currency requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all currency requests retrieved successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonSearchInfo.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content)
    })
    @GetMapping("requests")
    public List<PersonSearchInfo> getAll() {
        return service.getAll();
    }
}
