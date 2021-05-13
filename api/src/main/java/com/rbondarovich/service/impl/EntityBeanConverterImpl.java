package com.rbondarovich.service.impl;

import com.rbondarovich.service.interfaces.EntityBeanConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Сlass for mapping beans to entities and vice versa
 * @author Roman Bondarovich
 */
@Service
@RequiredArgsConstructor
public class EntityBeanConverterImpl implements EntityBeanConverter {

    /**
     * Instance of the external library for mapping
     */
    private final ModelMapper mapper;

    /**
     * Converts the entity to a bean
     * @param entity source object
     * @param beanClass *.class of destination object
     * @param <E> type of source object
     * @param <B> type of destination object
     * @return object B
     */
    @Override
    public <E, B> B convertToBean(E entity, Class<B> beanClass) {
        return Objects.isNull(entity) ? null : mapper.map(entity, beanClass);
    }

    /**
     *
     * @param entities collection of source objects
     * @param beanClass collection of destination objects
     * @param <E> type of collection of source object
     * @param <B> type of collection of destination object
     * @return List<B>
     */
    @Override
    public <E, B> List<B> convertToBeanList(Iterable<E> entities, Class<B> beanClass) {
        return  StreamSupport.stream(entities.spliterator(), false)
                .map(ent -> mapper.map(ent, beanClass))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param bean source object
     * @param entityClass *.class of destination object
     * @param <E> type of destination object
     * @param <B> type of source object
     * @return object <E>
     */
    @Override
    public <E, B> E convertToEntity(B bean, Class<E> entityClass) {
        return Objects.isNull(bean) ? null : mapper.map(bean, entityClass);
    }
}
