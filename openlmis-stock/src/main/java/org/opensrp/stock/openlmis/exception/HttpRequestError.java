package org.opensrp.stock.openlmis.exception;

public class HttpRequestError extends RuntimeException {

    public HttpRequestError(String message) {
        super(message);
    }
}
