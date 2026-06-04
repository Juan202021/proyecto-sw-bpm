const API_URL = '/api';
let currentUser = JSON.parse(localStorage.getItem('currentUser'));

function checkAuth() {
    if (!currentUser) {
        window.location.href = 'auth.html';
    }
}

function updateUserName() {
    const el = document.getElementById('user-name-display');
    if (el && currentUser) {
        el.textContent = `Hola, ${currentUser.nombre}`;
    }
}

// UI Helpers
function showToast(message) {
    const toast = document.getElementById('toast');
    if(!toast) return;
    toast.textContent = message;
    toast.classList.remove('hidden');
    setTimeout(() => {
        toast.classList.add('hidden');
    }, 3000);
}

// Auth Logic
function showAuthTab(tab) {
    document.querySelectorAll('.auth-content').forEach(el => el.classList.add('hidden'));
    document.querySelectorAll('.tab-btn').forEach(el => el.classList.remove('active'));
    
    const tabContent = document.getElementById(`${tab}-tab`);
    const tabBtn = document.getElementById(`btn-tab-${tab}`);
    
    if(tabContent) tabContent.classList.remove('hidden');
    if(tabBtn) tabBtn.classList.add('active');
}

const loginForm = document.getElementById('login-form');
if(loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const documento = document.getElementById('login-doc').value;
        const password = document.getElementById('login-pass').value;

        try {
            const res = await fetch(`${API_URL}/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ documento, password })
            });
            const data = await res.json();
            
            if (res.ok) {
                localStorage.setItem('currentUser', JSON.stringify(data));
                window.location.href = 'propiedades.html';
            } else {
                showToast(data.message || 'Error al iniciar sesión');
            }
        } catch (err) {
            showToast('Error de conexión');
        }
    });
}

const registerForm = document.getElementById('register-form');
if(registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const nombre = document.getElementById('reg-name').value;
        const documento = document.getElementById('reg-doc').value;
        const telefono = document.getElementById('reg-phone').value;
        const password = document.getElementById('reg-pass').value;

        try {
            const res = await fetch(`${API_URL}/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ nombre, documento, telefono, password })
            });
            const data = await res.json();
            
            if (res.ok) {
                showToast('Registro exitoso. Inicia sesión.');
                showAuthTab('login');
                document.getElementById('login-doc').value = documento;
                document.getElementById('login-pass').value = password;
            } else {
                showToast(data.message || 'Error en el registro');
            }
        } catch (err) {
            showToast('Error de conexión');
        }
    });
}

function logout() {
    localStorage.removeItem('currentUser');
    window.location.href = 'index.html';
}

// Properties Logic
const addPropertyForm = document.getElementById('add-property-form');
if(addPropertyForm) {
    addPropertyForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const direccion = document.getElementById('prop-address').value;

        try {
            const res = await fetch(`${API_URL}/propiedades`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ clienteId: currentUser.id, direccion })
            });
            
            if (res.ok) {
                showToast('Propiedad guardada exitosamente');
                document.getElementById('add-property-form').reset();
                loadProperties();
            } else {
                showToast('Error al guardar propiedad');
            }
        } catch (err) {
            showToast('Error de conexión');
        }
    });
}

async function loadProperties() {
    const list = document.getElementById('properties-list');
    if(!list) return;
    list.innerHTML = '<p>Cargando...</p>';

    try {
        const res = await fetch(`${API_URL}/propiedades/cliente/${currentUser.id}`);
        const propiedades = await res.json();
        
        if (propiedades.length === 0) {
            list.innerHTML = '<p>No tienes propiedades registradas.</p>';
            return;
        }

        list.innerHTML = propiedades.map(p => `
            <div class="card">
                <h3>${p.direccion}</h3>
                <button class="btn-action" onclick="solicitarGas(${p.id})">Solicitar Instalación de Gas</button>
            </div>
        `).join('');
    } catch (err) {
        list.innerHTML = '<p>Error al cargar propiedades.</p>';
    }
}

// Processes Logic
async function solicitarGas(propiedadId) {
    try {
        const res = await fetch(`${API_URL}/procesos/solicitar`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ propiedadId })
        });
        
        if (res.ok) {
            alert('Solicitud de instalación de gas creada exitosamente');
            window.location.href = 'procesos.html';
        } else {
            showToast('Error al crear solicitud');
        }
    } catch (err) {
        showToast('Error de conexión');
    }
}

