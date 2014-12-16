package api.rest;


import org.apache.commons.fileupload.UploadContext;
import restx.RestxRequest;

import java.io.IOException;
import java.io.InputStream;

public class RestXRequestContext implements UploadContext {

    private RestxRequest request;

    public RestXRequestContext(RestxRequest request) {
        this.request = request;
    }

    @Override
    public String getCharacterEncoding() {
        return "utf8";
    }

    @Override
    public String getContentType() {
        return request.getContentType();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return request.getContentStream();
    }

    @Override
    public long contentLength() {
        return -1;
    }

    @Override
    public int getContentLength() {
        return -1;
    }

}