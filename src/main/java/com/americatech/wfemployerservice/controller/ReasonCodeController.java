package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.entity.ReasonCodeEntity;
import com.americatech.wfemployerservice.service.ReasonCodeCommandService;
import com.americatech.wfemployerservice.service.ReasonCodeQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reason-codes")
@RequiredArgsConstructor
public class ReasonCodeController {

    private final ReasonCodeQueryService queryService;
    private final ReasonCodeCommandService commandService;


    @PostMapping
    public ResponseEntity<ReasonCodeEntity> create(@Valid @RequestBody ReasonCodeEntity rc) {
        ReasonCodeEntity created = commandService.create(rc);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ReasonCodeEntity getById(@PathVariable UUID id) {
        return queryService.getById(id);
    }

    @GetMapping
    public List<ReasonCodeEntity> getAll() {
        return queryService.getAll();
    }

    @PutMapping("/{id}")
    public ReasonCodeEntity update(@PathVariable UUID id, @Valid @RequestBody ReasonCodeEntity rc) {
        return commandService.update(id, rc);
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
