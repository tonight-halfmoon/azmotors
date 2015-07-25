package com.azmotors.store.controller;

import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.TextInputControl;

import com.azmotors.store.controller.repo.RepoOnDiskPointer;
import com.azmotors.store.foundation.DataIOFromRepo;
import com.azmotors.store.foundation.DateUtil;
import com.azmotors.store.model.IndustryPartElement;
import com.azmotors.store.model.Motor;

public final class MotorTableController extends IndustryPartElementTableController
{
    public MotorTableController()
    {
        super(DataIOFromRepo.getInstance().getmRepo(RepoOnDiskPointer.MOTOR));
    }

    @FXML
    private void initialize()
    {
        if (null != m_repo)
        {
            m_repo.clear();
        }
        m_repo = DataIOFromRepo.getInstance().getmRepo(deriveSpecificRepoOnDiskPointer());
        initialiseSuper();
    }

    @Override
    protected IndustryPartElement instantiateTargetIndustryPart()
    {
        return new Motor(m_addName.getText(), DateUtil.today(), m_addKW.getText(), m_addHP.getText(), m_addRPM.getText(), m_addVolt.getText(),
                m_addHz.getText(), m_addHo.getText(), m_addWholesalePrice.getText(), m_addRetailPrice.getText());
    }

    @Override
    protected RepoOnDiskPointer deriveSpecificRepoOnDiskPointer()
    {
        return RepoOnDiskPointer.MOTOR;
    }

    @Override
    protected void add2GatherClearableInputControls(final Set<TextInputControl> tmp)
    {
        //nothing to do - default is enough for Motor
    }

    @Override
    protected void initialiseSpecific()
    {
        /** so far no implementation required */
    }
}
