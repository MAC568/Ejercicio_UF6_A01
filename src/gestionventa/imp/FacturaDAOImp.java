package gestionventa.imp;

import gestionventa.beans.Factura;
import gestionventa.def.DAO;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImp implements DAO<Factura> {

    private static FacturaDAOImp dao = null;

    private FacturaDAOImp() {
    }

    public static FacturaDAOImp getInstance() {
        if (dao == null) {
            dao = new FacturaDAOImp();
        }
        return dao;
    }

    // Devuelve una lista con los datos de las facturas
    @Override
    public List<Factura> list() {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select id, created_at, e_codigo, total from factura");
        if (l == null || l.isEmpty()) {
            return null;
        } else {
            List<Factura> facturas = new ArrayList();
            l.forEach(r -> {
                facturas.add(new Factura(r[0], r[1], r[2], r[3]));
            });
            return facturas;
        }
    }

    @Override
    public Factura record(String[] params) {
        return findByCodigo(
                ConexionBD.getInstance().executeInsert("insert into factura (e_codigo, total) values (?,?)", params).toString()
        );
    }

    @Override
    public boolean update(Factura t) {
        return ConexionBD.getInstance().executeSql("update factura set e_codigo = ?, total = ? where id = ?", new String[]{
            t.geteCodigo(),
            t.getTotal(),
            t.getId()
        });
    }

    @Override
    public boolean delete(Factura t) {
        return ConexionBD.getInstance().executeSql("delete from factura where id = ?", t.getId());
    }

    @Override
    public Factura findByCodigo(String codigo) {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select id, created_at, e_codigo, total from factura where id = ?", codigo);
        if (l == null || l.isEmpty()) {
            return null;
        } else {
            return new Factura(l.get(0)[0], l.get(0)[1], l.get(0)[2], l.get(0)[3]);
        }
    }

}
