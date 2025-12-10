package bts.sio.azurimmo.controller;

import bts.sio.azurimmo.model.Appartement;
import bts.sio.azurimmo.model.dto.AppartementDTO;
import bts.sio.azurimmo.service.AppartementService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController // Gère les requêtes HTTP et retourne du JSON.
@RequestMapping("/api/appartements") // Chemin de base de l'API.
public class AppartementController {
	
   @Autowired // Injection de la couche Service.
    private AppartementService appartementService;
    
    @PostMapping("/")
	 public ResponseEntity<AppartementDTO> createAppartement(@RequestBody AppartementDTO dto) {
    AppartementDTO savedDTO = appartementService.saveAppartementDTO(dto);
	 return ResponseEntity.status(201).body(savedDTO); // 201 Created
	}
    
    @GetMapping("/ville/{ville}") // Endpoint de recherche par ville.
    public List<Appartement> findByVille(@PathVariable String ville) {
    	// @PathVariable : Récupère la valeur depuis l'URL.
        return appartementService.findByVille(ville);
    }
    
    @GetMapping("/batiment/{batimentId}") // Endpoint de recherche par ID de bâtiment.
    public List<Appartement> getAppartementsParBatiment(@PathVariable long batimentId) {
            return appartementService.getAppartementsParBatiment(batimentId);
    }
    
    @GetMapping("/surface/{surface}") // Endpoint de recherche par surface minimum.
    public List<Appartement> findBySurfaceGreaterThan(@PathVariable Integer surface) {
            return appartementService.findBySurfaceGreaterThan(surface);
    }
    
    @GetMapping("/{appartementId}")
	public ResponseEntity<AppartementDTO> getBatimentReDTO(@PathVariable long appartementId) {
	 return appartementService.getAppartementDTO(appartementId)
			 .map(ResponseEntity::ok) // batiment trouvé → 200
			 .orElse(ResponseEntity.notFound().build()); // pas trouvé → 404
	}
	
	@GetMapping("/all")
	 public List<AppartementDTO> getAllAppartements() {
	 return appartementService.getAppartementsDTO();
	}
}