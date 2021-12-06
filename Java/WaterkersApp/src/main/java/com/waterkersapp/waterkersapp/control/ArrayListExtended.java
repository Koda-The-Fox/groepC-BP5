package com.waterkersapp.waterkersapp.control;

import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListExtended<E> extends ArrayList {

    /**
     * Adds an object to the end of the list
     * @param e
     * @return
     */
    public boolean append(E e){
        add(size(), e);
        return true;
    }


    /**
     * Adds an object to the start of the list
     * @param e
     * @return
     */
    /*
    public boolean prepend(E e){
        Object[] elementData = this.elementData;
        System.arraycopy(elementData, index,
                elementData, index + 1);
    }

     */





}
