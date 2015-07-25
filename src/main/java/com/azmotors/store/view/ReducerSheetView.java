package com.azmotors.store.view;

import java.net.URL;

public final class ReducerSheetView extends IndustryPartsSheetView
{
    @Override
    protected URL specificResourceFXML()
    {
        return getClass().getResource("ReducerTable.fxml");
    }
}
