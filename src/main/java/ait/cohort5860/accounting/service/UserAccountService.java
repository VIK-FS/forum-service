package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.*;

public interface UserAccountService {
    UserDto register(UserRegisterDto userRegisterDto);
    UserDto getUser(String login);
    UserDto removeUser(String login);
    UserDto updateUser(String login, UserUpdateDto userUpdateDto);
    RolesDto changeRolesList(String login, String role, boolean isAddRole);
    void changePassword(String login, String newPassword);
}
