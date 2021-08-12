package com.cursojava.java.controllers;

import com.cursojava.java.dao.UsuarioDao;
import com.cursojava.java.models.Usuario;
import com.cursojava.java.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

//    @RequestMapping(value = "usuario")
//    public Usuario getUsuario(){
//        Usuario usuario = new Usuario();
//
//        usuario.setNombre("Lucas");
//        usuario.setApellido("Moy");
//        usuario.setEmail("lucasmoy@gmail.com");
//        usuario.setTelefono("3109201");
//
//        return usuario;
//    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id) {
        Usuario usuario = new Usuario();

        usuario.setId(id);
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("lucasmoy@gmail.com");
        usuario.setTelefono("3109201");

        return usuario;
    }

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @RequestMapping(value = "api/usuarios")
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token) {

        if (validarToken(token)){ return null; }

        return usuarioDao.getUsuarios();
    /*
//      usaremos usuario DAO
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();

        usuario.setId(432L);
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("lucasmoy@gmail.com");
        usuario.setTelefono("3109201");

        Usuario usuario2 = new Usuario();

        usuario2.setId(123L);
        usuario2.setNombre("Maria");
        usuario2.setApellido("Gonzales");
        usuario2.setEmail("marygonzales@gmail.com");
        usuario2.setTelefono("74837478");

        Usuario usuario3 = new Usuario();

        usuario3.setId(768L);
        usuario3.setNombre("Gabriel");
        usuario3.setApellido("Aliaga");
        usuario3.setEmail("gabrielali@gmail.com");
        usuario3.setTelefono("92934992");

        usuarios.add(usuario);
        usuarios.add(usuario2);
        usuarios.add(usuario3);
        return usuarios;
        */
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());

        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "api/usuario32")
    public Usuario editar(){
        Usuario usuario = new Usuario();

        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("lucasmoy@gmail.com");
        usuario.setTelefono("3109201");

        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){

        if (validarToken(token)){ return; }
        usuarioDao.eliminar(id);
    }
}
