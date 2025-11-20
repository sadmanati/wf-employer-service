package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import com.americatech.wfemployerservice.service.EmployerQuotaCommandService;
import com.americatech.wfemployerservice.service.EmployerQuotaQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employer-quotas")
public class EmployerQuotaController {

    private final EmployerQuotaQueryService quotaQueryService;
    private final EmployerQuotaCommandService quotaCommandService;

    public EmployerQuotaController(EmployerQuotaQueryService quotaQueryService,
                                   EmployerQuotaCommandService quotaCommandService) {
        this.quotaQueryService = quotaQueryService;
        this.quotaCommandService = quotaCommandService;
    }

    @PostMapping
    public ResponseEntity<EmployerQuotaEntity> create(@Valid @RequestBody EmployerQuotaEntity quota) {
        EmployerQuotaEntity created = quotaCommandService.create(quota);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public EmployerQuotaEntity getById(@PathVariable UUID id) {
        return quotaQueryService.getById(id);
    }

    @GetMapping
    public List<EmployerQuotaEntity> getAll() {
        return quotaQueryService.getAll();
    }

    @PutMapping("/{id}")
    public EmployerQuotaEntity update(@PathVariable UUID id, @Valid @RequestBody EmployerQuotaEntity quota) {
        return quotaCommandService.update(id, quota);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        quotaCommandService.delete(id);
    }

    @ExceptionHandler({EntityNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleErrors(RuntimeException ex) {
        HttpStatus status = ex instanceof EntityNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}
