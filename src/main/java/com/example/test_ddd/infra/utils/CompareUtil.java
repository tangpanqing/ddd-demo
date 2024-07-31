package com.example.test_ddd.infra.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.*;

@Data
public class CompareUtil {
    private List<Object> addList = new ArrayList<>();
    private List<Object> updateList = new ArrayList<>();
    private List<Object> delList = new ArrayList<>();
    private Map<String, BaseMapper> functionMap = new HashMap<>();

    protected boolean isEquals(Object oldObject, Object newObject) {
        if(null == oldObject && null == newObject){
            return true;
        }

        if(null == oldObject && null != newObject){
            return false;
        }

        if(null != oldObject && null == newObject){
            return false;
        }

        return oldObject.equals(newObject);
    }

    protected Object getUpdateObj(Object oldValue, Object newValue) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        //System.out.println("旧值 " + oldValue);
        //System.out.println("新值 " + newValue);

        String className = newValue.getClass().getName();
        Class clazz = Class.forName(className);
        Object resValue = clazz.newInstance();

        int index = 0;
        for (Field fieldField : newValue.getClass().getDeclaredFields()) {
            fieldField.setAccessible(true);
            Object oldValueValue = fieldField.get(oldValue);
            Object newValueValue = fieldField.get(newValue);

            if (index == 0 || !isEquals(oldValueValue, newValueValue)) {
                Field field = clazz.getDeclaredField(fieldField.getName());
                field.setAccessible(true);
                field.set(resValue, newValueValue);
            }

            index += 1;
        }

        //System.out.println("结果 " + resValue);
        return resValue;
    }

    protected void compareObject(Object oldValue, Object newValue) throws NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (!"java.lang.Long".equals(newValue.getClass().getName())) {
            if (oldValue == null && newValue != null) {
                this.getAddList().add(newValue);
            }

            if (oldValue != null && newValue == null) {
                this.getDelList().add(oldValue);
            }

            if (oldValue != null && newValue != null && !oldValue.equals(newValue)) {
                //先对比主键是否一致，如果不一致，这删除旧值，添加新值。如果一致，再比对属性。
                Field firstFieldOfOldValue = oldValue.getClass().getDeclaredFields()[0];
                firstFieldOfOldValue.setAccessible(true);

                Field firstFieldOfNewValue = newValue.getClass().getDeclaredFields()[0];
                firstFieldOfNewValue.setAccessible(true);

                if (!firstFieldOfOldValue.get(oldValue).equals(firstFieldOfNewValue.get(newValue))) {
                    this.getDelList().add(oldValue);
                    this.getAddList().add(newValue);
                } else {
                    this.getUpdateList().add(getUpdateObj(oldValue, newValue));
                }
            }
        }
    }

    public void compare(Object oldObject, Object newObject) {
        try{
            Class<?> clazz = newObject.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object oldValue = oldObject == null ? null : field.get(oldObject);
                Object newValue = newObject == null ? null : field.get(newObject);

                if(newValue != null){
                    if ("java.util.ArrayList".equals(newValue.getClass().getName())) {
                        List<Object> oldValueList = oldValue == null ? Arrays.asList() : (ArrayList) oldValue;
                        List<Object> newValueList = newValue == null ? Arrays.asList() : (ArrayList) newValue;
                        for (int i = 0; i < Math.max(oldValueList.size(), newValueList.size()); i++) {
                            compareObject(i < oldValueList.size() ? oldValueList.get(i) : null, i < newValueList.size() ? newValueList.get(i) : null);
                        }
                    } else {
                        compareObject(oldValue, newValue);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        this.getAddList().forEach(item -> {
            BaseMapper callback = this.getFunctionMap().get(item.getClass().getName());
            if (null != callback) {
                callback.insert(item);
            }
        });

        this.getUpdateList().forEach(item -> {
            BaseMapper callback = this.getFunctionMap().get(item.getClass().getName());
            if (null != callback) {
                callback.updateById(item);
            }
        });

        this.getDelList().forEach(item -> {
            BaseMapper callback = this.getFunctionMap().get(item.getClass().getName());
            if (null != callback) {
                callback.deleteById(item);
            }
        });
    }

    public void regCallback(Class<?> clazz, BaseMapper callback) {
        this.functionMap.put(clazz.getName(), callback);
    }
}