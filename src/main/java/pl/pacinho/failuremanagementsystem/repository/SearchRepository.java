package pl.pacinho.failuremanagementsystem.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import pl.pacinho.failuremanagementsystem.repository.queries.utils.SearchQueryUtils;
import pl.pacinho.failuremanagementsystem.ui.model.SearchOptionsDto;
import pl.pacinho.failuremanagementsystem.ui.model.SearchResultItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.sql.Array;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SearchRepository {

    private final EntityManagerFactory entityManagerFactory;

    public List<SearchResultItem> search(SearchOptionsDto searchOptions) {
        EntityManager entityManager = null;
        try {
            //NOT WORKING
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(SearchQueryUtils.createQuery(searchOptions));
            query.setParameter("searchText", searchOptions.getSearchText());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.getTransaction().rollback();
        }
        return Collections.emptyList();
    }
}