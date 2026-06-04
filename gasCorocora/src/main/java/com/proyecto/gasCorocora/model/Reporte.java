package com.proyecto.gasCorocora.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Reporte implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmXXX");

    public String fechaReporteFalla;
    public String codigoReporteFalla;
    public String codigoOrdenTrabajo;
    public String domicilio;
    public double presion;
    public double caudal;
    public double temperatura;
    public boolean comunicacionEquiposMedicionActiva;

    public Reporte() {
        setCodigoReporteFalla(UUID.randomUUID().toString());
        setFechaReporteFalla(ZonedDateTime.now().format(FORMATTER));
        setCodigoOrdenTrabajo(UUID.randomUUID().toString());
    }

    public String getCodigoOrdenTrabajo() {
        return codigoOrdenTrabajo;
    }

    public void setCodigoOrdenTrabajo(String codigoOrdenTrabajo) {
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
    }

    public String getFechaReporteFalla() {
        return fechaReporteFalla;
    }

    public void setFechaReporteFalla(String fechaReporteFalla) {
        this.fechaReporteFalla = fechaReporteFalla;
    }

    public String getCodigoReporteFalla() {
        return codigoReporteFalla;
    }

    public void setCodigoReporteFalla(String codigoReporteFalla) {
        this.codigoReporteFalla = codigoReporteFalla;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public double getPresion() {
        return presion;
    }

    public void setPresion(double presion) {
        this.presion = presion;
    }

    public double getCaudal() {
        return caudal;
    }

    public void setCaudal(double caudal) {
        this.caudal = caudal;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public boolean isComunicacionEquiposMedicionActiva() {
        return comunicacionEquiposMedicionActiva;
    }

    public void setComunicacionEquiposMedicionActiva(boolean comunicacionEquiposMedicionActiva) {
        this.comunicacionEquiposMedicionActiva = comunicacionEquiposMedicionActiva;
    }

    @Override
    public String toString(){
        return this.codigoReporteFalla
                + " | " + this.fechaReporteFalla
                + " | " + this.domicilio
                + " | " + this.presion
                + " | " + this.caudal
                + " | " + this.temperatura
                + " | " + this.comunicacionEquiposMedicionActiva + "\n";
    }
}
