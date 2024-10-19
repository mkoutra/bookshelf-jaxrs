package personal.bookshelf.dao;

import personal.bookshelf.model.IdentifiableEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IGenericCRUD<T extends IdentifiableEntity> {
    Optional<T> insert(T t);
    Optional<T> update(T t);
    void delete(Object id);
    Optional<T> getById(Object id);
    List<T> getAll();
    List<T> getByCriteria(Map<String, Object> criteria);
    List<T> getByCriteria(Class<T> tClass, Map<String, Object> criteria);
}
