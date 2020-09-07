package com.red.services.role;

import com.red.model.Role;
import com.red.services.IGeneralService;

public interface RoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
