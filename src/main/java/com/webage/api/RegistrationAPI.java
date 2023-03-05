package com.webage.api;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.webage.domain.Registration;
import com.webage.repository.RegistrationRepository;

@RestController
@RequestMapping("/registrations")
public class RegistrationAPI {

	@Autowired
	RegistrationRepository repo;

	@GetMapping
	public Iterable<Registration> getAll() {
		return repo.findAll();
	}

	@GetMapping("/{registrationId}")
	public Optional<Registration> getRegistrationById(@PathVariable("registrationId") long id) {
		// return repo.findOne(id);
		return repo.findById(id);
	}

	@PostMapping
	public ResponseEntity<?> addRegistration(@RequestBody Registration newRegistration, UriComponentsBuilder uri) {
		if (newRegistration.getId() != 0 || newRegistration.getEvent_id() == null || newRegistration.getCustomer_id() == null || newRegistration.getRegistration_date() == null) {
			// Reject we'll assign the event id
			return ResponseEntity.badRequest().build();
		}
		newRegistration = repo.save(newRegistration);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newRegistration.getId()).toUri();
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		return response;
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<?> putRegistration(
			@RequestBody Registration newRegistration,
			@PathVariable("eventId") long eventId) 
	{
		if (newRegistration.getEvent_id() == null || newRegistration.getCustomer_id() == null ) { //|| newRegistration.getRegistration_date() == null) {
			return ResponseEntity.badRequest().build();
		}
		newRegistration = repo.save(newRegistration);
		return ResponseEntity.ok().build();
	}	
	
	@DeleteMapping("/{eventId}")
	public ResponseEntity<?> deleteRegistrationById(@PathVariable("eventId") long id) {
		// repo.delete(id);
		repo.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}	
	
}
