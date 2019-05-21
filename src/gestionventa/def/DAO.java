package gestionventa.def;

import java.util.List;


public interface DAO <T> {
    
    public List<T> list();
    
    public T record(String [] params);
    
    public boolean update(T t);
    
    public boolean delete(T t);
    
    public T findByCodigo(String codigo);
}
