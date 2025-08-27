package com.fpass.service.services;

import com.fpass.service.util.QRCodeGenerator;
import org.springframework.stereotype.Service;

@Service
public class QRCodeService {

    public String generateQRCode(String data) throws Exception {
        return QRCodeGenerator.generateQRCodeImage(data, 250, 250, "qrcodes/");
    }
}
