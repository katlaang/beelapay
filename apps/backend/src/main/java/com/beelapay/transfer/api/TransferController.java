package com.beelapay.transfer.api;

import com.beelapay.transfer.domain.InternalTransfer;
import com.beelapay.transfer.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/internal")
    public InternalTransfer internalTransfer(@Valid @RequestBody InternalTransferRequest request) {
        return transferService.internalTransfer(request);
    }
}
