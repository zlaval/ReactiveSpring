package com.zlrx.reactive.webflux.webfluxdemo.fluxmono;

public class CustomException extends Throwable {

    public CustomException(Throwable e) {
        System.out.println(e.getMessage());
    }


}
