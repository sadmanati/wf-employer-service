package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.domain.EmployerQuotaModel;
import com.americatech.wfemployerservice.entity.EmployerQuotaEntity;
import com.americatech.wfemployerservice.mapper.EmployerQuotaRequestMapper;
import com.americatech.wfemployerservice.mapper.EmployerQuotaResponseMapper;
import com.americatech.wfemployerservice.request.EmployerQuotaRequest;
import com.americatech.wfemployerservice.response.EmployerQuotaResponse;
import com.americatech.wfemployerservice.service.EmployerQuotaCommandService;
import com.americatech.wfemployerservice.service.EmployerQuotaQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employer-quotas")
@RequiredArgsConstructor
public class EmployerQuotaController {

    private final EmployerQuotaQueryService quotaQueryService;
    private final EmployerQuotaCommandService quotaCommandService;
    private final EmployerQuotaRequestMapper requestMapper;
    private final EmployerQuotaResponseMapper responseMapper;


    @PostMapping
    public ResponseEntity<EmployerQuotaResponse> create(@Valid @RequestBody EmployerQuotaRequest quota) {
        EmployerQuotaModel model = requestMapper.requestModelToDomainModel(quota);
        model = quotaCommandService.create(model);
        EmployerQuotaResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerQuotaResponse> getById(@PathVariable UUID id) {
        EmployerQuotaModel model= quotaQueryService.getById(id);
        EmployerQuotaResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @GetMapping
//    public List<EmployerQuotaResponse> getAll() {
//        return quotaQueryService.getAll();
//    }

    @PutMapping("/{id}")
    public EmployerQuotaResponse update(@PathVariable UUID id, @Valid @RequestBody EmployerQuotaRequest quota) {
        EmployerQuotaModel model = requestMapper.requestModelToDomainModel(quota);
        model = quotaCommandService.update(id, model);
        return responseMapper.domainModelToResponseModel(model);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        quotaCommandService.delete(id);
    }

}
