import json
import time
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer


def decode_body(raw_body):
    try:
        return raw_body.decode("utf-8")
    except UnicodeDecodeError:
        return raw_body.decode("latin-1")


def build_product_rows(products_text):
    if not products_text:
        return []

    separators = ["\n", ";", ","]
    rows = [products_text]
    for separator in separators:
        if separator in products_text:
            rows = products_text.split(separator)
            break

    return [
        {"descripcion": item.strip()}
        for item in rows
        if item and item.strip()
    ]


def build_invoice(payload):
    products_text = str(payload.get("productos", ""))
    products = build_product_rows(products_text)
    total = payload.get("totalDescuento") or payload.get("totalFactura") or 0

    return {
        "facturaId": f"FAC-{int(time.time())}",
        "clienteId": payload.get("clienteId"),
        "categoriaCliente": payload.get("categoriaCliente"),
        "consumo": payload.get("consumo"),
        "resultado": payload.get("resultado"),
        "tarifa": payload.get("tarifa"),
        "subsidio": payload.get("subsidio"),
        "totalFactura": payload.get("totalFactura"),
        "totalDescuento": payload.get("totalDescuento"),
        "totalAPagar": total,
        "tipoLectura": payload.get("tipoLectura"),
        "fecha": payload.get("fecha"),
        "medidor": payload.get("medidor"),
        "operador": payload.get("operador"),
        "observaciones": payload.get("observaciones"),
        "comentario": payload.get("comentario"),
        "productos": products,
        "tablaProductosTexto": "\n".join(
            f"- {product['descripcion']}" for product in products
        ),
    }


class FacturaHandler(BaseHTTPRequestHandler):
    def _send_json(self, status_code, body):
        response = json.dumps(body, ensure_ascii=False).encode("utf-8")
        self.send_response(status_code)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(response)))
        self.end_headers()
        self.wfile.write(response)

    def do_GET(self):
        if self.path == "/health":
            self._send_json(200, {"status": "ok"})
            return

        self._send_json(404, {"error": "Ruta no encontrada"})

    def do_POST(self):
        if self.path != "/factura":
            self._send_json(404, {"error": "Ruta no encontrada"})
            return

        content_length = int(self.headers.get("Content-Length", "0"))
        raw_body = decode_body(self.rfile.read(content_length))

        try:
            payload = json.loads(raw_body) if raw_body else {}
        except json.JSONDecodeError:
            self._send_json(400, {"error": "El cuerpo debe ser JSON válido"})
            return

        invoice = build_invoice(payload)
        print("Factura generada:", json.dumps(invoice, ensure_ascii=False), flush=True)
        self._send_json(200, {"ok": True, "factura": invoice})

    def log_message(self, format, *args):
        print(f"{self.address_string()} - {format % args}", flush=True)


if __name__ == "__main__":
    server = ThreadingHTTPServer(("0.0.0.0", 5000), FacturaHandler)
    print("Factura service escuchando en puerto 5000", flush=True)
    server.serve_forever()
