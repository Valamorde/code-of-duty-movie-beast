package com.ticketmonster.ticketbeast.controllers;


import com.ticketmonster.ticketbeast.exceptions.ResourceNotFoundException;
import com.ticketmonster.ticketbeast.models.Role;
import com.ticketmonster.ticketbeast.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/role_api")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    // Get All Roles
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Get a Single Role
    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable(value = "id") Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "Id", id));
    }

    // Create a new Role
    @PostMapping("/roles")
    public Role createRole(@Valid @RequestBody Role role) {
        return roleRepository.save(role);
    }

    // Update a Role
    @PutMapping("/roles/{id}")
    public Role updateRole(@PathVariable(value = "id") Long id, @Valid @RequestBody Role roleDetails) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "Id", id));
        role.setTitle(roleDetails.getTitle());

        Role updatedRole = roleRepository.save(role);
        return updatedRole;
    }

    // Delete a Role
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "Id", id));

        roleRepository.delete(role);

        return ResponseEntity.ok().build();
    }
}
