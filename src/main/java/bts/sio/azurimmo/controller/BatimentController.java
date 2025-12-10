package bts.sio.azurimmo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bts.sio.azurimmo.model.Batiment;
import bts.sio.azurimmo.model.dto.BatimentDTO;
import bts.sio.azurimmo.service.BatimentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@RequestMapping("/api/batiments") 
@Tag(name = "Batiments", description = "Gestion des batiments")
public class BatimentController {
	
	private final BatimentService batimentService;
	
	public BatimentController(BatimentService batimentService) {
		this.batimentService = batimentService;
	}
	
	@PostMapping("/")
	@Operation(summary = "Création de bâtiments")
	 public ResponseEntity<BatimentDTO> createBatiment(@RequestBody BatimentDTO dto) {
	 BatimentDTO savedDTO = batimentService.saveBatimentDTO(dto);
	 return ResponseEntity.status(201).body(savedDTO); // 201 Created
	}
	
	@GetMapping("/ville/{ville}")
    @Operation(summary = "Recherche de ville")
    public List<Batiment> findByVille(@PathVariable String ville) {
        return batimentService.findByVille(ville);
    }
	
	/*@GetMapping("/{batimentId}")
	 public Optional <BatimentDTO> getBatimentDTO(@PathVariable long batimentId) {
	 return batimentService.getBatimentDTO(batimentId);
	 }*/
	
	@GetMapping("/{batimentId}")
	@Operation(summary = "Récupère un bâtiment par son ID")
	public ResponseEntity<BatimentDTO> getBatimentReDTO(@PathVariable long batimentId) {
	 return batimentService.getBatimentDTO(batimentId)
			 .map(ResponseEntity::ok) // batiment trouvé → 200
			 .orElse(ResponseEntity.notFound().build()); // pas trouvé → 404
	}
	
	@GetMapping("/all")
	@Operation(summary = "Récupère tous les bâtiments")
	 public List<BatimentDTO> getAllBatiments() {
	 return batimentService.getBatimentsDTO();
	}
}
