package com.example.conflict_tracker2.controller;

import com.example.conflict_tracker2.DTO.ConflictPedidaDTO;
import com.example.conflict_tracker2.DTO.ConflictRespuestaDTO;
import com.example.conflict_tracker2.model.Conflict;
import com.example.conflict_tracker2.service.ConflictService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class ConflictController {

    private final ConflictService conflictService;

    public ConflictController(ConflictService conflictService) {
        this.conflictService = conflictService;
    }

    @GetMapping("/conflicts")
    public List<ConflictRespuestaDTO> findAll(@RequestParam(required = false) Conflict.status status) {
        if (status != null) {
            return conflictService.findByStatus(status);
        }
        return conflictService.findAll();
    }

    @GetMapping("/conflicts/{id}")
    public ConflictRespuestaDTO findConflict(@PathVariable Long id) {
        return conflictService.findById(id);
    }

    @PostMapping("/conflicts")
    public ConflictRespuestaDTO addConflict(@RequestBody ConflictPedidaDTO dto) {
        return conflictService.addConflict(dto);
    }

    @PutMapping("/conflicts/{id}")
    public ConflictRespuestaDTO updateConflict(@PathVariable Long id,
                                               @RequestBody ConflictPedidaDTO dto) {
        return conflictService.updateConflict(id, dto);
    }

    @DeleteMapping("/conflicts/{id}")
    public void deleteConflict(@PathVariable Long id) {
        conflictService.deleteConflict(id);
    }

    @GetMapping("/countries/{code}/conflicts")
    public List<ConflictRespuestaDTO> findByCountry(@PathVariable String code) {
        return conflictService.findByCountryCode(code);
    }
}
