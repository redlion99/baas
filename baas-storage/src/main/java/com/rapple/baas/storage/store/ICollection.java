package com.rapple.baas.storage.store;

import java.util.List;

/**
 * Created by libin on 14-11-25.
 */
public interface ICollection<T> {
    public List<T> collection();

    public void append(T instance);

    public void remove(T instance);

    public void update(T instance);
}
