package com.azmotors.store;

import com.azmotors.store.view.Store;

public final class App
{
    private App()
    {
        super();
    }

    public static void main(final String[] argv)
    {
        new Store(argv);
    }
}
