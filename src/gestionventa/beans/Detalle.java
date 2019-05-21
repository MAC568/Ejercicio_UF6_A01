package gestionventa.beans;


public class Detalle {
    private String id;
    private String createdAt;
    private String idFactura;
    private String pCodigo;
    private String precio;
    private String cantidad;
    
    
    public Detalle(){
        id = "";
        createdAt = "";
        idFactura = "";
        pCodigo = "";
        precio = "";
        cantidad = "";
    }
    
    public Detalle(String id, String createdAt, String idFactura, String pCodigo, String precio, String cantidad){
        this.id = id;
        this.createdAt = createdAt;
        this.idFactura = idFactura;
        this.pCodigo = pCodigo;
        this.precio = precio;
        this.cantidad = cantidad;
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

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }


    public String getpCodigo() {
        return pCodigo;
    }


    public void setpCodigo(String pCodigo) {
        this.pCodigo = pCodigo;
    }

    public String getPrecio() {
        return precio;
    }

 
    public void setPrecio(String precio) {
        this.precio = precio;
    }


    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
