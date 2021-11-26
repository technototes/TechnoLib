package com.technototes.library.util;

public enum Alliance {
    RED(Color.RED), BLUE(Color.BLUE);
    Color color;
    Alliance(Color c) {
        color = c;
    }
    public Color getColor(){
        return color;
    }

    public <T> T selectOf(T a, T b){
        return Selector.selectOf(this, a, b);
    }

    public static class Selector<T>{
        private final T r, b;
        protected Selector(T red, T blue){
            r = red;
            b = blue;
        }
        public T select(Alliance a){
            return a == RED ? r : b;
        }
        public static <T> Selector<T> of(T red, T blue){
            return new Selector<>(red, blue);
        }

        public static <T> T selectOf(Alliance alliance, T red, T blue){
            return Selector.of(red, blue).select(alliance);
        }
    }
}
