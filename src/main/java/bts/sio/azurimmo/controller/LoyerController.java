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

import bts.sio.azurimmo.model.Loyer;
import bts.sio.azurimmo.model.dto.LoyerDTO;
import bts.sio.azurimmo.service.LoyerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/loyers") 
@Tag(name = "Loyers", description = "Gestion des loyers")
public class LoyerController {
	
	@Autowired
    private LoyerService loyerService;
	
	@PostMapping("/")
    @Operation(summary = "Création de loyers")
	 public ResponseEntity<LoyerDTO> createLoyer(@RequestBody LoyerDTO dto) {
		LoyerDTO savedDTO = loyerService.saveLoyerDTO(dto);
	 return ResponseEntity.status(201).body(savedDTO); // 201 Created
	}
	
	 @GetMapping("/loyer/{loyerId}")
	    @Operation(summary = "Recherche des loyers d'un contrat grâce à son ID")
	    public List<Loyer> getLoyersParContrat(@PathVariable long loyerId) {
	            return loyerService.getLoyersParContrat(loyerId);
	    }
	 
	 @GetMapping("/{loyerId}")
	    @Operation(summary = "Récupère un loyer par son ID")
		public ResponseEntity<LoyerDTO> getLoyerReDTO(@PathVariable long loyerId) {
		 return loyerService.getLoyerDTO(loyerId)
				 .map(ResponseEntity::ok) // batiment trouvé → 200
				 .orElse(ResponseEntity.notFound().build()); // pas trouvé → 404
		}
	
	@GetMapping("/all")
	@Operation(summary = "Récupère tous les loyers", description = "Retourne une liste de toutes les Loyers sous forme de DTO.")
	 public List<LoyerDTO> getAllLoyers() {
	 return loyerService.getLoyersDTO();
	}

}
