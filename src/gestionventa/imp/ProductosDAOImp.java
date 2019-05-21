package gestionventa.imp;

import gestionventa.beans.Producto;
import gestionventa.def.DAO;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAOImp implements DAO<Producto> {

    private static ProductosDAOImp dao = null;

    private ProductosDAOImp() {
    }

    public static ProductosDAOImp getInstance() {
        if (dao == null) {
            dao = new ProductosDAOImp();
        }
        return dao;
    }

    @Override
    public List<Producto> list() {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select p_codigo, p_nombre, p_descripcion, p_precio from productos");
        if (l == null || l.isEmpty()) {
            return null;
        } else {
            List<Producto> productos = new ArrayList();
            l.forEach(r -> {
                Producto p = new Producto();
                p.setCodigo(r[0]);
                p.setNombre(r[1]);
                p.setDescripcion(r[2]);
                p.setPrecio(r[3]);
                productos.add(p);
            });
            return productos;
        }
    }

    @Override
    public Producto record(String[] params) {
        return findByCodigo(ConexionBD.getInstance()
                .executeInsert("insert into productos (p_nombre, p_descripcion, p_precio) values (?,?,?)", params).toString());
    }

    @Override
    public boolean update(Producto t) {
        return ConexionBD.getInstance().executeSql("update productos set p_nombre = ?, p_descripcion = ?, p_precio = ? where p_codigo = ?", new String[]{
            t.getNombre(),
            t.getDescripcion(),
            t.getPrecio(),
            t.getCodigo()
        });
    }

    public boolean updateCodigo(Producto t, String newCodigo) {
        return ConexionBD.getInstance().executeSql("update productos set p_codigo = ? where p_codigo = ?", new String[]{
            newCodigo, t.getCodigo()
        });
    }

    @Override
    public boolean delete(Producto t) {
        return ConexionBD.getInstance().executeSql("delete FROM productos where p_codigo = ?", t.getCodigo());
    }

    @Override
    public Producto findByCodigo(String codigo) {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select p_codigo, p_nombre, p_descripcion, p_precio from productos where p_codigo = ?");
        if (l == null || l.isEmpty()) {
            return null;
        } else {
            Producto p = new Producto();
            p.setCodigo(l.get(0)[0]);
            p.setNombre(l.get(0)[1]);
            p.setDescripcion(l.get(0)[2]);
            p.setPrecio(l.get(0)[3]);
            return p;
        }
    }

}
