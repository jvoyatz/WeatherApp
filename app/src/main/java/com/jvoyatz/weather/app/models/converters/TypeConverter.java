package com.jvoyatz.weather.app.models.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Generic class used to transform remote models to local entities
 * @param <F> the remote model, eg an API Model
 * @param <T> the local model-entity, used in app
 *
 */
public abstract class TypeConverter<F, T> {

    /**
     * Abstract method implemented in subclasses.
     * Creates an entity type from the given argument (FROM type)
     * @param from the remote model
     * @return the newly-created local model
     */
    public abstract T toEntity(F from);

    /**
     * Method which takes a collection of remote models
     * and transforms them into a List of Entities
     *
     * @param apiModels the list of remote models
     * @return a new list containing the entities.
     */
    public List<T> toEntities(Collection<F> apiModels){
        List<T> entities = null;
        if(apiModels != null){
            entities = new ArrayList<>(apiModels.size());

            for (F apiModel : apiModels) {
                T entity = toEntity(apiModel);
                if(entity != null){
                    entities.add(entity);
                }
            }
        }
        return entities;
    }
}
