package com.hkkj.oa.entity;

import java.io.Serializable;

public class Role implements Serializable {

	private static final long serialVersionUID = -2021103671823463652L;

	private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}