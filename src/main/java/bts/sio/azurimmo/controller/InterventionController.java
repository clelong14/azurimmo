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

import bts.sio.azurimmo.model.Intervention;
import bts.sio.azurimmo.model.dto.InterventionDTO;
import bts.sio.azurimmo.service.InterventionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/interventions") 
@Tag(name = "Interventions", description = "Gestion des interventions")
public class InterventionController {

	@Autowired
    private InterventionService interventionService;
	
	@PostMapping("/")
    @Operation(summary = "Création d'interventions")
	 public ResponseEntity<InterventionDTO> createIntervention(@RequestBody InterventionDTO dto) {
		InterventionDTO savedDTO = interventionService.saveInterventionDTO(dto);
	 return ResponseEntity.status(201).body(savedDTO); // 201 Created
	}
	
	 @GetMapping("/intervention/{interventionId}")
	    @Operation(summary = "Recherche des interventions d'un appartement grâce à son ID")
	    public List<Intervention> getInterventionsParAppartement(@PathVariable long interventionId) {
	            return interventionService.getInterventionsParAppartement(interventionId);
	    }
	 
	 @GetMapping("/{interventionId}")
	    @Operation(summary = "Récupère une intervention par son ID")
		public ResponseEntity<InterventionDTO> getInterventionReDTO(@PathVariable long interventionId) {
		 return interventionService.getInterventionDTO(interventionId)
				 .map(ResponseEntity::ok) // batiment trouvé → 200
				 .orElse(ResponseEntity.notFound().build()); // pas trouvé → 404
		}
	
	@GetMapping("/all")
	@Operation(summary = "Récupère toutes les interventions", description = "Retourne une liste de toutes les interventions sous forme de DTO.")
	 public List<InterventionDTO> getAllInterventions() {
	 return interventionService.getInterventionsDTO();
	}
}
