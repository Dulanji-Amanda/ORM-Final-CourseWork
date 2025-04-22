package lk.ijse.dao.custom;

import java.util.List;

public interface CrudDAO <T>{
    boolean save(T entity);
    boolean update(T entity);
    boolean deleteByPK(String id) throws Exception;
    List<T> getAll();
    String getNextId();
    T findById(String id) ;
}