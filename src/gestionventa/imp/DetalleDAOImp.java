package gestionventa.imp;

import gestionventa.beans.Detalle;
import gestionventa.def.DAO;
import java.util.ArrayList;
import java.util.List;


public class DetalleDAOImp implements DAO<Detalle> {

    private static DetalleDAOImp dao = null;

    private DetalleDAOImp() {
    }

    public static DetalleDAOImp getInstance() {
        if (dao == null) {
            dao = new DetalleDAOImp();
        }
        return dao;
    }

  
     // Nos devuelve una lista con los detalles de factura
    @Override
    public List<Detalle> list() {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select id, created_at, id_factura, p_codigo, precio, cantidad from detalles");
        if (l == null || !l.isEmpty()) {
            return null;
        } else {
            List<Detalle> detalles = new ArrayList();
            l.forEach(r -> {
                detalles.add(new Detalle(r[0], r[1], r[2], r[3], r[4], r[5]));
            });
            return detalles;
        }
    }

    public List<Detalle> list(String idFactura) {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select id, created_at, id_factura, p_codigo, precio, cantidad from detalles where id_factura = ?", idFactura);
        if (l == null || !l.isEmpty()) {
            return null;
        } else {
            List<Detalle> detalles = new ArrayList();
            l.forEach(r -> {
                detalles.add(new Detalle(r[0], r[1], r[2], r[3], r[4], r[5]));
            });
            return detalles;
        }
    }

    
     // Queremos insertar un registro (id_factura, p_codigo, precio, cantidad) en la tabla detalles
    @Override
    public Detalle record(String[] params) {
        return findByCodigo(ConexionBD.getInstance().executeInsert("insert into detalles (id_factura, p_codigo, precio, cantidad) values (?,?,?,?)", params).toString());
    }

  
     // Se actualiza un registro de la tabla detalles
    @Override
    public boolean update(Detalle t) {
        return ConexionBD.getInstance().executeSql("update detalles set id_factura = ?, p_codigo = ?, precio = ?, cantidad = ? where id = ?", new String[]{
            t.getIdFactura(),
            t.getpCodigo(),
            t.getPrecio(),
            t.getCantidad(),
            t.getId()
        });
    }

    
     // Elimina un registro de la tabla detalles
    @Override
    public boolean delete(Detalle t) {
        return ConexionBD.getInstance().executeSql("delete from detalles where id = ?", t.getId());
    }

    
     // Aqui hace una busqueda en la bdd del producto
    @Override
    public Detalle findByCodigo(String codigo) {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select id, created_at, id_factura, p_codigo, precio, cantidad from detalles where id = ?", codigo);
        if (l == null || l.isEmpty()) {
            return null;
        } else {
            return new Detalle(l.get(0)[0], l.get(0)[1], l.get(0)[2], l.get(0)[3], l.get(0)[4], l.get(0)[5]);
        }
    }

}
