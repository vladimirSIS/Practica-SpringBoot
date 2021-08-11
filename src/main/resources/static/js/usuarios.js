// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuarios').DataTable();
});

let cargarUsuarios = async () => {
    const request = await fetch('api/usuarios', {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    });
    const usuarios = await request.json();

    let listadohtml = '';
    for (let usuario of usuarios) {
        let usuariohtml = `
            <tr>
                <td>${usuario.id}</td>
                <td>${usuario.nombre}</td>
                <td>${usuario.apellido}</td>
                <td>${usuario.email}</td>
                <td>${usuario.telefono===null? '-' : usuario.telefono}</td>
                <td>
                    <a href="#" onclick="eliminarUsuario(${usuario.id})" class="btn btn-danger btn-circle btn-sm">
                        <i class="fas fa-trash"></i>
                    </a>
                </td>
            </tr>
        `;
        listadohtml += usuariohtml;
    }

    console.log(usuarios);
    document.querySelector('#usuarios tbody').outerHTML= listadohtml;
}

let eliminarUsuario = async (id) =>{
    if (!confirm('Desea eliminar')){
        return;
    }
    const request = await fetch('api/usuarios/'+id, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    document.location.reload(); // actualiza
}