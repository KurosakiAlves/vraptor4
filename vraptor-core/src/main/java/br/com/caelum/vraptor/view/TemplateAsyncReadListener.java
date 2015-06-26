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
import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * Template for ReadListener
 * @author Guilherme Alves.
 */
public abstract class TemplateAsyncReadListener implements ReadListener, VRaptorAsyncLogicExecutedListener
{

    protected final ServletInputStream input;
    protected final AsyncContext async;

    public TemplateAsyncReadListener(ServletInputStream input, AsyncContext async)
    {
        this.input = input;
        this.async = async;
    }

    @Override
    public void onDataAvailable() throws IOException
    {
        //The two methods must be called in that order to work properly
        while (input.isReady() && !input.isFinished())
        {
            if (finished())
            {
                async.complete();
                break;
            }
        }
    }

    @Override
    public void onAllDataRead() throws IOException
    {
        executeLogic();
    }

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
