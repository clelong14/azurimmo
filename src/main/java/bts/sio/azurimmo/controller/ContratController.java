package bts.sio.azurimmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bts.sio.azurimmo.model.Contrat;
import bts.sio.azurimmo.model.dto.ContratDTO;
import bts.sio.azurimmo.service.ContratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/contrats") 
@Tag(name = "Contrats", description = "Gestion des contrats")

public class ContratController {
	
	@Autowired
    private ContratService contratService;
	
	@PostMapping("/")
    @Operation(summary = "Création de contrats")
	 public ResponseEntity<ContratDTO> createContrat(@RequestBody ContratDTO dto) {
		ContratDTO savedDTO = contratService.saveContratDTO(dto);
	 return ResponseEntity.status(201).body(savedDTO); // 201 Created
	}
	
	 @GetMapping("/contrat/{contratId}")
	    @Operation(summary = "Recherche des contrats d'un appartement grâce à son ID")
	    public List<Contrat> getContratsParAppartement(@PathVariable long contratId) {
	            return contratService.getContratsParAppartement(contratId);
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
