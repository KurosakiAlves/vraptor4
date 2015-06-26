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
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * Template of an WriteListener.
 * @author Guilherme Alves
 */
public abstract class TemplateAsyncWriteListener implements WriteListener, VRaptorAsyncLogicExecutedListener
{
    protected final ServletOutputStream output;
    protected final AsyncContext async;

    public TemplateAsyncWriteListener(ServletOutputStream output, AsyncContext async)
    {
        this.output = output;
        this.async = async;
    }

    @Override
    public void onWritePossible() throws IOException
    {
        while (output.isReady())
        {
            /**
             * The method isFinished must not 
             * execute reading logic from the input,
             * reading logic must be executed in TemplateAsyncReadListener
             * or it's sub-classes.
             * Obs.: The container will throw a IllegalStateException if
             * reading logic is executed in writing logic.
             */
            if (isFinished())
            {
                async.complete();
                break;
            }
        }
    }

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
