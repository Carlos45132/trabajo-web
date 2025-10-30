package com.example.sistema.controller;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.sistema.model.Usuario;
import com.example.sistema.service.GestorUsuario;

@Controller
public class LoginController {

    private final GestorUsuario gestorUsuario;
    // El gestorUsuario es el objeto que el controlador usa para buscar, registrar o
    // manejar usuarios.

    public LoginController(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login"; // login.html
    }

    //Procesar login
    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model, HttpSession session) {
        // Validación de campos vacíos
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty() ||
                usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
            model.addAttribute("error", "Debes ingresar tu correo y contraseña");
            return "login";
        }

        Optional<Usuario> userOpt = gestorUsuario.buscarPorCorreoYContrasena(
                usuario.getCorreo(),
                usuario.getContrasena());

        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();

            // Guardar usuario en sesión activa
            session.setAttribute("usuarioLogueado", user);

            if ("ADMIN".equalsIgnoreCase(user.getRol())) {
                return "redirect:/admin/usuarios";
            } else if ("RECLUTADOR".equalsIgnoreCase(user.getRol())) {
                return "redirect:/reclutador/convocatorias";
            } else if ("POSTULANTE".equalsIgnoreCase(user.getRol())) {
                return "redirect:/postulante/test";
            } else {
                model.addAttribute("error", "Rol de usuario no reconocido");
                return "login";
            }

        } else {
            // Si no existe el usuario, mostrar mensaje de error
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }
    }

    // Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