async function loadProcesses() {
    const list = document.getElementById('processes-list');
    if(!list) return;
    list.innerHTML = '<p>Cargando...</p>';

    try {
        const res = await fetch(`${API_URL}/procesos/cliente/${currentUser.id}`);
        const procesos = await res.json();
        
        if (procesos.length === 0) {
            list.innerHTML = '<p>No tienes procesos activos.</p>';
            return;
        }

        list.innerHTML = procesos.map(p => `
            <div class="list-item">
                <div>
                    <strong>Proceso #${p.id}</strong>
                    <p style="color: #94a3b8; font-size: 0.9rem;">Propiedad: ${p.propiedad.direccion}</p>
                </div>
                <span class="badge ${p.estado}">${p.estado.replace('_', ' ')}</span>
            </div>
        `).join('');
    } catch (err) {
        list.innerHTML = '<p>Error al cargar procesos.</p>';
    }
}

// Contracts & Facturas Logic
async function loadContracts() {
    const list = document.getElementById('contracts-list');
    if(!list) return;
    document.getElementById('facturas-container').classList.add('hidden');
    list.innerHTML = '<p>Cargando...</p>';

    try {
        const res = await fetch(`${API_URL}/clientes/${currentUser.documento}/contratos`);
        const contratos = await res.json();
        
        if (contratos.length === 0) {
            list.innerHTML = '<p>No tienes contratos registrados.</p>';
            return;
        }

        list.innerHTML = contratos.map(c => `
            <div class="card" style="cursor: pointer;" onclick="loadFacturas(${c.id})">
                <h3>Contrato #${c.id}</h3>
                <p><strong>Estado:</strong> ${c.estadoServicio}</p>
                <p><strong>Medidor:</strong> ${c.medidor || 'N/A'}</p>
                <button class="btn-secondary" style="margin-top: 10px;">Ver Facturas</button>
            </div>
        `).join('');
    } catch (err) {
        list.innerHTML = '<p>Error al cargar contratos.</p>';
    }
}

async function loadFacturas(contratoId) {
    const container = document.getElementById('facturas-container');
    const list = document.getElementById('facturas-list');
    if(!container || !list) return;
    container.classList.remove('hidden');
    list.innerHTML = '<p>Cargando facturas...</p>';

    try {
        const res = await fetch(`${API_URL}/contratos/${contratoId}/facturas`);
        const facturas = await res.json();
        
        if (facturas.length === 0) {
            list.innerHTML = '<p>No hay facturas para este contrato.</p>';
            return;
        }

        list.innerHTML = facturas.map(f => `
            <div class="list-item">
                <div>
                    <strong>Factura #${f.id}</strong>
                    <p style="color: #94a3b8; font-size: 0.9rem;">Fecha límite: ${f.fechaLimite || 'N/A'}</p>
                    <p style="color: #94a3b8; font-size: 0.9rem;">Monto: $${f.monto}</p>
                    ${f.montoDeuda > 0 ? `<p style="color: var(--danger); font-size: 0.9rem;">Deuda: $${f.montoDeuda}</p>` : ''}
                </div>
                <div>
                    <span class="badge ${f.estado}">${f.estado}</span>
                    ${f.estado !== 'PAGADA' ? `
                        <button class="btn-action" style="margin-left: 10px;" onclick="openPaymentModal(${f.id}, ${f.montoDeuda > 0 ? f.montoDeuda : f.monto}, ${contratoId})">Pagar</button>
                    ` : ''}
                </div>
            </div>
        `).join('');
    } catch (err) {
        list.innerHTML = '<p>Error al cargar facturas.</p>';
    }
}

// Payment Modal Logic
let currentPaymentData = null;

function openPaymentModal(facturaId, monto, contratoId) {
    currentPaymentData = { facturaId, monto, contratoId };
    const payFacturaId = document.getElementById('pay-factura-id');
    const payMonto = document.getElementById('pay-monto');
    const paymentModal = document.getElementById('payment-modal');
    
    if(payFacturaId) payFacturaId.textContent = facturaId;
    if(payMonto) payMonto.textContent = monto;
    if(paymentModal) paymentModal.classList.remove('hidden');
}

function closePaymentModal() {
    const paymentModal = document.getElementById('payment-modal');
    if(paymentModal) paymentModal.classList.add('hidden');
    currentPaymentData = null;
    const paymentForm = document.getElementById('payment-form');
    if(paymentForm) paymentForm.reset();
}

