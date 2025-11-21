package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.domain.JobOrderModel;
import com.americatech.wfemployerservice.mapper.JobOrderRequestMapper;
import com.americatech.wfemployerservice.mapper.JobOrderResponseMapper;
import com.americatech.wfemployerservice.request.JobOrderRequest;
import com.americatech.wfemployerservice.response.JobOrderResponse;
import com.americatech.wfemployerservice.service.JobOrderCommandService;
import com.americatech.wfemployerservice.service.JobOrderQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/job-orders")
@RequiredArgsConstructor
public class JobOrderController {

    private final JobOrderQueryService queryService;
    private final JobOrderCommandService commandService;
    private final JobOrderRequestMapper requestMapper;
    private final JobOrderResponseMapper responseMapper;


    @PostMapping
    public ResponseEntity<JobOrderResponse> create(@Valid @RequestBody JobOrderRequest request) {
        JobOrderModel model = requestMapper.requestModelToDomainModel(request);
        model = commandService.create(model);
        JobOrderResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOrderResponse> getById(@PathVariable UUID id) {
        JobOrderModel model = queryService.getById(id);
        JobOrderResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<JobOrderResponse>> getAll() {
        List<JobOrderModel> domains = queryService.getAll();
        List<JobOrderResponse> responses = domains.stream()
                .map(responseMapper::domainModelToResponseModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobOrderResponse> update(@PathVariable UUID id,
                                                   @Valid @RequestBody JobOrderRequest request) {
        JobOrderModel model = requestMapper.requestModelToDomainModel(request);
        model = commandService.update(id, model);
        JobOrderResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
