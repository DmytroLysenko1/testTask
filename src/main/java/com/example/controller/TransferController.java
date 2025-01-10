package com.example.controller;

import com.example.dto.transfer.TransferRequestDTO;
import com.example.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    private ResponseEntity<Void> transferPlayer(@ModelAttribute TransferRequestDTO transferRequestDTO) {
        transferService.transfer(transferRequestDTO.getPlayerId(), transferRequestDTO.getTeamId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
