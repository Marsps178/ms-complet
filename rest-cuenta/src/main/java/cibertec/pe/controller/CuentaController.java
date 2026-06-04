package cibertec.pe.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cibertec.pe.dto.CuentaRequest;
import cibertec.pe.dto.CuentaResponse;
import cibertec.pe.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cuentas")
@Tag(name = "Cuentas", description = "Endpoints for managing bank accounts")
public class CuentaController {

    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @Operation(summary = "List all accounts",
               description = "Returns all registered bank accounts")
    @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    @GetMapping
    public ResponseEntity<List<CuentaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Get account by ID",
               description = "Returns a single account by its unique identifier")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account found"),
        @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> findById(
            @Parameter(description = "Account ID") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "List accounts by employee",
               description = "Returns all accounts for a given employee code")
    @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    @GetMapping("/por-empleado/{codEmployee}")
    public ResponseEntity<List<CuentaResponse>> findByCodEmployee(
            @Parameter(description = "Employee code") @PathVariable Long codEmployee) {
        return ResponseEntity.ok(service.findByCodEmployee(codEmployee));
    }

    @Operation(summary = "Create a new account",
               description = "Creates a new bank account with the provided data")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CuentaResponse> create(
            @Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(service.create(request));
    }

    @Operation(summary = "Update an existing account",
               description = "Updates an existing account identified by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account updated successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponse> update(
            @Parameter(description = "Account ID") @PathVariable Long id,
            @Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Delete an account",
               description = "Deletes an account identified by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Account ID") @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
