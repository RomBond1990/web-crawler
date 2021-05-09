package com.rbondarovich.service.interfaces;

import com.rbondarovich.service.bean.LinkBean;

import java.util.List;

public interface LinkService {

    List<LinkBean> getAllLinks(String seedLink);

    LinkBean getLinkById(Long id);

    LinkBean getLinkByName(String name);

    void saveLink(LinkBean linkBean);

    void deleteLink(Long id);
}
