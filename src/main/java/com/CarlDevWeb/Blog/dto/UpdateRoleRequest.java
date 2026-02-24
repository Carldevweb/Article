package com.CarlDevWeb.Blog.dto;

import com.CarlDevWeb.Blog.enums.Role;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    private Role role;
}