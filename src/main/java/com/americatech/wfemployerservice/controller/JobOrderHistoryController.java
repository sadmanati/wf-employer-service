package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.domain.JobOrderHistoryModel;
import com.americatech.wfemployerservice.mapper.JobOrderHistoryRequestMapper;
import com.americatech.wfemployerservice.mapper.JobOrderHistoryResponseMapper;
import com.americatech.wfemployerservice.request.JobOrderHistoryRequest;
import com.americatech.wfemployerservice.response.JobOrderHistoryResponse;
import com.americatech.wfemployerservice.service.JobOrderHistoryCommandService;
import com.americatech.wfemployerservice.service.JobOrderHistoryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/job-order-history")
@RequiredArgsConstructor
public class JobOrderHistoryController {

    private final JobOrderHistoryQueryService queryService;
    private final JobOrderHistoryCommandService commandService;
    private final JobOrderHistoryRequestMapper requestMapper;
    private final JobOrderHistoryResponseMapper responseMapper;


    @PostMapping
    public ResponseEntity<JobOrderHistoryResponse> create(@Valid @RequestBody JobOrderHistoryRequest request) {
        JobOrderHistoryModel model = requestMapper.requestModelToDomainModel(request);
        model = commandService.create(model);
        JobOrderHistoryResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOrderHistoryResponse> getById(@PathVariable UUID id) {
        JobOrderHistoryModel model = queryService.getById(id);
        JobOrderHistoryResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<JobOrderHistoryResponse>> getAll() {
        List<JobOrderHistoryModel> domains = queryService.getAll();
        List<JobOrderHistoryResponse> responses = domains.stream()
                .map(responseMapper::domainModelToResponseModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobOrderHistoryResponse> update(@PathVariable UUID id,
                                                          @Valid @RequestBody JobOrderHistoryRequest request) {
        JobOrderHistoryModel model = requestMapper.requestModelToDomainModel(request);
         model = commandService.update(id, model);
        JobOrderHistoryResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
