package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.dto.AppartementDTO;
import bts.sio.azurimmo.service.AppartementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/appartements") 
@Tag(name = "Appartements", description = "Gestion des appartements")
public class AppartementController {
	
   @Autowired // Injection de la couche Service.
    private AppartementService appartementService;
    
    @PostMapping("/")
    @Operation(summary = "Création d'appartements")
	 public ResponseEntity<AppartementDTO> createAppartement(@RequestBody AppartementDTO dto) {
    AppartementDTO savedDTO = appartementService.saveAppartementDTO(dto);
	 return ResponseEntity.status(201).body(savedDTO); // 201 Created
	}
    
    @GetMapping("/ville/{ville}")
    @Operation(summary = "Recherche de ville")
    public List<Appartement> findByVille(@PathVariable String ville) {
        return appartementService.findByVille(ville);
    }
    
    @GetMapping("/batiment/{batimentId}")
    @Operation(summary = "Recherche des appartements d'un bâtiment grâce à son ID")
    public List<Appartement> getAppartementsParBatiment(@PathVariable long batimentId) {
            return appartementService.getAppartementsParBatiment(batimentId);
    }
    
    @GetMapping("/surface/{surface}")
    @Operation(summary = "Recherche des appartements avec une surface minimale")
    public List<Appartement> findBySurfaceGreaterThan(@PathVariable Integer surface) {
            return appartementService.findBySurfaceGreaterThan(surface);
    }
    
    @GetMapping("/{appartementId}")
    @Operation(summary = "Récupère un appartement par son ID")
	public ResponseEntity<AppartementDTO> getBatimentReDTO(@PathVariable long appartementId) {
	 return appartementService.getAppartementDTO(appartementId)
			 .map(ResponseEntity::ok) // batiment trouvé → 200
			 .orElse(ResponseEntity.notFound().build()); // pas trouvé → 404
	}
	
	@GetMapping("/all")
	@Operation(summary = "Récupère tous les appartements")
	 public List<AppartementDTO> getAllAppartements() {
	 return appartementService.getAppartementsDTO();
	}
}