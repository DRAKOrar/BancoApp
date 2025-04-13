    package com.tecnica.tecnica.service.implementation;

    import com.tecnica.tecnica.dto.CrearClienteDTO;
    import com.tecnica.tecnica.entity.Cliente;
    import com.tecnica.tecnica.repository.ClienteRepository;
    import com.tecnica.tecnica.service.ClienteService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.time.Period;
    import java.util.List;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    @Service
    public class ClienteImpl implements ClienteService {

        @Autowired
        private ClienteRepository clienteRepository;

        @Override
        public Cliente crearCliente(CrearClienteDTO crearClienteDTO) {

            if (esMenorDeEdad(crearClienteDTO.getFechaNacimiento())) {
                throw new IllegalArgumentException("Debes ser mayor de edad para registrarte");
            }

            if (!esCorreoValido(crearClienteDTO.getCorreoElectronico())) {
                throw new IllegalArgumentException("Formato de correo electrónico inválido");
            }

            if (crearClienteDTO.getNombres() == null || crearClienteDTO.getNombres().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }

            if (crearClienteDTO.getNumeroIdentificacion() == null || crearClienteDTO.getNumeroIdentificacion().trim().isEmpty()) {
                throw new IllegalArgumentException("El número de identificación es obligatorio");
            }

            if (crearClienteDTO.getNumeroIdentificacion().length() < 6) {
                throw new IllegalArgumentException("El número de identificación debe tener al menos 6 dígitos");
            }

            Cliente cliente = new Cliente();
            cliente.setTipoIdentificacion(crearClienteDTO.getTipoIdentificacion());
            cliente.setNumeroIdentificacion(crearClienteDTO.getNumeroIdentificacion());
            cliente.setNombres(crearClienteDTO.getNombres());
            cliente.setApellido(crearClienteDTO.getApellido());
            cliente.setCorreoElectronico(crearClienteDTO.getCorreoElectronico());
            cliente.setFechaNacimiento(crearClienteDTO.getFechaNacimiento());
            cliente.setFechaCreacion(LocalDate.now());
            cliente.setFechaModificacion(LocalDate.now());

            return clienteRepository.save(cliente);
        }

        @Override
        public List<Cliente> listarClientes() {
            return (List<Cliente>) clienteRepository.findAll();
        }

        @Override
        public void eliminarCliente(Long id) {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

            // Verificar si el cliente tiene productos activos
            boolean tieneProductosActivos = cliente.getProductos().stream()
                    .anyMatch(producto -> "Activa".equalsIgnoreCase(producto.getEstado()));

            if (tieneProductosActivos) {
                throw new IllegalArgumentException("No se puede eliminar el cliente porque tiene productos activos");
            }

            clienteRepository.delete(cliente);
        }

        @Override
        public Cliente actualizarCliente(Long id, CrearClienteDTO actualizarClienteDTO) {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

            // Validaciones similares a la creación de cliente
            if (actualizarClienteDTO.getCorreoElectronico() != null &&
                    !esCorreoValido(actualizarClienteDTO.getCorreoElectronico())) {
                throw new IllegalArgumentException("Formato de correo electrónico inválido");
            }

            // Actualizamos los campos solo si no son nulos
            if (actualizarClienteDTO.getNombres() != null) {
                cliente.setNombres(actualizarClienteDTO.getNombres());
            }
            if (actualizarClienteDTO.getApellido() != null) {
                cliente.setApellido(actualizarClienteDTO.getApellido());
            }
            if (actualizarClienteDTO.getCorreoElectronico() != null) {
                cliente.setCorreoElectronico(actualizarClienteDTO.getCorreoElectronico());
            }

            cliente.setFechaModificacion(LocalDate.now());

            return clienteRepository.save(cliente);
        }



        public boolean esCorreoValido(String correoElectronico) {
            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(correoElectronico);
            return matcher.matches();
        }

        private boolean esMenorDeEdad(LocalDate fechaNacimiento) {
            return Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18;
        }

    }
