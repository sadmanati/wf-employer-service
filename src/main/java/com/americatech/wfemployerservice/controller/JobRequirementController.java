package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.entity.JobRequirementEntity;
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

    public JobRequirementController(JobRequirementQueryService queryService,
                                    JobRequirementCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<JobRequirementEntity> create(@Valid @RequestBody JobRequirementEntity requirement) {
        JobRequirementEntity created = commandService.create(requirement);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public JobRequirementEntity getById(@PathVariable UUID id) {
        return queryService.getById(id);
    }

    @GetMapping
    public List<JobRequirementEntity> getAll() {
        return queryService.getAll();
    }

    @PutMapping("/{id}")
    public JobRequirementEntity update(@PathVariable UUID id, @Valid @RequestBody JobRequirementEntity requirement) {
        return commandService.update(id, requirement);
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
