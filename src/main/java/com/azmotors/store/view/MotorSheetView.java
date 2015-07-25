package com.azmotors.store.view;

import java.net.URL;

public final class MotorSheetView extends IndustryPartsSheetView
{
    @Override
    protected URL specificResourceFXML()
    {
        return getClass().getResource("MotorTable.fxml");
    }
}
