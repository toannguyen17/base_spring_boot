package com.red.seeders;

import com.red.model.Role;
import com.red.services.role.RoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private RoleService roleService;

    public DatabaseSeeder() {
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedRoleTable();
    }

    private void seedRoleTable() {

        List<Role> rs = new ArrayList<>();
        Iterable<Role> iterable = roleService.findAll();

        iterable.forEach(rs::add);

        if(rs.size() < 1) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");

            Role userRole = new Role();
            userRole.setName("ROLE_USER");

            roleService.save(adminRole);
            roleService.save(userRole);

            logger.info("Role table seeded");
        }else {
            logger.trace("Role Seeding Not Required");
        }
    }
}