package gestionventa.imp;

import gestionventa.beans.Empleado;
import gestionventa.def.DAO;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImp implements DAO<Empleado> {

    private static EmpleadoDAOImp dao = null;

    private EmpleadoDAOImp() {
    }

    public static EmpleadoDAOImp getInstance() {
        if (dao == null) {
            dao = new EmpleadoDAOImp();
        }
        return dao;
    }

    
     //Queremos que nos devuelva una lista con los empleados registrados en la base de datos 
    @Override
    public List<Empleado> list() {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select e_codigo, e_nombre, e_apellidos, e_password from empleados");
        if (l == null || l.isEmpty()) {
            return null;
        } else {
            List<Empleado> empleados = new ArrayList();
            l.forEach(r -> {
                Empleado e = new Empleado();
                e.setCodigo(r[0]);
                e.setNombre(r[1]);
                e.setApellidos(r[2]);
                e.setContraseña(r[3]);
                empleados.add(e);
            });
            return empleados;
        }
    }

    
// Queremos que inserte un registro (param: nombre, apellido, password) en la tabla empleados.
    @Override
    public Empleado record(String[] params) {
        return findByCodigo(ConexionBD.getInstance()
                .executeInsert("insert into empleados (e_nombre, e_apellidos, e_password) values (?,?,?)", params).toString());
    }

//Actualiza un registro de la tabla empleados
    @Override
    public boolean update(Empleado t) {
        return ConexionBD.getInstance().executeSql("update empleados set e_nombre = ?, e_apellidos = ?, e_password = ? where e_codigo = ?", new String[]{
            t.getNombre(),
            t.getApellidos(),
            t.getContraseña(),
            t.getCodigo()
        });
    }

//Elimina un registro de la tbla empleados
    @Override
    public boolean delete(Empleado t) {
        return ConexionBD.getInstance().executeSql("delete FROM empleados where e_codigo = ?", t.getCodigo());
    }

    @Override
    public Empleado findByCodigo(String codigo) {
        List<String[]> l = ConexionBD.getInstance().executeQuery("select e_codigo, e_nombre, e_apellidos, e_password from empleados where e_codigo = ?", codigo);
        if (l == null || l.isEmpty()) {
            return null;
        } else {
            Empleado e = new Empleado();
            e.setCodigo(l.get(0)[0]);
            e.setNombre(l.get(0)[1]);
            e.setApellidos(l.get(0)[2]);
            e.setContraseña(l.get(0)[3]);
            return e;
        }
    }

}
