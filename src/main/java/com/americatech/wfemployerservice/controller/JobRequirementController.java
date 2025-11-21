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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-requirements")
public class JobRequirementController {

    private final JobRequirementQueryService queryService;
    private final JobRequirementCommandService commandService;
    private final JobRequirementRequestMapper requestMapper;
    private final JobRequirementResponseMapper responseMapper;

    public JobRequirementController(JobRequirementQueryService queryService,
                                    JobRequirementCommandService commandService,
                                    JobRequirementRequestMapper requestMapper,
                                    JobRequirementResponseMapper responseMapper) {
        this.queryService = queryService;
        this.commandService = commandService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    public ResponseEntity<JobRequirementResponse> create(@Valid @RequestBody JobRequirementRequest requirement) {
        JobRequirementModel model = requestMapper.requestModelToDomainModel(requirement);
        JobRequirementModel created = commandService.create(model);
        JobRequirementResponse response = responseMapper.domainModelToResponseModel(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public JobRequirementResponse getById(@PathVariable UUID id) {
        JobRequirementModel model = queryService.getById(id);
        return responseMapper.domainModelToResponseModel(model);
    }

    @GetMapping
    public List<JobRequirementResponse> getAll() {
        return responseMapper.domainModelToResponseModel(queryService.getAll());
    }

    @PutMapping("/{id}")
    public JobRequirementResponse update(@PathVariable UUID id, @Valid @RequestBody JobRequirementRequest requirement) {
        JobRequirementModel model = requestMapper.requestModelToDomainModel(requirement);
        JobRequirementModel updated = commandService.update(id, model);
        return responseMapper.domainModelToResponseModel(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        commandService.delete(id);
    }

    @ExceptionHandler({EntityNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleErrors(RuntimeException ex) {
        HttpStatus status = ex instanceof EntityNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}
