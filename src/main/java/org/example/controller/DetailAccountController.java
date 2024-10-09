package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.HttpStatuses;
import org.example.dto.DetailAccountDTO;
import org.example.model.SwaggerExampleModel;
import org.example.service.DetailAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/detail-account")
@Tag(name = "Detail Account Controller", description = "Controller for Detail Account")
public class DetailAccountController {
    private final DetailAccountService detailAccountService;

    @GetMapping
    @Operation(summary = "Get all detail accounts")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.OK)
    })
    public ResponseEntity<List<DetailAccountDTO>> getAllDetailAccounts() {
        log.info("Get all detail accounts");
        List<DetailAccountDTO> detailAccounts = detailAccountService.getAllDetailAccounts();
        return ResponseEntity.ok(detailAccounts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get detail account by id")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.OK,
                    content = @Content(schema = @Schema(implementation = DetailAccountDTO.class),
                            examples = {
                                    @ExampleObject(name = "Detail Account Example",
                                            value = SwaggerExampleModel.DETAIL_ACCOUNT_MODEL)
                            }))
    })
    public ResponseEntity<DetailAccountDTO> getDetailAccountById(@PathVariable Long id) {
        log.info("Get detail account by id: {}", id);
        DetailAccountDTO detailAccount = detailAccountService.getDetailAccountById(id);
        return ResponseEntity.ok(detailAccount);
    }

    @PostMapping
    @Operation(summary = "Create new detail account")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.CREATED,
                    content = @Content(schema = @Schema(implementation = DetailAccountDTO.class),
                            examples = {
                                    @ExampleObject(name = "Detail Account Example",
                                            value = SwaggerExampleModel.DETAIL_ACCOUNT_MODEL)
                            }))
    })
    public ResponseEntity<DetailAccountDTO> createDetailAccount(@RequestBody DetailAccountDTO detailAccountDTO) {
        log.info("Create detail account: {}", detailAccountDTO);
        DetailAccountDTO createdDetailAccount = detailAccountService.createDetailAccount(detailAccountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDetailAccount);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing detail account")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.OK,
                    content = @Content(schema = @Schema(implementation = DetailAccountDTO.class),
                            examples = {
                                    @ExampleObject(name = "Detail Account Example",
                                            value = SwaggerExampleModel.DETAIL_ACCOUNT_MODEL)
                            }))
    })
    public ResponseEntity<DetailAccountDTO> updateDetailAccount(@PathVariable Long id, @RequestBody DetailAccountDTO detailAccountDTO) {
        log.info("Update detail account by id: {}", id);
        DetailAccountDTO updatedDetailAccount = detailAccountService.updateDetailAccount(id, detailAccountDTO);
        return ResponseEntity.ok(updatedDetailAccount);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a detail account")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.OK),
            @ApiResponse(responseCode = "403", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    })
    public ResponseEntity<Void> deleteDetailAccount(@PathVariable Long id) {
        log.info("Delete detail account by id: {}", id);
        detailAccountService.deleteDetailAccount(id);
        return ResponseEntity.noContent().build();
    }
}
