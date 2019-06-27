package com.zlrx.springreactive.springreactive.fluxmono;

public class CustomException extends Throwable {

    public CustomException(Throwable e) {
        System.out.println(e.getMessage());
    }


}
