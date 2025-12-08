package bts.sio.azurimmo.service;

import java.util.Optional;

import bts.sio.azurimmo.model.dto.BatimentDTO;
import bts.sio.azurimmo.model.mapper.BatimentMapper;
import bts.sio.azurimmo.repository.BatimentRepository;

public class BatimentService {
	public Optional<BatimentDTO> getBatimentDTO(Long id) {
		return BatimentRepository.findById(id)
		 .map(BatimentMapper::toDTO);
		 }

}
