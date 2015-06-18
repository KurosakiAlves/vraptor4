/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package br.com.caelum.vraptor;

import java.io.IOException;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletRequest;
import org.slf4j.Logger;

/**
 * Used to listen to the asynchronous event.
 * @author Guilherme Alves
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
        logger.debug("VRaptor completed async request");
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
        logger.trace("VRaptor received a new async request {}", req);
    }
}
