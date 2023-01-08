package pl.pacinho.failuremanagementsystem.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pacinho.failuremanagementsystem.repository.queries.utils.SearchQueryUtils;
import pl.pacinho.failuremanagementsystem.ui.model.SearchOptionsDto;
import pl.pacinho.failuremanagementsystem.ui.model.SearchResultItem;
import pl.pacinho.failuremanagementsystem.ui.model.mapper.SearchResultItemMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SearchRepository {

    private final EntityManagerFactory entityManagerFactory;

    public List<SearchResultItem> search(SearchOptionsDto searchOptions) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(SearchQueryUtils.createQuery(searchOptions), Tuple.class);
            query.setParameter("searchText", searchOptions.getSearchText());
            return SearchResultItemMapper.parseList((List <Tuple>)query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.getTransaction().rollback();
        }
        return Collections.emptyList();
    }
}