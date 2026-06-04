package proye.soft1.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import proye.soft1.cliente.controller.ApiController;
import proye.soft1.cliente.model.Cliente;
import proye.soft1.cliente.model.Solicitud;
import proye.soft1.cliente.repository.*;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private PropiedadRepository propiedadRepository;

    @MockBean
    private ProcesoRepository procesoRepository;

    @MockBean
    private ContratoRepository contratoRepository;

    @MockBean
    private FacturaRepository facturaRepository;

    @MockBean
    private NotificacionRepository notificacionRepository;

    @MockBean
    private SolicitudRepository solicitudRepository;

    private Cliente testCliente;

    @BeforeEach
    void setUp() {
        testCliente = new Cliente();
        testCliente.setId(1L);
        testCliente.setDocumento("123456789");
        testCliente.setPassword("password123");
        testCliente.setNombre("Test User");
    }

    @Test
    void testRegisterExistingDocumento() throws Exception {
        Mockito.when(clienteRepository.findByDocumento("123456789")).thenReturn(Optional.of(testCliente));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCliente)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Documento ya registrado"));
    }

    @Test
    void testRegisterSuccess() throws Exception {
        Mockito.when(clienteRepository.findByDocumento("123456789")).thenReturn(Optional.empty());
        Mockito.when(clienteRepository.save(any(Cliente.class))).thenReturn(testCliente);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documento").value("123456789"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        Mockito.when(clienteRepository.findByDocumento("123456789")).thenReturn(Optional.of(testCliente));

        Map<String, String> creds = Map.of("documento", "123456789", "password", "password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creds)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documento").value("123456789"));
    }

    @Test
    void testLoginFailure() throws Exception {
        Mockito.when(clienteRepository.findByDocumento("123456789")).thenReturn(Optional.of(testCliente));

        Map<String, String> creds = Map.of("documento", "123456789", "password", "wrongpass");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creds)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Credenciales incorrectas"));
    }

    @Test
    void testAddPropiedadClienteNotFound() throws Exception {
        Mockito.when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Map<String, Object> payload = Map.of("clienteId", 99, "direccion", "Calle Falsa 123");

        mockMvc.perform(post("/api/propiedades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cliente no encontrado"));
    }

    @Test
    void testRegistrarPago() throws Exception {
        Map<String, Object> payload = Map.of(
            "idContrato", 1,
            "valorPagado", 50000,
            "esProcesoActivoSuspension", true,
            "antesDeSuspension", false
        );

        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Pago registrado y enviado a la API externa exitosamente"));
    }

    @Test
    void testCrearSolicitud() throws Exception {
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion("Revisión de factura");
        
        Solicitud savedSolicitud = new Solicitud();
        savedSolicitud.setId(1L);
        savedSolicitud.setDescripcion("Revisión de factura");
        savedSolicitud.setEstado("radicada");

        Mockito.when(solicitudRepository.save(any(Solicitud.class))).thenReturn(savedSolicitud);

        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitud)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("radicada"))
                .andExpect(jsonPath("$.id").value(1));
    }
}
