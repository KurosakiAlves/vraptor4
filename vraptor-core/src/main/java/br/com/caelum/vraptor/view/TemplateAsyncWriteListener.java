package br.com.caelum.vraptor.view;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * Template of an AsyncWriteListener.
 * @author Guilherme Alves
 */
public abstract class TemplateAsyncWriteListener implements WriteListener
{
    protected final ServletOutputStream output;
    protected final AsyncContext async;

    public TemplateAsyncWriteListener(ServletOutputStream output, AsyncContext async)
    {
        this.output = output;
        this.async = async;
    }
    
    /**
     * The classes the write in an async way,
     * must implement this.
     * @return if method was finished.
     * @throws java.io.IOException
     */
    public abstract boolean finished() throws IOException;
    
    @Override
    public void onWritePossible() throws IOException
    {
        while (output.isReady())
        {
            if (finished())
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
