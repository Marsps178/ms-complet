package cibertec.pe.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cibertec.pe.dto.CuentaRequest;
import cibertec.pe.dto.CuentaResponse;
import cibertec.pe.entity.Cuenta;
import cibertec.pe.exception.ResourceNotFoundException;
import cibertec.pe.mapper.CuentaMapper;
import cibertec.pe.repository.CuentaRepository;

@Service
@Transactional
public class CuentaService {

    private static final Logger log = LoggerFactory.getLogger(CuentaService.class);

    private final CuentaRepository repository;
    private final CuentaMapper mapper;

    public CuentaService(CuentaRepository repository, CuentaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<CuentaResponse> findAll() {
        log.info("Fetching all cuentas");
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CuentaResponse findById(Long id) {
        log.info("Fetching cuenta by id: {}", id);
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Cuenta not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CuentaResponse> findByCodEmployee(Long codEmployee) {
        log.info("Fetching cuentas by employee code: {}", codEmployee);
        return repository.findByCodEmployee(codEmployee)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CuentaResponse create(CuentaRequest request) {
        log.info("Creating cuenta with nroCta: {}", request.getNroCta());
        Cuenta cuenta = mapper.toEntity(request);
        Cuenta saved = repository.save(cuenta);
        log.info("Cuenta created with id: {}", saved.getId());
        return mapper.toResponse(saved);
    }

    public CuentaResponse update(Long id, CuentaRequest request) {
        log.info("Updating cuenta with id: {}", id);
        Cuenta existing = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Cuenta not found with id: " + id));
        mapper.updateEntity(request, existing);
        Cuenta saved = repository.save(existing);
        log.info("Cuenta updated with id: {}", saved.getId());
        return mapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        log.info("Deleting cuenta with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cuenta not found with id: " + id);
        }
        repository.deleteById(id);
        log.info("Cuenta deleted with id: {}", id);
    }
}
