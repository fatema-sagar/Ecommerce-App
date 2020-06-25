package com.ecommerce.ecommApp.invoice;

import com.ecommerce.ecommApp.invoice.invoiceGenerator.service.FetchOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * invoice thread used for send the invoice
 */
@Slf4j
@Service
public class Invoice extends Thread {

    @Autowired
    private FetchOrderServiceImpl fetchOrderServiceImpl;

    /**
     * override method for start the fetch service
     */
    @Override
    public void run() {
        log.info("Start the invoice service in background ------------ with name {}", this.getName());
        fetchOrderServiceImpl.fetchOrder();
        log.info("Stop the fetch service due to some issue");
    }
}
