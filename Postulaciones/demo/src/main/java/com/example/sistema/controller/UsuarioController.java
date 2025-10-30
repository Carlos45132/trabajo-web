package com.example.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.sistema.model.Usuario;
import com.example.sistema.service.GestorUsuario;

import jakarta.validation.Valid;


@Controller
public class UsuarioController {

    private final GestorUsuario gestorUsuario;

    public UsuarioController(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }

    // Página de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // Guardar nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {

        // Validación personalizada del número de documento
        String docError = usuario.getDocNumeroError();
        if (docError != null) {
            result.rejectValue("docNumero", "error.docNumero", docError);
        }

        // Validar que las contraseñas coincidan con que sea ingresado
        if (!usuario.getContrasena().equals(usuario.getConfirmarContrasena())) {
            result.rejectValue("confirmarContrasena", "error.confirmarContrasena", "Las contraseñas no coinciden");
        }

        // Si hay errores de validación, regresar al formulario
        if (result.hasErrors()) {
            model.addAttribute("activeTab", "registro");
            return "registro"; 
        }

        // Guardar usuario en memoria
        gestorUsuario.guardar(usuario);

        // Para que el se limpie ----el formulario limpio y mensaje de éxito
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("registroExitoso", true);
        model.addAttribute("activeTab", "registro");

        return "redirect:/login?registroExitoso";
    }

    // Panel administrador
    @GetMapping("/admin/usuarios")
    public String mostrarAdmin(Model model,
            @RequestParam(name = "buscar", required = false) String buscar) {
        if (buscar != null && !buscar.isEmpty()) {
            //llamar al service..donde esta la lsita de usuarios para filtrar
            model.addAttribute("usuarios", gestorUsuario.buscarPorTexto(buscar));
        } else {
            model.addAttribute("usuarios", gestorUsuario.listarTodos());
        }

        model.addAttribute("usuario", new Usuario()); // para modal de edición
        model.addAttribute("buscar", buscar); //  valor en el input, osea para que no se borre lo que se escribe xd
        return "admin_usuarios";
    }

    // Editar/Actualizar usuario
    @PostMapping("/admin/usuarios/actualizar")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        if (usuario.getId() != null) {
            gestorUsuario.actualizar(usuario.getId(), usuario);
        }
        return "redirect:/admin/usuarios";
    }

    // Eliminar
    @GetMapping("/admin/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        gestorUsuario.eliminar(id);
        return "redirect:/admin/usuarios";
    }
}

