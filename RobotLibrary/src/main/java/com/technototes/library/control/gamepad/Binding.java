package com.technototes.library.control.gamepad;

import java.util.function.BooleanSupplier;

/** Class for bindings to extend
 * @author Alex Stedman
 *
 */
public interface Binding<T extends BooleanSupplier> extends BooleanSupplier{
    /** Button type
     *
     */
    enum Type {
        NONE_ACTIVE, SOME_ACTIVE, ALL_ACTIVE

    }


    T[] getSuppliers();

    Type getDefaultType();


    @Override
    default boolean getAsBoolean() {
        return get(getDefaultType());
    }

    /** Get this as boolean for the type
     *
     * @param type The type to get boolean as
     * @return If the binding meets the criteria
     */
    default boolean get(Type type){
        boolean on=false, off=false;
        for(T s : getSuppliers()){
            if(s.getAsBoolean()){
                on=true;
            }else{
                off=true;
            }
        }
        switch (type){
            case NONE_ACTIVE:
                return !on;
            case ALL_ACTIVE:
                return !off;
            default:
                return on;

        }

    }
    default boolean get(){
        return get(getDefaultType());
    }

}
