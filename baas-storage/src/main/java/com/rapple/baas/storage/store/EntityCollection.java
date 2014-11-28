package com.rapple.baas.storage.store;

import org.redisson.Redisson;

import java.util.List;

/**
 * Created by libin on 14-11-25.
 */
public class EntityCollection<T> implements ICollection<T>{
    protected final List<T> list;

    public EntityCollection(Redisson redisson,Class<T> tClass,String key) {
        list=redisson.getList("Collection@"+tClass.getName()+"@"+key);
    }

    public List<T> collection(){
        return head(0);
    }

    public List<T> head(int page){
        if(page<0)
            page=0;
        int size=list.size();
        int from=Math.min(size,page*50);
        int until=Math.min(size,(page+1)*50);
        return list.subList(from,until);
    }

    public List<T> tail(int page){
        if(page<0)
            page=0;
        int size=list.size();
        int until=size-Math.min(size,page*50);
        int from=size-Math.min(size,(page+1)*50);
        return list.subList(from,until);
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
