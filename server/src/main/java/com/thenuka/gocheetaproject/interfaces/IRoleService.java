package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.modals.Role;

public interface IRoleService {
    Role findByName(String name);
}
