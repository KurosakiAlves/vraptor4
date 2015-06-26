package br.com.caelum.vraptor.view;

import java.io.IOException;

/**
 * Inteface for Async Listeners
 * @author Guilherme Alves.
 */
public interface VRaptorAsyncLogicExecutedListener
{
    /**
     * Classes that reads\writes in an async way must implement this.
     * @return true if the method was finished or false otherwise.
     * @throws java.io.IOException
     */
    boolean finished() throws IOException;
}
