package pl.pacinho.failuremanagementsystem.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import pl.pacinho.failuremanagementsystem.repository.queries.utils.SearchQueryUtils;
import pl.pacinho.failuremanagementsystem.ui.model.SearchOptionsDto;
import pl.pacinho.failuremanagementsystem.ui.model.SearchResultItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
            return  entityManager.createNativeQuery(SearchQueryUtils.createQuery(searchOptions))
                    .setParameter("searchText", searchOptions.getSearchText())
                    .unwrap(org.hibernate.query.NativeQuery.class)
                    .setResultTransformer(Transformers.aliasToBean(SearchResultItem.class))
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return Collections.emptyList();
    }
}