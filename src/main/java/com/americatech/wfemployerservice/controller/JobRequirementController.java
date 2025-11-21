package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.domain.JobRequirementModel;
import com.americatech.wfemployerservice.mapper.JobRequirementRequestMapper;
import com.americatech.wfemployerservice.mapper.JobRequirementResponseMapper;
import com.americatech.wfemployerservice.request.JobRequirementRequest;
import com.americatech.wfemployerservice.response.JobRequirementResponse;
import com.americatech.wfemployerservice.service.JobRequirementCommandService;
import com.americatech.wfemployerservice.service.JobRequirementQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/job-requirements")
@RequiredArgsConstructor
public class JobRequirementController {

    private final JobRequirementQueryService queryService;
    private final JobRequirementCommandService commandService;
    private final JobRequirementRequestMapper requestMapper;
    private final JobRequirementResponseMapper responseMapper;


    @PostMapping
    public ResponseEntity<JobRequirementResponse> create(@Valid @RequestBody JobRequirementRequest requirement) {
        JobRequirementModel model = requestMapper.requestModelToDomainModel(requirement);
        model = commandService.create(model);
        JobRequirementResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobRequirementResponse> getById(@PathVariable UUID id) {
        JobRequirementModel model = queryService.getById(id);
        JobRequirementResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<JobRequirementResponse>> getAll() {
        List<JobRequirementModel> models = queryService.getAll();
        List<JobRequirementResponse> responses = models.stream()
                .map(responseMapper::domainModelToResponseModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobRequirementResponse> update(@PathVariable UUID id,
                                                         @Valid @RequestBody JobRequirementRequest requirement) {
        JobRequirementModel model = requestMapper.requestModelToDomainModel(requirement);
        JobRequirementModel updated = commandService.update(id, model);
        JobRequirementResponse response = responseMapper.domainModelToResponseModel(updated);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        commandService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
