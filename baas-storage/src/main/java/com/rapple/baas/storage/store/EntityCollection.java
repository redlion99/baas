package com.rapple.baas.storage.store;

import com.rapple.baas.common.dto.InstantMessage;
import com.rapple.baas.common.dto.Room;
import org.redisson.Redisson;

import java.util.List;
import java.util.UUID;

/**
 * Created by libin on 14-11-25.
 */
public class EntityCollection<T> implements ICollection<T>{
    protected final List<T> list;

    public EntityCollection(Redisson redisson,Class<T> tClass,String key) {
        list=redisson.getList("Collection@"+tClass.getName()+"@"+key);
    }

    public List<T> collection(){
        return list;
    }

    public void append(T instance){
        list.add(instance);
    }

    public void remove(T instance){
        list.remove(instance);
    }

    public void update(T instance){
        int idx=list.indexOf(instance);
        if(idx>=0){
            list.set(idx,instance);
        }
    }
}