const paymentForm = document.getElementById('payment-form');
if(paymentForm) {
    paymentForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        if (!currentPaymentData) return;

        const medioPago = document.getElementById('pay-method').value;
        const payload = {
            idContrato: currentPaymentData.contratoId,
            clienteId: currentUser.id,
            valorPagado: currentPaymentData.monto,
            fechaPago: new Date().toISOString(),
            referenciaPago: 'REF-' + currentPaymentData.facturaId + '-' + Date.now(),
            medioPago: medioPago,
            estadoPago: 'CONFIRMADO',
            esProcesoActivoSuspension: true,
            antesDeSuspension: true
        };

        try {
            const res = await fetch(`${API_URL}/pagos`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            
            if (res.ok) {
                showToast('Pago registrado y procesado exitosamente.');
                closePaymentModal();
                loadFacturas(currentPaymentData.contratoId);
            } else {
                showToast('Error al procesar el pago.');
            }
        } catch (err) {
            showToast('Error de conexión durante el pago.');
        }
    });
}

// Notifications Logic
async function loadNotifications() {
    const list = document.getElementById('notifications-list');
    if(!list) return;
    list.innerHTML = '<p>Cargando...</p>';

    try {
        const res = await fetch(`${API_URL}/clientes/${currentUser.id}/notificaciones`);
        const notificaciones = await res.json();
        
        if (notificaciones.length === 0) {
            list.innerHTML = '<p>No tienes notificaciones recientes.</p>';
            return;
        }

        list.innerHTML = notificaciones.map(n => `
            <div class="list-item" style="flex-direction: column; align-items: flex-start;">
                <div style="width: 100%; display: flex; justify-content: space-between;">
                    <strong>${n.tipo}</strong>
                    <span style="font-size: 0.8rem; color: #94a3b8;">${n.fecha ? new Date(n.fecha).toLocaleString() : ''}</span>
                </div>
                <p style="margin-top: 5px; color: #cbd5e1;">${n.mensaje || ''}</p>
            </div>
        `).join('');
    } catch (err) {
        list.innerHTML = '<p>Error al cargar notificaciones.</p>';
    }
}

// Requests Logic
function showNewRequestForm() {
    const el = document.getElementById('new-request-form-container');
    if(el) el.classList.remove('hidden');
}

function hideNewRequestForm() {
    const el = document.getElementById('new-request-form-container');
    if(el) el.classList.add('hidden');
    const form = document.getElementById('add-request-form');
    if(form) form.reset();
}

const addRequestForm = document.getElementById('add-request-form');
if(addRequestForm) {
    addRequestForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const payload = {
            tipoSolicitud: document.getElementById('req-type').value,
            idContrato: document.getElementById('req-contract').value || null,
            descripcion: document.getElementById('req-desc').value,
            idCliente: currentUser.id,
            tipoDocumento: 'CC',
            numeroDocumento: currentUser.documento
        };

        try {
            const res = await fetch(`${API_URL}/solicitudes`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            
            if (res.ok) {
                showToast('Solicitud enviada exitosamente.');
                hideNewRequestForm();
                loadRequests();
            } else {
                showToast('Error al enviar la solicitud.');
            }
        } catch (err) {
            showToast('Error de conexión.');
        }
    });
}

async function loadRequests() {
    const list = document.getElementById('requests-list');
    if(!list) return;
    list.innerHTML = '<p>Cargando...</p>';

    try {
        const res = await fetch(`${API_URL}/clientes/${currentUser.id}/solicitudes`);
        const solicitudes = await res.json();
        
        if (solicitudes.length === 0) {
            list.innerHTML = '<p>No has realizado ninguna solicitud.</p>';
            return;
        }

        list.innerHTML = solicitudes.map(s => `
            <div class="list-item" style="flex-direction: column; align-items: flex-start;">
                <div style="width: 100%; display: flex; justify-content: space-between; margin-bottom: 5px;">
                    <strong>Solicitud #${s.id} - ${s.tipoSolicitud}</strong>
                    <span class="badge ${s.estado === 'radicada' ? 'PENDIENTE' : 'EN_PROCESO'}">${s.estado.toUpperCase()}</span>
                </div>
                <p style="color: #cbd5e1; font-size: 0.9rem;">${s.descripcion}</p>
                ${s.respuesta ? `
                    <div style="margin-top: 10px; padding: 10px; background: rgba(0,0,0,0.2); border-radius: 5px; width: 100%;">
                        <strong style="font-size: 0.85rem; color: #3b82f6;">Respuesta de Empresa:</strong>
                        <p style="font-size: 0.9rem; color: #f8fafc;">${s.respuesta}</p>
                    </div>
                ` : ''}
            </div>
        `).join('');
    } catch (err) {
        list.innerHTML = '<p>Error al cargar solicitudes.</p>';
    }
}
