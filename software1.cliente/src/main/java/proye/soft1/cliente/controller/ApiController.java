package proye.soft1.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proye.soft1.cliente.model.*;
import proye.soft1.cliente.repository.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private ProcesoRepository procesoRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    // --- AUTH ---
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody Cliente cliente) {
        if (clienteRepository.findByDocumento(cliente.getDocumento()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Documento ya registrado"));
        }
        Cliente saved = clienteRepository.save(cliente);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String documento = credentials.get("documento");
        String password = credentials.get("password");
        
        Optional<Cliente> clienteOpt = clienteRepository.findByDocumento(documento);
        if (clienteOpt.isPresent() && clienteOpt.get().getPassword().equals(password)) {
            return ResponseEntity.ok(clienteOpt.get());
        }
        return ResponseEntity.status(401).body(Map.of("message", "Credenciales incorrectas"));
    }

    // --- PROPIEDADES ---
    @GetMapping("/propiedades/cliente/{clienteId}")
    public ResponseEntity<List<Propiedad>> getPropiedades(@PathVariable Long clienteId) {
        return ResponseEntity.ok(propiedadRepository.findByClienteId(clienteId));
    }

    @PostMapping("/propiedades")
    public ResponseEntity<?> addPropiedad(@RequestBody Map<String, Object> payload) {
        Long clienteId = Long.valueOf(payload.get("clienteId").toString());
        String direccion = payload.get("direccion").toString();

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Cliente no encontrado"));
        }

        Propiedad propiedad = new Propiedad();
        propiedad.setDireccion(direccion);
        propiedad.setCliente(cliente);
        return ResponseEntity.ok(propiedadRepository.save(propiedad));
    }

    // --- PROCESOS ---
    @GetMapping("/procesos/cliente/{clienteId}")
    public ResponseEntity<List<Proceso>> getProcesos(@PathVariable Long clienteId) {
        return ResponseEntity.ok(procesoRepository.findByClienteId(clienteId));
    }

    @PostMapping("/procesos/solicitar")
    public ResponseEntity<?> solicitarGas(@RequestBody Map<String, Long> payload) {
        Long propiedadId = payload.get("propiedadId");
        Propiedad propiedad = propiedadRepository.findById(propiedadId).orElse(null);
        if (propiedad == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Propiedad no encontrada"));
        }

        Proceso proceso = new Proceso();
        proceso.setPropiedad(propiedad);
        proceso.setEstado("PENDIENTE");
        return ResponseEntity.ok(procesoRepository.save(proceso));
    }

    // --- CONTRATOS ---
    @GetMapping("/clientes/{documento}/contratos")
    public ResponseEntity<List<Contrato>> getContratosByDocumento(@PathVariable String documento) {
        return ResponseEntity.ok(contratoRepository.findByClienteDocumento(documento));
    }

    @GetMapping("/contratos/{idContrato}/estado-servicio")
    public ResponseEntity<?> getEstadoServicio(@PathVariable Long idContrato) {
        Optional<Contrato> contrato = contratoRepository.findById(idContrato);
        if (contrato.isPresent()) {
            return ResponseEntity.ok(Map.of("estadoServicio", contrato.get().getEstadoServicio()));
        }
        return ResponseEntity.notFound().build();
    }

    // --- FACTURAS ---
    @GetMapping("/contratos/{idContrato}/facturas")
    public ResponseEntity<List<Factura>> getFacturasByContrato(@PathVariable Long idContrato) {
        return ResponseEntity.ok(facturaRepository.findByContratoId(idContrato));
    }

    @GetMapping("/facturas/{idFactura}")
    public ResponseEntity<Factura> getFactura(@PathVariable Long idFactura) {
        return facturaRepository.findById(idFactura)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- PAGOS ---
    @PostMapping("/pagos")
    public ResponseEntity<?> registrarPago(@RequestBody Map<String, Object> payload) {
        // Datos minimos: idContrato, clienteId, valorPagado, fechaPago, referenciaPago, medioPago y estadoPago
        System.out.println("Enviando pago a API Externa: " + payload);
        
        // Simulación de envío a Camunda si corresponde
        boolean esProcesoActivoSuspension = payload.containsKey("esProcesoActivoSuspension") ? (Boolean) payload.get("esProcesoActivoSuspension") : false;
        boolean antesDeSuspension = payload.containsKey("antesDeSuspension") ? (Boolean) payload.get("antesDeSuspension") : true;

        if (esProcesoActivoSuspension) {
            if (antesDeSuspension) {
                System.out.println("Disparando evento en Camunda: Pago Recibido (POST /camunda/pagos/recibido)");
            } else {
                System.out.println("Disparando evento en Camunda: Pago Confirmado (POST /camunda/pagos/confirmado)");
            }
        }

        return ResponseEntity.ok(Map.of("message", "Pago registrado y enviado a la API externa exitosamente", "datos", payload));
    }

    // --- NOTIFICACIONES ---
    @GetMapping("/clientes/{clienteId}/notificaciones")
    public ResponseEntity<List<Notificacion>> getNotificaciones(@PathVariable Long clienteId) {
        return ResponseEntity.ok(notificacionRepository.findByClienteIdOrderByFechaDesc(clienteId));
    }

    // --- SOLICITUDES ---
    @PostMapping("/solicitudes")
    public ResponseEntity<?> crearSolicitud(@RequestBody Solicitud solicitud) {
        solicitud.setFechaCreacion(java.time.LocalDateTime.now());
        if (solicitud.getEstado() == null) {
            solicitud.setEstado("radicada");
        }
        Solicitud saved = solicitudRepository.save(solicitud);
        // Simular envío a plataforma externa de empresa
        System.out.println("Enviando solicitud a plataforma empresa: " + saved);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/clientes/{clienteId}/solicitudes")
    public ResponseEntity<List<Solicitud>> getSolicitudesByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(solicitudRepository.findByIdClienteOrderByFechaCreacionDesc(clienteId));
    }

    @GetMapping("/solicitudes/{idSolicitud}")
    public ResponseEntity<Solicitud> getSolicitud(@PathVariable Long idSolicitud) {
        return solicitudRepository.findById(idSolicitud)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- MOCKS CAMUNDA/EMPRESA ---
    @PostMapping("/camunda/pagos/recibido")
    public ResponseEntity<?> mockPagoRecibidoCamunda(@RequestBody Map<String, Object> payload) {
        System.out.println("Mock Camunda: Evento Pago Recibido procesado - " + payload);
        return ResponseEntity.ok(Map.of("status", "success", "event", "Pago Recibido"));
    }

    @PostMapping("/camunda/pagos/confirmado")
    public ResponseEntity<?> mockPagoConfirmadoCamunda(@RequestBody Map<String, Object> payload) {
        System.out.println("Mock Camunda: Evento Pago Confirmado procesado - " + payload);
        return ResponseEntity.ok(Map.of("status", "success", "event", "Pago Confirmado"));
    }
}
