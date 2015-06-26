/**
 * *
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.caelum.vraptor.view;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * Template for ReadListener
 * @author Guilherme Alves.
 */
public abstract class TemplateAsyncReadListener implements ReadListener, VRaptorAsyncLogicExecutedListener
{
    private ServletInputStream input;
    private AsyncContext async;

    public TemplateAsyncReadListener()
    {
        //Default constructor
    }

    public TemplateAsyncReadListener(ServletInputStream input, AsyncContext async)
    {
        setInput(input).
        setAsync(async);
    }

    public ServletInputStream getInput()
    {
        return input;
    }

    public TemplateAsyncReadListener setInput(ServletInputStream input)
    {
        Objects.requireNonNull(async, "The ServletInputStream must not be null!");
        this.input = input;
        input.setReadListener(this);
        return this;
    }

    public AsyncContext getAsync()
    {
        return async;
    }

    public TemplateAsyncReadListener setAsync(AsyncContext async)
    {
        Objects.requireNonNull(async, "The AsyncContext must not be null!");
        this.async = async;
        return this;
    }

    @Override
    public void onDataAvailable() throws IOException
    {
        //The two methods must be called in that order to work properly
        while (input.isReady() && !input.isFinished())
        {
            /**
             * The method isFinished must not 
             * execute writing logic from the output,
             * writing logic must be executed in TemplateAsyncWriteListener
             * or it's sub-classes.
             * Obs.: Writing logic may block in this place.
             */
            if (isFinished())
            {
                async.complete();
                break;
            }
        }

        if (input.isFinished())
        {
            async.complete();
        }
    }

    @Override
    public void onAllDataRead() throws IOException
    {
        executeLogic();
    }

    /**
     * Writing logic may be puth here, 
     * but it must be delegated to an 
     * TemplateAsyncWriteListener implementation.
     */
    public abstract void executeLogic();

    @Override
    public void onError(Throwable e)
    {
        try
        {
            throw new ResultException("Couldn't write to response body", e);
        }
        finally
        {
            async.complete();
        }
    }
}
