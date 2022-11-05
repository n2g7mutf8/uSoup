package me.n2g7mutf8.soup.utils;

import java.io.Serializable;

public interface Callback<T> extends Serializable {

    /**
     * Called when the request is successfully completed
     *
     * @param data the data received from the call
     */
    void callback(T data);
}