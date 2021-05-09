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

@Service
@Transactional
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final EntityBeanConverterImpl converter;
    private final LinkRepository linkRepository;

    @Override
    public List<LinkBean> getAllLinks(String seedLink) {
        List<Link> links = linkRepository.findBySeed(seedLink)
                .orElseThrow(() -> new ResourceNotFoundException("Link doesn't exist with seedLink: " + seedLink));
        List<LinkBean> linkBeans = converter.convertToBeanList(links, LinkBean.class);

        return linkBeans;
    }

    @Override
    public LinkBean getLinkById(Long id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Link doesn't exist with id: " + id));
        LinkBean linkBean = converter.convertToBean(link, LinkBean.class);

        return linkBean;
    }

    @Override
    public LinkBean getLinkByName(String name) {
        Link link = linkRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Link doesn't exist with name: " + name));
        LinkBean linkBean = converter.convertToBean(link, LinkBean.class);

        return linkBean;
    }

    @Override
    public void saveLink(LinkBean linkBean) {
        Link link = converter.convertToEntity(linkBean, Link.class);
        linkRepository.save(link);
    }

    @Override
    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }
}
