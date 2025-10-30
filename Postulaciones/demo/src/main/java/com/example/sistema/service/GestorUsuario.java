package com.example.sistema.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.sistema.model.Usuario;

@Service
public class GestorUsuario {

    private final List<Usuario> usuarios = new ArrayList<>();
    private Long nextId = 1L;

    public List<Usuario> listarTodos() {
        return usuarios;
    }

    public GestorUsuario() {
        // Usuario administrador por defecto
        Usuario admin = new Usuario();
        admin.setId(nextId++);
        admin.setNombre("Carlos");
        admin.setApellido("Perez");
        admin.setCorreo("admin@gmail.com");
        admin.setContrasena("admin123");
        admin.setRol("Admin");
        usuarios.add(admin);

        // Usuario RECLUTADOR
        Usuario reclutador = new Usuario();
        reclutador.setId(nextId++);
        reclutador.setNombre("Miguel");
        reclutador.setApellido("Gómez");
        reclutador.setCorreo("reclutador@gmail.com");
        reclutador.setContrasena("reclu123");
        reclutador.setRol("Reclutador");
        usuarios.add(reclutador);

    }

    public Usuario guardar(Usuario usuario) {
        usuario.setId(nextId++);
        if (usuario.getRol() == null) {
            usuario.setRol("USUARIO"); // todos los nuevos por defecto
        }
        usuarios.add(usuario);
        return usuario;
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOpt = buscarPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            u.setNombre(usuarioActualizado.getNombre());
            u.setApellido(usuarioActualizado.getApellido());
            u.setCorreo(usuarioActualizado.getCorreo());
            u.setTelefono(usuarioActualizado.getTelefono());
            u.setDoc(usuarioActualizado.getDoc());
            u.setDocNumero(usuarioActualizado.getDocNumero());
            u.setSexo(usuarioActualizado.getSexo());
            u.setFechaNac(usuarioActualizado.getFechaNac());
            u.setContrasena(usuarioActualizado.getContrasena());
            return u;
        }
        return null;
    }

    public boolean eliminar(Long id) {
        return usuarios.removeIf(u -> u.getId().equals(id));
    }

    public List<Usuario> buscarPorTexto(String texto) {
        return usuarios.stream()
                .filter(u -> u.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                        u.getApellido().toLowerCase().contains(texto.toLowerCase()) ||
                        u.getCorreo().toLowerCase().contains(texto.toLowerCase()) ||
                        u.getDocNumero().toLowerCase().contains(texto.toLowerCase()) ||
                        (u.getDoc() != null && u.getDoc().toLowerCase().contains(texto.toLowerCase())) ||
                        (u.getFechaNac() != null && u.getFechaNac().toString().contains(texto)))
                .collect(Collectors.toList());
    }

    // Método para la validación en el login
    public Optional<Usuario> buscarPorCorreoYContrasena(String correo, String contrasena) {
        return usuarios.stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo)
                        && u.getContrasena().equals(contrasena))
                .findFirst();
    }


}
