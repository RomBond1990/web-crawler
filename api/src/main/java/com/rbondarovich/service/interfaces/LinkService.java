package com.rbondarovich.service.interfaces;

import com.rbondarovich.service.bean.LinkBean;

public interface LinkService {

    Iterable<LinkBean> getAllLinks();

    LinkBean getLinkById(Long id);

    void saveLink(LinkBean linkBean);

    void deleteLink(Long id);
}
