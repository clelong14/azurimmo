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

import bts.sio.azurimmo.model.Locataire;
import bts.sio.azurimmo.model.dto.LocataireDTO;
import bts.sio.azurimmo.service.LocataireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/locataires") 
@Tag(name = "Locataires", description = "Gestion des locataires")
public class LocataireController {
	
	@Autowired
    private LocataireService locataireService;
	
	@PostMapping("/")
    @Operation(summary = "Création de Locataires")
	 public ResponseEntity<LocataireDTO> createLocataire(@RequestBody LocataireDTO dto) {
		LocataireDTO savedDTO = locataireService.saveLocataireDTO(dto);
	 return ResponseEntity.status(201).body(savedDTO); // 201 Created
	}
	
	 @GetMapping("/locataire/{LocataireId}")
	    @Operation(summary = "Recherche des Locataires d'un contrat grâce à son ID")
	    public List<Locataire> getLocatairesParContrat(@PathVariable long locataireId) {
	            return locataireService.getLocatairesParContrat(locataireId);
	    }
	 
	 @GetMapping("/{locataireId}")
	    @Operation(summary = "Récupère un Locataire par son ID")
		public ResponseEntity<LocataireDTO> getLocataireReDTO(@PathVariable long locataireId) {
		 return locataireService.getLocataireDTO(locataireId)
				 .map(ResponseEntity::ok) // batiment trouvé → 200
				 .orElse(ResponseEntity.notFound().build()); // pas trouvé → 404
		}
	
	@GetMapping("/all")
	@Operation(summary = "Récupère tous les locataires", description = "Retourne une liste de tous les locataires sous forme de DTO.")
	 public List<LocataireDTO> getAllLocataires() {
	 return locataireService.getLocatairesDTO();
	}

}
