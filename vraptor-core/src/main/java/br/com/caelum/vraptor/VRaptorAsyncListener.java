/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.caelum.vraptor;

import java.io.IOException;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletRequest;
import org.slf4j.Logger;

/**
 *
 * @author A-IKASORUK
 */
public class VRaptorAsyncListener implements AsyncListener
{
    private final Logger logger;

    public VRaptorAsyncListener(Logger logger)
    {
        this.logger = logger;
    }

    @Override
    public void onComplete(AsyncEvent event) throws IOException
    {
        logger.debug("VRaptor ended the request");
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException
    {
        long timeout = event.getAsyncContext().getTimeout();
        logger.trace("VRaptor ended in timeout {} ", timeout);
    }

    @Override
    public void onError(AsyncEvent event) throws IOException
    {
        String errorMessage = event.getThrowable().getMessage();
        logger.error("VRaptor received a error {}", errorMessage);
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException
    {
        ServletRequest req = event.getSuppliedRequest();
        logger.trace("VRaptor received a new request {}", req);
    }
}
