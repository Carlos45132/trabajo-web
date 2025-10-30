package com.example.sistema.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sistema.model.Convocatoria;
import com.example.sistema.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reclutador")
public class ReclutadorController {

    private List<Convocatoria> listaConvocatorias = new ArrayList<>();

    @GetMapping("/convocatorias")
    public String mostrarConvocatorias(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        // Lista actual de convocatorias (para las cards)
        model.addAttribute("convocatorias", listaConvocatorias);

        // Objeto vacío para el modal (formulario)
        model.addAttribute("nuevaConvocatoria", new Convocatoria());

        return "convocatorias";
    }

    @PostMapping("/convocatorias/guardar")
    public String guardarConvocatoria(@ModelAttribute("nuevaConvocatoria") Convocatoria convocatoria) {
        convocatoria.setId((long) (listaConvocatorias.size() + 1)); // Asignar ID simple
        listaConvocatorias.add(convocatoria);
        return "redirect:/reclutador/convocatorias";
    }
}

