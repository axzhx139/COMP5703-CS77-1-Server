package com.gday.trackmygrocery.service;

import javax.servlet.http.HttpServletResponse;

public interface ThirdPartyService {
    public byte[] getFacebook(String fileName);

    String downloadFile(HttpServletResponse response, String fileName);
}
