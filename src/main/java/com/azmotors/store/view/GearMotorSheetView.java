package com.azmotors.store.view;

import java.net.URL;

public final class GearMotorSheetView extends IndustryPartsSheetView
{
    @Override
    protected URL specificResourceFXML()
    {
        return getClass().getResource("GearMotorTable.fxml");
    }
}
