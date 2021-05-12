package com.rbondarovich.service.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkBean {

    private Long id;
    private String name;
    private Integer linkDepth;
    private LinkBean parentLink;
    private String seed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkBean)) return false;
        LinkBean linkBean = (LinkBean) o;
        return Objects.equals(getName(), linkBean.getName()) &&
                Objects.equals(getSeed(), linkBean.getSeed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSeed());
    }

}
