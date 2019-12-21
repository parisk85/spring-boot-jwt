package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.Role;
import gr.parisk85.springbootjwt.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> getByName(String name) {
        return roleRepository.findByName(name);
    }
}
