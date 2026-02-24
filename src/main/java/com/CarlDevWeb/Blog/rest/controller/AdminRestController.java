package com.CarlDevWeb.Blog.rest.controller;

import com.CarlDevWeb.Blog.dto.AdminUserDto;
import com.CarlDevWeb.Blog.dto.CreateCategorieRequest;
import com.CarlDevWeb.Blog.dto.UpdateRoleRequest;
import com.CarlDevWeb.Blog.entity.Categorie;
import com.CarlDevWeb.Blog.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping("/users")
    public Page<AdminUserDto> getUsers(
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return adminService.getUsers(q, pageable);
    }

    @PatchMapping("/users/{id}/role")
    public AdminUserDto updateRole(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request
    ) {
        return adminService.updateRole(id, request);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
    }

    @PostMapping("/categories")
    public Categorie createCategorie(@RequestBody CreateCategorieRequest request) {
        return adminService.createCategorie(request);
    }
}
