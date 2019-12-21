package br.digivox.odravison.apiserver.shared.domain;

import java.io.Serializable;

public abstract class Domain<T extends Serializable> {

    public static final String WHERE_DELETED_CLAUSE = "deleted = false";

    public abstract T getId();
    public abstract void setId(T id);

}
