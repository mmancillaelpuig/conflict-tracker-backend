package com.example.conflict_tracker2.service;

import com.example.conflict_tracker2.DTO.ConflictPedidaDTO;
import com.example.conflict_tracker2.DTO.ConflictRespuestaDTO;
import com.example.conflict_tracker2.model.Conflict;
import com.example.conflict_tracker2.model.Country;
import com.example.conflict_tracker2.repo.ConflictRepository;
import com.example.conflict_tracker2.repo.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConflictService {

    private final ConflictRepository conflictRepository;
    private final CountryRepository countryRepository;

    public ConflictService(ConflictRepository conflictRepository, CountryRepository countryRepository) {
        this.conflictRepository = conflictRepository;
        this.countryRepository = countryRepository;
    }

    private ConflictRespuestaDTO toDTO(Conflict conflict) {
        ConflictRespuestaDTO dto = new ConflictRespuestaDTO();

        dto.setId(conflict.getId()); // 👈 ESTE ES EL CAMBIO CLAVE
        dto.setName(conflict.getName());
        dto.setStartDate(conflict.getStartDate());
        dto.setConflictStatus(conflict.getConflictStatus());
        dto.setDescription(conflict.getDescription());

        dto.setCountryIds(conflict.getCountries()
                .stream()
                .map(Country::getId)
                .collect(Collectors.toSet()));

        return dto;
    }

    public List<ConflictRespuestaDTO> findAll(){
        return conflictRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ConflictRespuestaDTO findById(Long id) {
        return conflictRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public ConflictRespuestaDTO addConflict(ConflictPedidaDTO dto){
        Conflict conflict = new Conflict();
        conflict.setName(dto.getName());
        conflict.setStartDate(dto.getStartDate());
        conflict.setConflictStatus(dto.getConflictStatus());
        conflict.setDescription(dto.getDescription());

        Set<Long> ids = dto.getCountryIds();

        Set<Country> countries = (ids != null && !ids.isEmpty())
                ? countryRepository.findAllById(ids).stream().collect(Collectors.toSet())
                : new java.util.HashSet<>();

        conflict.setCountries(countries);

        Conflict saved = conflictRepository.save(conflict);
        return toDTO(saved);
    }

    public ConflictRespuestaDTO updateConflict(Long id, ConflictPedidaDTO dto) {
        return conflictRepository.findById(id)
                .map(conflict -> {
                    conflict.setName(dto.getName());
                    conflict.setStartDate(dto.getStartDate());
                    conflict.setConflictStatus(dto.getConflictStatus());
                    conflict.setDescription(dto.getDescription());

                    Set<Long> ids = dto.getCountryIds();

                    Set<Country> countries = (ids != null && !ids.isEmpty())
                            ? countryRepository.findAllById(ids).stream().collect(Collectors.toSet())
                            : new java.util.HashSet<>();

                    conflict.setCountries(countries);

                    return toDTO(conflictRepository.save(conflict));
                })
                .orElse(null);
    }

    public void deleteConflict(Long id) {
        conflictRepository.deleteById(id);
    }

    public List<ConflictRespuestaDTO> findByStatus(Conflict.status status) {
        return conflictRepository.findByConflictStatus(status)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ConflictRespuestaDTO> findByCountryCode(String code) {
        return conflictRepository.findByCountriesCode(code)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }
}
