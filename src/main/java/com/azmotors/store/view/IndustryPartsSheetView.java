package com.azmotors.store.view;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public abstract class IndustryPartsSheetView
{
    public final Parent load()
    {
        final FXMLLoader loader = new FXMLLoader(specificResourceFXML());
        assert null != loader : "Local variable 'loader' of method 'start' must not be null";
        try
        {
            return loader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    protected abstract URL specificResourceFXML();
}
