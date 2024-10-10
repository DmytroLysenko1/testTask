package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.constant.HttpStatuses;
import org.example.dto.DetailAccountDTO;
import org.example.service.DetailAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/detail-account")
@Tag(name = "Detail Account Controller", description = "Controller for Detail Account")
public class DetailAccountController {
    private final DetailAccountService detailAccountService;

    @GetMapping
    @Operation(summary = "Get all detail accounts")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.OK),
    })
    public ResponseEntity<List<DetailAccountDTO>> getAllDetailAccounts() {
        List<DetailAccountDTO> detailAccounts = this.detailAccountService.getAllDetailAccounts();
        return ResponseEntity.ok(detailAccounts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get detail account by id")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.OK,
                    content = @Content(schema = @Schema(implementation = DetailAccountDTO.class))),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    })
    public ResponseEntity<DetailAccountDTO> getDetailAccountById(@PathVariable Long id) {
        DetailAccountDTO detailAccount = this.detailAccountService.getDetailAccountById(id);
        return ResponseEntity.ok(detailAccount);
    }

    @PostMapping
    @Operation(summary = "Create new detail account")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.CREATED,
                    content = @Content(schema = @Schema(implementation = DetailAccountDTO.class))),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)

    })
    public ResponseEntity<DetailAccountDTO> createDetailAccount(@RequestBody DetailAccountDTO detailAccountDTO) {
        DetailAccountDTO createdDetailAccount = this.detailAccountService.createDetailAccount(detailAccountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDetailAccount);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing detail account")
    @ApiResponses(value = {
            @ApiResponse(description = "200", responseCode = HttpStatuses.OK,
                    content = @Content(schema = @Schema(implementation = DetailAccountDTO.class))),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    })
    public ResponseEntity<DetailAccountDTO> updateDetailAccount(@PathVariable Long id, @RequestBody DetailAccountDTO detailAccountDTO) {
        DetailAccountDTO updatedDetailAccount = this.detailAccountService.updateDetailAccount(id, detailAccountDTO);
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
        this.detailAccountService.deleteDetailAccount(id);
        return ResponseEntity.noContent().build();
    }
}
