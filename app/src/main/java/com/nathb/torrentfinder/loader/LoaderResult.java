package com.nathb.torrentfinder.loader;

public class LoaderResult<T> {

    private T mResult;
    private Exception mError;

    public T getResult() {
        return mResult;
    }

    public void setResult(T result) {
        mResult = result;
    }

    public Exception getError() {
        return mError;
    }

    public void setError(Exception error) {
        mError = error;
    }

}
