package bts.sio.azurimmo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.model.dto.ContratDTO;
import bts.sio.azurimmo.service.ContratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/contrats")
@Tag(name = "Contrats", description = "Gestion des contrats")

public class ContratController {
	
	private final ContratService contratService;

	public ContratController(ContratService contratService) {
		this.contratService = contratService;
	}
	
	@PostMapping
    @Operation(summary = "Création de contrats")
	 public ResponseEntity<ContratDTO> createContrat(@RequestBody ContratDTO dto) {
		ContratDTO savedDTO = contratService.saveContratDTO(dto);
	 return ResponseEntity.status(201).body(savedDTO); // 201 Created
	}

	@PutMapping("/{contratId}")
	@Operation(summary = "Modification d'un contrat")
	public ResponseEntity<ContratDTO> updateContrat(@PathVariable Long contratId, @RequestBody ContratDTO dto) {
		return contratService.updateContratDTO(contratId, dto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping({"/appartement/{appartementId}", "/contrat/{appartementId}"})
	@Operation(summary = "Recherche des contrats d'un appartement grâce à son ID")
	public List<Contrat> getContratsParAppartement(@PathVariable long appartementId) {
		return contratService.getContratsParAppartement(appartementId);
	}
	 
	 @GetMapping("/{contratId}")
	    @Operation(summary = "Récupère un contrat par son ID")
		public ResponseEntity<ContratDTO> getContratReDTO(@PathVariable long contratId) {
		 return contratService.getContratDTO(contratId)
				 .map(ResponseEntity::ok) // batiment trouvé → 200
				 .orElse(ResponseEntity.notFound().build()); // pas trouvé → 404
		}
	
	@GetMapping("/all")
	@Operation(summary = "Récupère tous les contrats", description = "Retourne une liste de toutes les contrats sous forme de DTO.")
	 public List<ContratDTO> getAllContrats() {
	 return contratService.getContratsDTO();
	}

}
