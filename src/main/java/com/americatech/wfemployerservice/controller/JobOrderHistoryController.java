package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.entity.JobOrderHistoryEntity;
import com.americatech.wfemployerservice.service.JobOrderHistoryCommandService;
import com.americatech.wfemployerservice.service.JobOrderHistoryQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-order-history")
public class JobOrderHistoryController {

    private final JobOrderHistoryQueryService queryService;
    private final JobOrderHistoryCommandService commandService;

    public JobOrderHistoryController(JobOrderHistoryQueryService queryService,
                                     JobOrderHistoryCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<JobOrderHistoryEntity> create(@Valid @RequestBody JobOrderHistoryEntity history) {
        JobOrderHistoryEntity created = commandService.create(history);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public JobOrderHistoryEntity getById(@PathVariable UUID id) {
        return queryService.getById(id);
    }

    @GetMapping
    public List<JobOrderHistoryEntity> getAll() {
        return queryService.getAll();
    }

    @PutMapping("/{id}")
    public JobOrderHistoryEntity update(@PathVariable UUID id, @Valid @RequestBody JobOrderHistoryEntity history) {
        return commandService.update(id, history);
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
