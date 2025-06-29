package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.RolesDto;
import ait.cohort5860.accounting.dto.UserDto;
import ait.cohort5860.accounting.dto.UserRegisterDto;
import ait.cohort5860.accounting.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService{
    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        return null;
    }

    @Override
    public UserDto getUser(String login) {
        return null;
    }

    @Override
    public UserDto removeUser(String login) {
        return null;
    }

    @Override
    public UserDto updateUser(String login, UserUpdateDto userUpdateDto) {
        return null;
    }

    @Override
    public RolesDto changeRoles(String login, String role, boolean isAddRole) {
        return null;
    }

    @Override
    public void changePassword(String login, String newPassword) {

    }
}
