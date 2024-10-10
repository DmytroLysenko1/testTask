package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.constant.HttpStatuses;
import org.example.dto.BankAccountDTO;
import org.example.dto.DepositDTO;
import org.example.dto.TransferDTO;
import org.example.dto.WithdrawalDTO;
import org.example.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-accounts")
@Tag(name = "Bank Account", description = "Bank Account API")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    @Operation(summary = "Get all bank accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    })
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        return ResponseEntity.ok(this.bankAccountService.getAllBankAccounts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bank account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    })
    public ResponseEntity<BankAccountDTO> getBankAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(this.bankAccountService.getBankAccountById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    })
    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestBody BankAccountDTO bankAccountDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bankAccountService.createBankAccount(bankAccountDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    })
    public ResponseEntity<BankAccountDTO> updateBankAccount(@PathVariable Long id, @RequestBody BankAccountDTO bankAccountDTO) {
        return ResponseEntity.ok(this.bankAccountService.updateBankAccount(id, bankAccountDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "204", description = HttpStatuses.NO_CONTENT),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    })
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long id) {
        this.bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit")
    @Operation(summary = "Deposit funds into a bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    })
    public ResponseEntity<?> depositFunds(@Valid @RequestBody DepositDTO depositDTO) {
        this.bankAccountService.depositFunds(depositDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw funds from a bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND),
            @ApiResponse(responseCode = "403", description = HttpStatuses.FORBIDDEN)
    })
    public ResponseEntity<?> withdrawFunds(@Valid @RequestBody WithdrawalDTO withdrawalDTO) {
        this.bankAccountService.withdrawFunds(withdrawalDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer funds between bank accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND),
            @ApiResponse(responseCode = "403", description = HttpStatuses.FORBIDDEN)
    })
    public ResponseEntity<?> transferFunds(@Valid @RequestBody TransferDTO transferDTO) {
        this.bankAccountService.transferFunds(transferDTO);
        return ResponseEntity.ok().build();
    }
}
