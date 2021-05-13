package com.rbondarovich.service.impl;

import com.rbondarovich.dao.entity.Link;
import com.rbondarovich.dao.repository.LinkRepository;
import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.interfaces.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Сlass for retrieving and saving links from the database
 * Implements LinkService
 * @author Roman Bondarovich
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    /**
     * To convert entities into bins and vice versa
     */
    private final EntityBeanConverterImpl converter;

    /**
     * Allows you to retrieve data from the database
     */
    private final LinkRepository linkRepository;

    /**
     * Gets a list of links from the database by seed link and converts it to a bean
     *
     * @param seedLink link consisting only of letters and numbers
     * @return List<LinkBean>
     * @throws ResourceNotFoundException if seedLink not found
     */
    @Override
    public List<LinkBean> getAllLinks(String seedLink) {
        List<Link> links = linkRepository.findBySeed(seedLink)
                .orElseThrow(() -> new ResourceNotFoundException("Link doesn't exist with seedLink: " + seedLink));

        return converter.convertToBeanList(links, LinkBean.class);
    }

    /**
     * Gets link from the database by id and converts it to a bean
     *
     * @param id assigned in the database
     * @return LinkBean
     * @throws ResourceNotFoundException if id not found
     */
    @Override
    public LinkBean getLinkById(Long id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Link doesn't exist with id: " + id));

        return converter.convertToBean(link, LinkBean.class);
    }

    /**
     * Gets link from the database by @param name and converts it to a bean
     *
     * @param name assigned in the database
     * @return LinkBean
     * @throws ResourceNotFoundException if name not found
     */
    @Override
    public LinkBean getLinkByName(String name) {
        Link link = linkRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Link doesn't exist with name: " + name));

        return converter.convertToBean(link, LinkBean.class);
    }

    /**
     * Converts bean into a entity and saves it to database
     *
     * @param linkBean
     */
    @Override
    public void saveLink(LinkBean linkBean) {
        Link link = converter.convertToEntity(linkBean, Link.class);
        linkRepository.save(link);
    }

    /**
     * Delete link by id
     * @param id assigned in the database
     */
    @Override
    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }

}
