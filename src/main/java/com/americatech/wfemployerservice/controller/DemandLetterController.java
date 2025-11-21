package com.americatech.wfemployerservice.controller;

import com.americatech.wfemployerservice.domain.DemandLetterModel;
import com.americatech.wfemployerservice.mapper.DemandLetterRequestMapper;
import com.americatech.wfemployerservice.mapper.DemandLetterResponseMapper;
import com.americatech.wfemployerservice.request.DemandLetterRequest;
import com.americatech.wfemployerservice.response.DemandLetterResponse;
import com.americatech.wfemployerservice.service.DemandLetterCommandService;
import com.americatech.wfemployerservice.service.DemandLetterQueryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/demand-letters")
@RequiredArgsConstructor
public class DemandLetterController {

    private final DemandLetterQueryService queryService;
    private final DemandLetterCommandService commandService;
    private final DemandLetterRequestMapper requestMapper;
    private final DemandLetterResponseMapper responseMapper;


    @PostMapping
    public ResponseEntity<DemandLetterResponse> create(@Valid @RequestBody DemandLetterRequest letter) {
        DemandLetterModel model = requestMapper.requestModelToDomainModel(letter);
        model = commandService.create(model);
        DemandLetterResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandLetterResponse> getById(@PathVariable UUID id) {
        DemandLetterModel model = queryService.getById(id);
        DemandLetterResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public List<DemandLetterResponse> getAll() {
        return responseMapper.domainModelToResponseModel(queryService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemandLetterResponse> update(@PathVariable UUID id, @Valid @RequestBody DemandLetterRequest letter) {
        DemandLetterModel model = requestMapper.requestModelToDomainModel(letter);
        model = commandService.update(id, model);
        DemandLetterResponse response = responseMapper.domainModelToResponseModel(model);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        commandService.delete(id);
    }

}
