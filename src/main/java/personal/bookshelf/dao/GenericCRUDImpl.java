package personal.bookshelf.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personal.bookshelf.core.util.JPAHelper;
import personal.bookshelf.model.IdentifiableEntity;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenericCRUDImpl<T extends IdentifiableEntity> implements IGenericCRUD<T> {

    private Class<T> persistenceClass;

    @Override
    public Optional<T> insert(T t) {
        EntityManager em = getEntityManager();
        em.persist(t);
        return Optional.of(t);
    }

    @Override
    public Optional<T> update(T t) {
        EntityManager em = getEntityManager();
        em.merge(t);
        return Optional.of(t);
    }

    @Override
    public void delete(Object id) {
        EntityManager em = getEntityManager();
        Optional<T> entity = getById(id);
        entity.ifPresent(em::remove);
    }

    @Override
    public Optional<T> getById(Object id) {
        EntityManager em = getEntityManager();
        return Optional.ofNullable(em.find(getPersistenceClass(), id));
    }

    @Override
    public List<T> getAll() {
        return getByCriteria(getPersistenceClass(), Collections.emptyMap());
    }

    @Override
    public List<T> getByCriteria(Map<String, Object> criteria) {
        return getByCriteria(getPersistenceClass(), criteria);
    }

    @Override
    public List<T> getByCriteria(Class<T> tClass, Map<String, Object> criteria) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);

        Predicate[] predicatesArray = createPredicatesArray(cb, root, criteria);
        criteriaQuery.select(root).where(predicatesArray);

        TypedQuery<T> query = em.createQuery(criteriaQuery);
        addParametersToQuery(query, criteria);
        return query.getResultList();
    }

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

    private Predicate[] createPredicatesArray(CriteriaBuilder cb, Root<T> root, Map<String, Object> criteria) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            Path<?> path = createPath(root, key);
            ParameterExpression<?> parameter = createParameter(cb, key, value);

            Predicate predicate = cb.like((Expression<String>) path, (Expression<String>) parameter);
            predicates.add(predicate);
        }

        return predicates.toArray(new Predicate[0]);
    }

    // Create the path needed in cb.like()
    private Path<?> createPath(Root<T> root, String key) {
        String[] pathAttributes = key.split("\\.");   // split keys of the form: courses.title

        Path<?> path = root.get(pathAttributes[0]);
        for (int i = 1; i < pathAttributes.length; i++) {
            path = path.get(pathAttributes[i]);
        }
        return path;
    }

    // Create a parameter using the name derived from key and the class of value
    private ParameterExpression<?> createParameter(CriteriaBuilder cb, String key, Object value) {
        String paramName = createParameterName(key);
        return cb.parameter(value.getClass(), paramName);
    }

    // Create the parameter name. If key is: courses.title,
    // the parameter name will be coursestitle (remove the dots).
    private String createParameterName(String key) {
        return key.replaceAll("\\.", "");
    }

    // Insert values to the parameters of the query.
    private void addParametersToQuery(TypedQuery<?> query, Map<String, Object> criteria) {
        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(createParameterName(key), value);
        }
    }
}
