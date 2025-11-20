package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.entity.DemandLetterEntity;
import com.americatech.wfemployerservice.service.DemandLetterCommandService;
import com.americatech.wfemployerservice.service.DemandLetterQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/demand-letters")
public class DemandLetterController {

    private final DemandLetterQueryService queryService;
    private final DemandLetterCommandService commandService;

    public DemandLetterController(DemandLetterQueryService queryService,
                                  DemandLetterCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<DemandLetterEntity> create(@Valid @RequestBody DemandLetterEntity letter) {
        DemandLetterEntity created = commandService.create(letter);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public DemandLetterEntity getById(@PathVariable UUID id) {
        return queryService.getById(id);
    }

    @GetMapping
    public List<DemandLetterEntity> getAll() {
        return queryService.getAll();
    }

    @PutMapping("/{id}")
    public DemandLetterEntity update(@PathVariable UUID id, @Valid @RequestBody DemandLetterEntity letter) {
        return commandService.update(id, letter);
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
