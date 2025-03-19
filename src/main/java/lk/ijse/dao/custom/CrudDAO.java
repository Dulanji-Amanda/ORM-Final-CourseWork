package lk.ijse.dao.custom;

public interface CrudDAO <T>{
    void save(T entity);
    public String getNextId();
}
