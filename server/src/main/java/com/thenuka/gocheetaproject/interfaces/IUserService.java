package com.thenuka.gocheetaproject.interfaces;

import com.thenuka.gocheetaproject.dto.UserRoleDTO;

public interface IUserService {

    UserRoleDTO getUser(int userId);
}
