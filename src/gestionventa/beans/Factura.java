package gestionventa.beans;

import gestionventa.imp.DetalleDAOImp;
import java.util.List;

public class Factura {

    private String id;
    private String createdAt;
    private String eCodigo;
    private String total;
    private List<Detalle> detalles;

    public Factura() {
        id = "";
        createdAt = "";
        eCodigo = "";
        total = "";
    }

    public Factura(String id, String createdAt, String eCodigo, String total) {
        this.id = id;
        this.createdAt = createdAt;
        this.eCodigo = eCodigo;
        this.total = total;
        detalles = DetalleDAOImp.getInstance().list(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String geteCodigo() {
        return eCodigo;
    }

    public void seteCodigo(String eCodigo) {
        this.eCodigo = eCodigo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

}
