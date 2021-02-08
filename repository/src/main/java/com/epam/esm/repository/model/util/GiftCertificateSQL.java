package com.epam.esm.repository.model.util;

import java.util.Arrays;
import java.util.Objects;

public class GiftCertificateSQL {

    private final String request;
    private final Object[] params;

    public GiftCertificateSQL(String request, Object[] params) {
        this.params = params;
        this.request = request;
    }

    public Object[] getParams() {
        return params;
    }

    public String getRequest() {
        return request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateSQL that = (GiftCertificateSQL) o;
        return Arrays.equals(params, that.params) &&
                Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(request);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificateSQL{" +
                "params=" + Arrays.toString(params) +
                ", request='" + request + '\'' +
                '}';
    }
}
