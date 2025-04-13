package com.tecnica.tecnica.service.implementation;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tecnica.tecnica.dto.CrearProductoDTO;
import com.tecnica.tecnica.entity.Cliente;
import com.tecnica.tecnica.entity.Producto;
import com.tecnica.tecnica.entity.Transaccion;
import com.tecnica.tecnica.repository.ClienteRepository;
import com.tecnica.tecnica.repository.ProductoRepository;
import com.tecnica.tecnica.repository.TransaccionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductoServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductoServiceImplDiffblueTest {
    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoServiceImpl productoServiceImpl;

    @MockBean
    private TransaccionRepository transaccionRepository;

    /**
     * Test {@link ProductoServiceImpl#agregarProducto(Long, CrearProductoDTO)}.
     * <ul>
     *   <li>Given {@link ClienteRepository} {@link CrudRepository#findById(Object)}
     * return empty.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ProductoServiceImpl#agregarProducto(Long, CrearProductoDTO)}
     */
    @Test
    @DisplayName("Test agregarProducto(Long, CrearProductoDTO); given ClienteRepository findById(Object) return empty")
    void testAgregarProducto_givenClienteRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<Cliente> emptyResult = Optional.empty();
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        CrearProductoDTO productoDTO = new CrearProductoDTO();
        productoDTO.setEstado("Estado");
        productoDTO.setExentaGMF(true);
        productoDTO.setNumeroCuenta("Numero Cuenta");
        productoDTO.setSaldo(10.0d);
        productoDTO.setTipoCuenta("Tipo Cuenta");

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> productoServiceImpl.agregarProducto(1L, productoDTO));
        verify(clienteRepository).findById(eq(1L));
    }

    /**
     * Test {@link ProductoServiceImpl#obtenerProductosPorCliente(Long)}.
     * <ul>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ProductoServiceImpl#obtenerProductosPorCliente(Long)}
     */
    @Test
    @DisplayName("Test obtenerProductosPorCliente(Long); then return Empty")
    void testObtenerProductosPorCliente_thenReturnEmpty() {
        // Arrange
        when(productoRepository.findByClienteId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        List<Producto> actualObtenerProductosPorClienteResult = productoServiceImpl.obtenerProductosPorCliente(1L);

        // Assert
        verify(productoRepository).findByClienteId(eq(1L));
        assertTrue(actualObtenerProductosPorClienteResult.isEmpty());
    }

    /**
     * Test {@link ProductoServiceImpl#obtenerProductosPorCliente(Long)}.
     * <ul>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ProductoServiceImpl#obtenerProductosPorCliente(Long)}
     */
    @Test
    @DisplayName("Test obtenerProductosPorCliente(Long); then throw IllegalArgumentException")
    void testObtenerProductosPorCliente_thenThrowIllegalArgumentException() {
        // Arrange
        when(productoRepository.findByClienteId(Mockito.<Long>any())).thenThrow(new IllegalArgumentException("foo"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> productoServiceImpl.obtenerProductosPorCliente(1L));
        verify(productoRepository).findByClienteId(eq(1L));
    }

    /**
     * Test {@link ProductoServiceImpl#eliminarProducto(Long)}.
     * <ul>
     *   <li>Given {@link Producto#Producto()} Cliente is
     * {@link Cliente#Cliente()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ProductoServiceImpl#eliminarProducto(Long)}
     */
    @Test
    @DisplayName("Test eliminarProducto(Long); given Producto() Cliente is Cliente()")
    void testEliminarProducto_givenProductoClienteIsCliente() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setApellido("Apellido");
        cliente.setCorreoElectronico("Correo Electronico");
        cliente.setFechaCreacion(LocalDate.of(1970, 1, 1));
        cliente.setFechaModificacion(LocalDate.of(1970, 1, 1));
        cliente.setFechaNacimiento(LocalDate.of(1970, 1, 1));
        cliente.setId(1L);
        cliente.setNombres("Nombres");
        cliente.setNumeroIdentificacion("Numero Identificacion");
        cliente.setProductos(new ArrayList<>());
        cliente.setTipoIdentificacion("Tipo Identificacion");

        Producto producto = new Producto();
        producto.setCliente(cliente);
        producto.setEstado("Estado");
        producto.setExentaGMF(true);
        producto.setFechaCreacion(LocalDate.of(1970, 1, 1));
        producto.setFechaModificacion(LocalDate.of(1970, 1, 1));
        producto.setId(1L);
        producto.setNumeroCuenta("Numero Cuenta");
        producto.setSaldo(10.0d);
        producto.setTipoCuenta("Tipo Cuenta");
        Optional<Producto> ofResult = Optional.of(producto);
        when(productoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> productoServiceImpl.eliminarProducto(1L));
        verify(productoRepository).findById(eq(1L));
    }

    /**
     * Test {@link ProductoServiceImpl#eliminarProducto(Long)}.
     * <ul>
     *   <li>Given {@link Producto} {@link Producto#getSaldo()} return
     * {@code -0.5}.</li>
     *   <li>Then calls {@link Producto#getNumeroCuenta()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ProductoServiceImpl#eliminarProducto(Long)}
     */
    @Test
    @DisplayName("Test eliminarProducto(Long); given Producto getSaldo() return '-0.5'; then calls getNumeroCuenta()")
    void testEliminarProducto_givenProductoGetSaldoReturn05_thenCallsGetNumeroCuenta() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setApellido("Apellido");
        cliente.setCorreoElectronico("Correo Electronico");
        cliente.setFechaCreacion(LocalDate.of(1970, 1, 1));
        cliente.setFechaModificacion(LocalDate.of(1970, 1, 1));
        cliente.setFechaNacimiento(LocalDate.of(1970, 1, 1));
        cliente.setId(1L);
        cliente.setNombres("Nombres");
        cliente.setNumeroIdentificacion("Numero Identificacion");
        cliente.setProductos(new ArrayList<>());
        cliente.setTipoIdentificacion("Tipo Identificacion");
        Producto producto = mock(Producto.class);
        when(producto.getSaldo()).thenReturn(-0.5d);
        when(producto.getNumeroCuenta()).thenReturn("Numero Cuenta");
        doNothing().when(producto).setCliente(Mockito.<Cliente>any());
        doNothing().when(producto).setEstado(Mockito.<String>any());
        doNothing().when(producto).setExentaGMF(anyBoolean());
        doNothing().when(producto).setFechaCreacion(Mockito.<LocalDate>any());
        doNothing().when(producto).setFechaModificacion(Mockito.<LocalDate>any());
        doNothing().when(producto).setId(Mockito.<Long>any());
        doNothing().when(producto).setNumeroCuenta(Mockito.<String>any());
        doNothing().when(producto).setSaldo(anyDouble());
        doNothing().when(producto).setTipoCuenta(Mockito.<String>any());
        producto.setCliente(cliente);
        producto.setEstado("Estado");
        producto.setExentaGMF(true);
        producto.setFechaCreacion(LocalDate.of(1970, 1, 1));
        producto.setFechaModificacion(LocalDate.of(1970, 1, 1));
        producto.setId(1L);
        producto.setNumeroCuenta("Numero Cuenta");
        producto.setSaldo(10.0d);
        producto.setTipoCuenta("Tipo Cuenta");
        Optional<Producto> ofResult = Optional.of(producto);
        doNothing().when(productoRepository).delete(Mockito.<Producto>any());
        when(productoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(transaccionRepository.findByProductoNumeroCuenta(Mockito.<String>any())).thenReturn(new ArrayList<>());
        doNothing().when(transaccionRepository).deleteAll(Mockito.<Iterable<Transaccion>>any());

        // Act
        productoServiceImpl.eliminarProducto(1L);

        // Assert that nothing has changed
        verify(producto).getNumeroCuenta();
        verify(producto).getSaldo();
        verify(producto).setCliente(isA(Cliente.class));
        verify(producto).setEstado(eq("Estado"));
        verify(producto).setExentaGMF(eq(true));
        verify(producto).setFechaCreacion(isA(LocalDate.class));
        verify(producto).setFechaModificacion(isA(LocalDate.class));
        verify(producto).setId(eq(1L));
        verify(producto).setNumeroCuenta(eq("Numero Cuenta"));
        verify(producto).setSaldo(eq(10.0d));
        verify(producto).setTipoCuenta(eq("Tipo Cuenta"));
        verify(transaccionRepository).findByProductoNumeroCuenta(eq("Numero Cuenta"));
        verify(productoRepository).delete(isA(Producto.class));
        verify(transaccionRepository).deleteAll(isA(Iterable.class));
        verify(productoRepository).findById(eq(1L));
    }

    /**
     * Test {@link ProductoServiceImpl#eliminarProducto(Long)}.
     * <ul>
     *   <li>Given {@link ProductoRepository} {@link CrudRepository#findById(Object)}
     * return empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link ProductoServiceImpl#eliminarProducto(Long)}
     */
    @Test
    @DisplayName("Test eliminarProducto(Long); given ProductoRepository findById(Object) return empty")
    void testEliminarProducto_givenProductoRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<Producto> emptyResult = Optional.empty();
        when(productoRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> productoServiceImpl.eliminarProducto(1L));
        verify(productoRepository).findById(eq(1L));
    }

    /**
     * Test {@link ProductoServiceImpl#actualizarEstadoProducto(Long, String)}.
     * <p>
     * Method under test:
     * {@link ProductoServiceImpl#actualizarEstadoProducto(Long, String)}
     */
    @Test
    @DisplayName("Test actualizarEstadoProducto(Long, String)")
    void testActualizarEstadoProducto() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setApellido("Apellido");
        cliente.setCorreoElectronico("Correo Electronico");
        cliente.setFechaCreacion(LocalDate.of(1970, 1, 1));
        cliente.setFechaModificacion(LocalDate.of(1970, 1, 1));
        cliente.setFechaNacimiento(LocalDate.of(1970, 1, 1));
        cliente.setId(1L);
        cliente.setNombres("Nombres");
        cliente.setNumeroIdentificacion("Numero Identificacion");
        cliente.setProductos(new ArrayList<>());
        cliente.setTipoIdentificacion("Tipo Identificacion");

        Producto producto = new Producto();
        producto.setCliente(cliente);
        producto.setEstado("Estado");
        producto.setExentaGMF(true);
        producto.setFechaCreacion(LocalDate.of(1970, 1, 1));
        producto.setFechaModificacion(LocalDate.of(1970, 1, 1));
        producto.setId(1L);
        producto.setNumeroCuenta("Numero Cuenta");
        producto.setSaldo(10.0d);
        producto.setTipoCuenta("Tipo Cuenta");
        Optional<Producto> ofResult = Optional.of(producto);
        when(productoRepository.save(Mockito.<Producto>any())).thenThrow(new IllegalArgumentException("foo"));
        when(productoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoServiceImpl.actualizarEstadoProducto(1L, "Nuevo Estado"));
        verify(productoRepository).findById(eq(1L));
        verify(productoRepository).save(isA(Producto.class));
    }

    /**
     * Test {@link ProductoServiceImpl#actualizarEstadoProducto(Long, String)}.
     * <ul>
     *   <li>Given {@link ProductoRepository} {@link CrudRepository#findById(Object)}
     * return empty.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ProductoServiceImpl#actualizarEstadoProducto(Long, String)}
     */
    @Test
    @DisplayName("Test actualizarEstadoProducto(Long, String); given ProductoRepository findById(Object) return empty")
    void testActualizarEstadoProducto_givenProductoRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<Producto> emptyResult = Optional.empty();
        when(productoRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoServiceImpl.actualizarEstadoProducto(1L, "Nuevo Estado"));
        verify(productoRepository).findById(eq(1L));
    }

    /**
     * Test {@link ProductoServiceImpl#actualizarEstadoProducto(Long, String)}.
     * <ul>
     *   <li>Then return {@link Producto#Producto()}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ProductoServiceImpl#actualizarEstadoProducto(Long, String)}
     */
    @Test
    @DisplayName("Test actualizarEstadoProducto(Long, String); then return Producto()")
    void testActualizarEstadoProducto_thenReturnProducto() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setApellido("Apellido");
        cliente.setCorreoElectronico("Correo Electronico");
        cliente.setFechaCreacion(LocalDate.of(1970, 1, 1));
        cliente.setFechaModificacion(LocalDate.of(1970, 1, 1));
        cliente.setFechaNacimiento(LocalDate.of(1970, 1, 1));
        cliente.setId(1L);
        cliente.setNombres("Nombres");
        cliente.setNumeroIdentificacion("Numero Identificacion");
        cliente.setProductos(new ArrayList<>());
        cliente.setTipoIdentificacion("Tipo Identificacion");

        Producto producto = new Producto();
        producto.setCliente(cliente);
        producto.setEstado("Estado");
        producto.setExentaGMF(true);
        producto.setFechaCreacion(LocalDate.of(1970, 1, 1));
        producto.setFechaModificacion(LocalDate.of(1970, 1, 1));
        producto.setId(1L);
        producto.setNumeroCuenta("Numero Cuenta");
        producto.setSaldo(10.0d);
        producto.setTipoCuenta("Tipo Cuenta");
        Optional<Producto> ofResult = Optional.of(producto);

        Cliente cliente2 = new Cliente();
        cliente2.setApellido("Apellido");
        cliente2.setCorreoElectronico("Correo Electronico");
        cliente2.setFechaCreacion(LocalDate.of(1970, 1, 1));
        cliente2.setFechaModificacion(LocalDate.of(1970, 1, 1));
        cliente2.setFechaNacimiento(LocalDate.of(1970, 1, 1));
        cliente2.setId(1L);
        cliente2.setNombres("Nombres");
        cliente2.setNumeroIdentificacion("Numero Identificacion");
        cliente2.setProductos(new ArrayList<>());
        cliente2.setTipoIdentificacion("Tipo Identificacion");

        Producto producto2 = new Producto();
        producto2.setCliente(cliente2);
        producto2.setEstado("Estado");
        producto2.setExentaGMF(true);
        producto2.setFechaCreacion(LocalDate.of(1970, 1, 1));
        producto2.setFechaModificacion(LocalDate.of(1970, 1, 1));
        producto2.setId(1L);
        producto2.setNumeroCuenta("Numero Cuenta");
        producto2.setSaldo(10.0d);
        producto2.setTipoCuenta("Tipo Cuenta");
        when(productoRepository.save(Mockito.<Producto>any())).thenReturn(producto2);
        when(productoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Producto actualActualizarEstadoProductoResult = productoServiceImpl.actualizarEstadoProducto(1L, "Nuevo Estado");

        // Assert
        verify(productoRepository).findById(eq(1L));
        verify(productoRepository).save(isA(Producto.class));
        assertSame(producto2, actualActualizarEstadoProductoResult);
    }
}
