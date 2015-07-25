package com.azmotors.store.controller;

import com.azmotors.store.model.IndustryPartElement;

public abstract class AZController
{
    protected AZController()
    {
        super();
    }

    public abstract void valueModified(final int index);

    public abstract void sold(IndustryPartElement industryPartElement);
}
