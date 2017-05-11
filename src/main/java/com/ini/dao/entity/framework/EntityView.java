package com.ini.dao.entity.framework;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 * assemble view of the entity
 */
public class EntityView extends SimpleBeanInfo {
    private Map<String, PropertyDescriptor> view;
    private final Map<String, PropertyDescriptor> all  = new HashMap<String, PropertyDescriptor>();

    private final Object bean;

    public static EntityView empty(Object object) {
        EntityView re = new EntityView(object);
        re.view = new HashMap<String, PropertyDescriptor>();
        return re;
    }
    public static EntityView all(Object object) {
        EntityView re = new EntityView(object);
        re.view = re.all;
        return re;
    }

    public EntityView add(String name) {
        PropertyDescriptor prop = all.get(name);
        if (prop != null) {
            view.put(name, prop);
        }
        return this;
    }
    public EntityView add(String... names) {
        for (String name : names) {
            PropertyDescriptor prop = all.get(name);
            if (prop != null) {
                view.put(name, prop);
            }
        }
        return this;
    }
    public EntityView remove(String name) {
        if (view.containsKey(name))
            view.remove(name);
        return this;
    }

    public Map getMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Collection<PropertyDescriptor> properties = view.values();
        try {
            for (PropertyDescriptor prop : properties) {
                map.put(prop.getName(), prop.getReadMethod().invoke(this.bean));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return map;
    }

    private EntityView(Object object) {
        this.bean = object;
        try {
            PropertyDescriptor[] properties = Introspector.
                    getBeanInfo(object.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor prop : properties) {
                if (prop.getName() == "class") //filter property class
                    continue;
                all.put(prop.getName(), prop);
            }

        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }
}
